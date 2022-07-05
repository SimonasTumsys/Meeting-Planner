package visma.intern.meetings.model.meeting;

import lombok.*;
import visma.intern.meetings.model.atendee.Attendee;
import visma.intern.meetings.model.meeting.enums.Category;
import visma.intern.meetings.model.meeting.enums.Type;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Meeting {
    private String name;
    private Attendee responsiblePerson;
    private String description;
    private Category category;
    private Type type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Attendee> attendees;

}
