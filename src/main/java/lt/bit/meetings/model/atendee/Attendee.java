package lt.bit.meetings.model.atendee;

import lombok.*;

import java.util.Set;

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
}
