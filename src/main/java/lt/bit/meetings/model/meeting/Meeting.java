package lt.bit.meetings.model.meeting;

import lombok.*;
import lt.bit.meetings.model.meeting.enums.Category;
import lt.bit.meetings.model.meeting.enums.Type;
import lt.bit.meetings.model.atendee.Attendee;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Meeting {
    private Long id;
    private String name;
    private Attendee responsiblePerson;
    private String description;
    private Category category;
    private Type type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Attendee> attendees;

}
