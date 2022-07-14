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


    public Long generateUniqueSerialMeetingId(List<Meeting> meetings){
        Long maxValue = Long.MIN_VALUE;
        long generatedId = 0L;
        if(meetings.size() > 0){
            for (Meeting meeting : meetings) {
                if (meeting.getId()
                        .compareTo(maxValue) > 0) {
                    maxValue = meeting.getId();
                }
            }
            generatedId = maxValue + 1;
        }
        return generatedId;
    }
}
