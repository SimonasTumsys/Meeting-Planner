package visma.intern.meetings.service.meeting;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import visma.intern.meetings.model.atendee.Attendee;
import visma.intern.meetings.model.meeting.Meeting;
import visma.intern.meetings.repository.meeting.MeetingRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;

    public Meeting addMeeting(Meeting meeting){
        List<Meeting> meetings = getAllMeetings();
        if(isUniqueMeeting(meeting) && isUniqueMeetingName(meeting)){
            meetings.add(meeting);
            return meetingRepository.writeMeetingData(meetings);
        }
        return null;
    }

    public Meeting addAttendeeToMeeting(Attendee attendee,
                                        LocalDateTime time,
                                        String meetingName){
        List<Meeting> meetings = meetingRepository.readMeetingData();
        for(Meeting meeting : meetings){
            if(meeting.getStartDate().compareTo(time) <= 0 &&
            meeting.getEndDate().compareTo(time) >= 0
            && meeting.getName().equalsIgnoreCase(meetingName)){
                List<Attendee> attendees = meeting.getAttendees();
                if(isUniqueAttendeeInMeeting(meeting, attendee) &&
                !meeting.getResponsiblePerson()
                        .toString().equals(attendee.toString())) {
                    generateWarningMessage(meetings, attendee);
                    attendees.add(attendee);
                    meeting.setAttendees(attendees);
                    }
                }
            }
        meetingRepository.writeMeetingData(meetings);
        return null;
    }

    private void generateWarningMessage(List<Meeting> meetings,
                                       Attendee attendee){
        for(Meeting meeting : meetings){
            boolean isInMeeting = false;
            List<Attendee> attendees = meeting.getAttendees();
            for(Attendee a : attendees) {
                isInMeeting = a.getId().equals(attendee.getId());
            }
            if (isInMeeting){
                String meetingName = meeting.getName();
                String meetingStart = meeting.getStartDate().toString()
                        .replace('T', ' ');
                String meetingEnd = meeting.getEndDate().toString()
                        .replace('T', ' ');

                System.out.println("WARNING! " +
                        "The person you are trying to add to this meeting " +
                        "is already in a meeting called " + meetingName + "," +
                        " which starts at " + meetingStart +
                        " and ends at " + meetingEnd + ".");
            }
        }
    }

    public Meeting deleteMeeting(String meetingName){
        List<Meeting> meetings = meetingRepository.readMeetingData();
        Meeting meetingToDelete = meetings.stream().filter(meeting ->
                meeting.getName().equalsIgnoreCase(meetingName)).toList().get(0);
        meetings.remove(meetingToDelete);
        meetingRepository.writeMeetingData(meetings);
        return null;
    }

    public Meeting removePersonFromMeeting(String meetingName, Attendee attendee){
        List<Meeting> meetings = meetingRepository.readMeetingData();
        Meeting meeting = searchMeetingByName(meetingName);
        Attendee attendeeToRemove = null;
        for(Meeting m : meetings){
            if(m.toString().equals(meeting.toString())){
                try{
                    attendeeToRemove = m.getAttendees().stream().filter(a ->
                        a.getId().equals(attendee.getId())).toList().get(0);
                } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("No such attendee!");
                }
                List<Attendee> attendees = m.getAttendees();
                if(!m.getResponsiblePerson().toString()
                        .equals(attendee.toString())){
                    attendees.remove(attendeeToRemove);
                    m.setAttendees(attendees);
                }
            }
        }
        meetingRepository.writeMeetingData(meetings);
        return null;
    }

    public Meeting searchMeetingByName(String name){
        List<Meeting> meetings = meetingRepository.readMeetingData();
        return meetings.stream().filter(meeting ->
                 meeting.getName()
                .equalsIgnoreCase(name)).toList().get(0);
    }

    public List<Meeting> searchByDescription(String description, List<Meeting> meetings){
        return meetings.stream().filter(meeting ->
                 meeting.getDescription().toLowerCase()
                .contains(description.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Meeting> searchByResponsiblePerson(Long id, List<Meeting> meetings){
        return meetings.stream().filter(meeting ->
                  meeting.getResponsiblePerson().getId().equals(id))
                 .collect(Collectors.toList());
    }

    public List<Meeting> searchByCategory(String cat, List<Meeting> meetings){
        return meetings.stream().filter(meeting ->
                 meeting.getCategory().toString()
                .equalsIgnoreCase(cat)).collect(Collectors.toList());
    }

    public List<Meeting> searchByType(String type, List<Meeting> meetings){
        return meetings.stream().filter(meeting ->
                 meeting.getType().toString()
                .equalsIgnoreCase(type)).collect(Collectors.toList());
    }

    public List<Meeting> searchByDate(String dateFrom, List<Meeting> meetings) {
        LocalDateTime dateFromDt = LocalDateTime.parse(dateFrom);

        return meetings.stream().filter(meeting ->
                        meeting.getStartDate().compareTo(dateFromDt) >= 0)
                .collect(Collectors.toList());
    }

    public List<Meeting> searchByDateTo(String dateTo, List<Meeting> meetings){
        LocalDateTime dateToDt = LocalDateTime.parse(dateTo);

        return meetings.stream().filter(meeting ->
                 meeting.getEndDate().compareTo(dateToDt) <= 0)
                .collect(Collectors.toList());
    }

    public List<Meeting> searchByNumberOfAttendeesFrom(int nr, List<Meeting> meetings){
        return meetings.stream().filter(meeting ->
                 meeting.getAttendees() != null &&
                 meeting.getAttendees().size() >= nr)
                .collect(Collectors.toList());
    }

    public List<Meeting> searchByNumberOfAttendeesTo(int nr, List<Meeting> meetings){
        return meetings.stream().filter(meeting ->
                 meeting.getAttendees() != null &&
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

    public boolean isUniqueMeeting(Meeting newMeeting){
        HashSet<String> uniqueMeetings = new HashSet<>();
        List<Meeting> meetings = meetingRepository.readMeetingData();

        meetings.forEach(m -> uniqueMeetings.add(m.toString()));
        return uniqueMeetings.add(newMeeting.toString());
    }

    public boolean isUniqueMeetingName(Meeting newMeeting){
        HashSet<String> uniqueNames = new HashSet<>();
        List<Meeting> meetings = meetingRepository.readMeetingData();

        meetings.forEach(m -> uniqueNames.add(m.getName()));
        return uniqueNames.add(newMeeting.getName());
    }
}
