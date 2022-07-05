package visma.intern.meetings.model.atendee;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Attendee {
    private Long id;
    private String name;
    private String surname;
    private String jobTitle;
    private String email;
    private String password;
}
