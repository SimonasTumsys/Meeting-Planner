package lt.bit.meetings.model.atendee;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Attendee {
    private Long id;
    private String name;
    private String surname;
    private String jobTitle;
    private String email;
    private String password;
//    private Set<Group> group;

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
