package lt.bit.meetings.service.meeting;

import lombok.AllArgsConstructor;
import lt.bit.meetings.exception.ApiException;
import lt.bit.meetings.model.atendee.Attendee;
import lt.bit.meetings.model.meeting.Meeting;
import lt.bit.meetings.repository.meeting.MeetingRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;

    public String getResponseIndicatorForAddingAttendees(Attendee attendeeBeingAdded,
                                                      Long meetingId){
        List<Meeting> allMeetings = getAllMeetings();
        Meeting meetingToAddTo = getMeetingById(meetingId);

        if(attendeeBeingAdded.equals(meetingToAddTo.getResponsiblePerson())){
            return "responsibleInThisMeeting";
        }
        if(isResponsibleInOtherMeetingAtThisTime(allMeetings,
                meetingToAddTo, attendeeBeingAdded)){
            return "responsibleInAnotherMeetingNow";
        }
        if(isUniqueAttendeeInMeeting(meetingToAddTo, attendeeBeingAdded)) {
            return "success";
        }
        return "notUniqueAttendee";
    }

    public String getResponseIndicatorForAddingMeetings(Meeting meetingBeingAdded,
                                                     List <Meeting> allMeetings){
        if(isResponsibleInOtherMeetingAtThisTime(allMeetings, meetingBeingAdded)){
            return "isResponsibleInOtherMeetingNow";
        }
        if(isUniqueMeetingName(meetingBeingAdded)){
            return "success";
        }
        return "notUniqueName";
    }

    public boolean isResponsibleInOtherMeetingAtThisTime(List<Meeting> allMeetings,
                                                         Meeting meetingToAddTo,
                                                         Attendee attendeeBeingAdded){
        List<Meeting> overlappingMeetings =
                getOverlappingMeetings(allMeetings, meetingToAddTo);

        for(Meeting m : overlappingMeetings){
            if(attendeeBeingAdded.equals(m.getResponsiblePerson())){
                return true;
            }
        }
        return false;
    }

    public boolean isResponsibleInOtherMeetingAtThisTime(List<Meeting> allMeetings,
                                                         Meeting meetingBeingAdded){
        List<Meeting> overlappingMeetings =
                getOverlappingMeetings(allMeetings, meetingBeingAdded);

        for(Meeting m : overlappingMeetings){
            if(meetingBeingAdded.getResponsiblePerson()
                    .equals(m.getResponsiblePerson())){
                return true;
            }
        }
        return false;
    }

    public List<Meeting> getOverlappingMeetings(List<Meeting> allMeetings,
                                                Meeting meetingToAddTo){
        String startTime = meetingToAddTo.getStartDate().toString();
        String endTime = meetingToAddTo.getEndDate().toString();
        List<Meeting> overlappingMeetings = getMeetingsByDateFrom(startTime, allMeetings);
        overlappingMeetings = getMeetingsByDateTo(endTime, overlappingMeetings);
        return overlappingMeetings;
    }


    public String warningIsInAnotherMeeting(Attendee attendee,
                                            Long meetingId){
        List<Meeting> meetings = getAllMeetings();
        Meeting meetingToAddTo = getMeetingById(meetingId);
        String warningMessage = "Attendee added succesfully! ";
        List<Meeting> overlappingMeetings =
                getOverlappingMeetings(meetings, meetingToAddTo);
        for(Meeting meeting : overlappingMeetings){
            if (isInThisMeeting(meeting, attendee)) {
                String meetingName = meeting.getName();
                String meetingStart = meeting.getStartDate().toString()
                        .replace('T', ' ');
                String meetingEnd = meeting.getEndDate().toString()
                        .replace('T', ' ');
                warningMessage += "WARNING! " +
                        "The person you added to this meeting " +
                        "is already in a meeting overlapping with this one," +
                        " which is called " + meetingName + "." +
                        " It starts at " + meetingStart +
                        " and ends at " + meetingEnd + ".\n";
                }
            }
        return warningMessage;
    }

    public boolean isInThisMeeting(Meeting meeting, Attendee attendee){
        for(Attendee a: meeting.getAttendees()){
            if(a.getId().equals(attendee.getId())){
                return true;
            }
        }
        return false;
    }

    public void addMeetingAfterChecks(Meeting meeting){
        List<Meeting> meetings = getAllMeetings();
        meeting.setId(meeting
                .generateUniqueSerialMeetingId(meetings));
        meetings.add(meeting);
        meetingRepository.writeMeetingData(meetings);
    }

    public void addAttendeeAfterChecks(Attendee attendee,
                                         Long meetingId){
        List<Meeting> meetings = getAllMeetings();
        for(Meeting meeting : meetings){
            if(meetingId.equals(meeting.getId())){
                List<Attendee> attendees = meeting.getAttendees();
                attendees.add(attendee);
                meeting.setAttendees(attendees);
                break;
            }
        }
        meetingRepository.writeMeetingData(meetings);
    }

    public boolean deleteMeeting(Long id){
        boolean success = false;
        List<Meeting> meetings = getAllMeetings();
        for(Meeting meeting: meetings){
            if (id.equals(meeting.getId())){
                if(isLoggedInUserResponsible(meeting)){
                    success = meetings.remove(meeting);
                    meetingRepository.writeMeetingData(meetings);
                    break;
                } else {
                    throw new ApiException(
                            "Cannot delete meeting because you are not" +
                                    "this meeting's responsible person", 4024);
                }
            }
        }
        return success;
    }

    private boolean isLoggedInUserResponsible(Meeting meeting){
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String currentUserEmail = ((UserDetails)principal).getUsername();
            return meeting.getResponsiblePerson().getEmail().equals(currentUserEmail);
        } else {
            return false;
        }
    }

    public boolean removeAttendeeFromMeeting(Long meetingId, Long id){
        List<Meeting> meetings = getAllMeetings();
        Meeting meeting = getMeetingById(meetingId);
        boolean success = false;
        for(Meeting m : meetings){
            if(m.equals(meeting)){
                List<Attendee> attendees = m.getAttendees();
                try{
                    success = attendees.remove(m.getAttendees()
                        .stream().filter(a ->
                        id.equals(a.getId())).toList().get(0));
                    m.setAttendees(attendees);
                } catch (ArrayIndexOutOfBoundsException e){
                    // ignore
                }
            }
        }
        meetingRepository.writeMeetingData(meetings);
        return success;
    }

    //TODO get all attendees in meeting

    public Meeting getMeetingByName(String name){
        List<Meeting> meetings = getAllMeetings();
        return meetings.stream().filter(meeting ->
                 name.equalsIgnoreCase(meeting.getName()))
                .toList().get(0);
    }

    public Meeting getMeetingById(Long id){
        List<Meeting> meetings = getAllMeetings();
        return meetings.stream().filter(meeting ->
                 id.equals(meeting.getId()))
                .toList().get(0);
    }

    public List<Meeting> getMeetingsByDescription(String description,
                                             List<Meeting> meetings){
        return meetings.stream().filter(meeting ->
                 meeting.getDescription().toLowerCase()
                .contains(description.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Meeting> getMeetingsByResponsiblePerson(Long id, List<Meeting> meetings){
        return meetings.stream().filter(meeting ->
                  id.equals(meeting.getResponsiblePerson().getId()))
                 .collect(Collectors.toList());
    }

    public List<Meeting> getMeetingsByCategory(String cat, List<Meeting> meetings){
        return meetings.stream().filter(meeting ->
                 cat.equalsIgnoreCase(meeting.getCategory().toString()))
                .collect(Collectors.toList());
    }

    public List<Meeting> getMeetingsByType(String type, List<Meeting> meetings){
        return meetings.stream().filter(meeting ->
                 type.equalsIgnoreCase(meeting.getType().toString()))
                .collect(Collectors.toList());
    }

    public List<Meeting> getMeetingsByDateFrom(String dateFrom, List<Meeting> meetings) {
        LocalDateTime dateFromDt = LocalDateTime.parse(dateFrom);
        return meetings.stream().filter(meeting ->
                 meeting.getEndDate().compareTo(dateFromDt) >= 0)
                .collect(Collectors.toList());
    }

    public List<Meeting> getMeetingsByDateTo(String dateTo, List<Meeting> meetings){
        LocalDateTime dateToDt = LocalDateTime.parse(dateTo);
        return meetings.stream().filter(meeting ->
                 meeting.getStartDate().compareTo(dateToDt) <= 0)
                .collect(Collectors.toList());
    }

    public List<Meeting> getMeetingsByNumberOfAttendeesFrom(int nr, List<Meeting> meetings){
        return meetings.stream().filter(meeting ->
                 meeting.getAttendees().size() >= nr)
                .collect(Collectors.toList());
    }

    public List<Meeting> getMeetingsByNumberOfAttendeesTo(int nr, List<Meeting> meetings){
        return meetings.stream().filter(meeting ->
                 meeting.getAttendees().size() <= nr)
                .collect(Collectors.toList());
    }

    public List<Meeting> getAllMeetings(){
        return meetingRepository.readMeetingData();
    }

    public boolean isUniqueAttendeeInMeeting(Meeting meeting,
                                    Attendee newAttendee){
        List<Attendee> attendees = meeting.getAttendees();
        HashSet<String> uniqueAttendees = new HashSet<>();

        attendees.forEach(a -> uniqueAttendees.add(a.toString()));
        return uniqueAttendees.add(newAttendee.toString());
    }

    public boolean isUniqueMeetingName(Meeting newMeeting){
        HashSet<String> uniqueNames = new HashSet<>();
        List<Meeting> meetings = getAllMeetings();

        meetings.forEach(m -> uniqueNames.add(m.getName()));
        return uniqueNames.add(newMeeting.getName());
    }
}
