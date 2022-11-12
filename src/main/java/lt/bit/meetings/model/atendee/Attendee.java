package lt.bit.meetings.model.atendee;

import lombok.*;
import lt.bit.meetings.security.authorities.ApplicationUserRole;

import java.util.List;
import java.util.Set;

@Data
public class Attendee {
    private Long id;
    private String name;
    private String surname;
    private String profilePicture;
    private String jobTitle;
    private String email;
    private String password;
    private Set<ApplicationUserRole> userRoles;

    public Long generateSerialUniqueAttendeeId(List<Attendee> attendees){
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
}
