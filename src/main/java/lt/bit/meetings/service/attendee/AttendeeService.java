package lt.bit.meetings.service.attendee;

import lombok.AllArgsConstructor;
import lt.bit.meetings.model.atendee.Attendee;
import lt.bit.meetings.repository.attendee.AttendeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class AttendeeService {
    @Autowired
    private final AttendeeRepository attendeeRepository;

    public Attendee getAttendeeById(Long id) {
        if(attendeeIdExists(id)){
            for(Attendee attendee : getAllAttendees()){
                if(id.equals(attendee.getId())){
                    return attendee;
                }
            }
        }
        return null;
    }

    public Attendee getAttendeeByEmail(String email){
        List<Attendee> attendees = getAllAttendees();
        for(Attendee attendee : attendees){
            if(email.equalsIgnoreCase(attendee.getEmail())){
                return attendee;
            }
        }
        return null;
    }

    public void removeAttendeeFromDb(Long id){
        List<Attendee> attendees = getAllAttendees();
        attendees.removeIf(attendee -> attendee.getId().equals(id));
        attendeeRepository.writeAttendeeData(attendees);
    }

    public Attendee addAttendeeToDb(Attendee attendee){
        List<Attendee> attendees = getAllAttendees();
        attendee.setId(attendee
                .generateSerialUniqueAttendeeId(attendees));
        attendees.add(attendee);
        return attendeeRepository.writeAttendeeData(attendees);
    }

    public List<Attendee> getAllAttendees(){
        return attendeeRepository.readAttendeeData();
    }

    public boolean isUniqueAttendeeEmail(Attendee attendee){
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