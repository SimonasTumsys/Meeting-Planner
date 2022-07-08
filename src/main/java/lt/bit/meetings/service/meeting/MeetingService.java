package lt.bit.meetings.service.meeting;

import lombok.AllArgsConstructor;
import lt.bit.meetings.model.atendee.Attendee;
import lt.bit.meetings.model.meeting.Meeting;
import lt.bit.meetings.repository.meeting.MeetingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;

    public int getResponseIndicatorForAddingAttendees(Attendee attendee,
                                                      Long meetingId){
        List<Meeting> meetings = getAllMeetings();
        Meeting meeting = getMeetingById(meetingId);

        if(attendee.equals(meeting.getResponsiblePerson())){
            return 1;
        }
        if(isResponsibleInOtherMeetingAtThisTime(meetings,
                meeting, attendee)){
            return 2;
        }
        if(isUniqueAttendeeInMeeting(meeting, attendee)) {
            return 3;
        }
        return 0;
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

    // check if the responsible person is responsible for any other meeting
    // overlapping with the meeting being added. If not, let add. If this person
    // is in another meeting (but not responsible), show warning message.

    public void addMeetingAfterChecks(Meeting meeting){
        List<Meeting> meetings = getAllMeetings();
        meetings.add(meeting);
        meetingRepository.writeMeetingData(meetings);
    }

    public int getResponseIndicatorForAddingMeetings(Meeting meetingToAdd,
                                                     List <Meeting> allMeetings){
        if(isResponsibleInOtherMeetingAtThisTime(allMeetings, meetingToAdd)){
            return 1;
        }
        if(isUniqueMeetingName(meetingToAdd)){
            return 2;
        }
        return 0;
    }

    public Meeting deleteMeeting(Long id){
        List<Meeting> meetings = getAllMeetings();
        for(Meeting meeting: meetings){
            if (id.equals(meeting.getId())){
                meetings.remove(meeting);
                break;
            }
        }
        meetingRepository.writeMeetingData(meetings);
        return null;
    }

    public boolean removePersonFromMeeting(Long meetingId, Long id){
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

    private Long generateUniqueSerialMeetingId(List<Meeting> meetings){
        Long maxValue = Long.MIN_VALUE;
        long generatedId = 0L;
        if(meetings.size() > 0){
            for (Meeting meeting : meetings) {
                if (meeting.getId()
                        .compareTo(maxValue) > 0) {
                    maxValue = meeting.getId();
                }
            }
            generatedId = maxValue + 1;
        }
        return generatedId;
    }

}
