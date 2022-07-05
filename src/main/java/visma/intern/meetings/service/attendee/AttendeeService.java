package visma.intern.meetings.service.attendee;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import visma.intern.meetings.model.atendee.Attendee;
import visma.intern.meetings.repository.attendee.AttendeeRepository;

import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class AttendeeService {
    private final AttendeeRepository attendeeRepository;

    public void removeAttendeeFromDb(Long id){
        List<Attendee> attendees = getAllAttendees();
        attendees.removeIf(attendee -> attendee.getId().equals(id));
        attendeeRepository.writeAttendeeData(attendees);
    }

    public Attendee addAttendeeToDb(Attendee attendee){
        List<Attendee> attendees = getAllAttendees();
        attendee.setId(generateSerialUniqueId(attendees));
        attendees.add(attendee);
        return attendeeRepository.writeAttendeeData(attendees);
    }

    private Long generateSerialUniqueId(List<Attendee> attendees){
        Long maxValue = Long.MIN_VALUE;
        long generatedId = 0L;
        if(attendees.size() > 0){
            for (Attendee attendee : attendees) {
                if (attendee.getId()
                        .compareTo(maxValue) > 0) {
                    maxValue = attendee.getId();
                }
            }
            generatedId = maxValue + 1;
        }
        return generatedId;
    }

    public List<Attendee> getAllAttendees(){
        return attendeeRepository.readAttendeeData();
    }

    public boolean isUniqueAttendee(Attendee attendee){
        HashSet<String> uniqueEmails = new HashSet<>();
        for(Attendee a : getAllAttendees()){
            uniqueEmails.add(a.getEmail());
        }
        return uniqueEmails.add(attendee.getEmail());
    }

    public boolean attendeeIdExists(Long id){
        for(Attendee a : getAllAttendees()){
            if(a.getId().equals(id)){
                return true;
            }
        }
        return false;
    }
}