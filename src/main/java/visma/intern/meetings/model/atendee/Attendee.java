package visma.intern.meetings.model.atendee;

import lombok.*;

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
}
