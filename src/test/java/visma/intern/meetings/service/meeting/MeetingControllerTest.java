package visma.intern.meetings.service.meeting;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import visma.intern.meetings.model.atendee.Attendee;
import visma.intern.meetings.model.meeting.Meeting;
import visma.intern.meetings.model.meeting.enums.Category;
import visma.intern.meetings.model.meeting.enums.Type;
import visma.intern.meetings.repository.meeting.MeetingRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeetingService.class)
public class MeetingControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    MeetingRepository meetingRepository;

    LocalDateTime dateTime = LocalDateTime.parse("2022-06-10T10:00:00");
    LocalDateTime dateTime1 = LocalDateTime.parse("2022-06-10T11:00:00");

    Attendee ATTENDEE_1 = new Attendee(1L,"name","surname","jt");
    Attendee ATTENDEE_2 = new Attendee(2L,"name","surname","jt");
    Attendee ATTENDEE_3 = new Attendee(3L,"name","surname","jt");
    Attendee ATTENDEE_4 = new Attendee(4L,"name","surname","jt");
    Attendee ATTENDEE_5 = new Attendee(5L,"name","surname","jt");
    Attendee ATTENDEE_6 = new Attendee(6L,"name","surname","jt");
    Attendee ATTENDEE_7 = new Attendee(7L,"name","surname","jt");
    Attendee ATTENDEE_8 = new Attendee(8L,"name","surname","jt");

    Meeting MEETING_1 = new Meeting("name",ATTENDEE_1,"description",
            Category.HUB, Type.IN_PERSON, dateTime, dateTime1, new ArrayList<>());
    Meeting MEETING_2 = new Meeting("name1",ATTENDEE_2,"description",
            Category.HUB, Type.IN_PERSON, dateTime, dateTime1, List.of(ATTENDEE_5,
            ATTENDEE_6, ATTENDEE_7, ATTENDEE_8));
    Meeting MEETING_3 = new Meeting("name2",ATTENDEE_3,"description",
            Category.HUB, Type.IN_PERSON, dateTime, dateTime1, List.of(ATTENDEE_1,
            ATTENDEE_2));

    @Test
    public void getAllMeetings_success() throws Exception{
        List<Meeting> meetings = new ArrayList<>(Arrays.asList(
                MEETING_1, MEETING_2, MEETING_3));

        Mockito.when(meetingRepository.readMeetingData()).thenReturn(meetings);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/meeting/get/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("name2")));


    }
}
