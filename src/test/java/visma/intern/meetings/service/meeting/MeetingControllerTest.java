package visma.intern.meetings.service.meeting;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import visma.intern.meetings.controller.meeting.MeetingController;
import visma.intern.meetings.model.atendee.Attendee;
import visma.intern.meetings.model.meeting.Meeting;
import visma.intern.meetings.model.meeting.enums.Category;
import visma.intern.meetings.model.meeting.enums.Type;
import visma.intern.meetings.repository.meeting.MeetingRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeetingController.class)
public class MeetingControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    MeetingRepository meetingRepository;
    @MockBean
    MeetingService meetingService;

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

    Meeting MEETING_1 = new Meeting("name",ATTENDEE_1,"description1",
            Category.HUB, Type.IN_PERSON, dateTime, dateTime1, new ArrayList<>());
    Meeting MEETING_2 = new Meeting("name1",ATTENDEE_2,"description1",
            Category.CODE_MONKEY, Type.IN_PERSON, dateTime, dateTime1, List.of(ATTENDEE_5,
            ATTENDEE_6, ATTENDEE_7, ATTENDEE_8));
    Meeting MEETING_3 = new Meeting("name2",ATTENDEE_3,"description",
            Category.TEAM_BUILDING, Type.IN_PERSON, dateTime, dateTime1, List.of(ATTENDEE_1,
            ATTENDEE_2));

    @Test
    public void getAllMeetings_success() throws Exception{
        List<Meeting> meetings = List.of(
                MEETING_1, MEETING_2, MEETING_3);

        Mockito.when(meetingService.getAllMeetings()).thenReturn(meetings);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/meeting/get/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("name2")));
    }

//    @Test
//    public void searchByDescription_success() throws Exception{
//        Mockito.when(meetingService.searchByDescription(MEETING_1.getDescription()))
//                .thenReturn(List.of(MEETING_1, MEETING_2));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/meeting/get/description/description1")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$[0].name", is("name")));
//    }
//
//    @Test
//    public void searchByResponsiblePerson_success() throws Exception{
//        Mockito.when(meetingService.searchByResponsiblePerson(
//                MEETING_2.getResponsiblePerson().getId()))
//                .thenReturn(List.of(MEETING_2));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/meeting/get/byResponsiblePerson/2")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$[0].name", is("name1")));
//    }
//
//    @Test
//    public void searchByCategory_success() throws Exception{
//        Mockito.when(meetingService
//                .searchByCategory(MEETING_3.getCategory()
//                        .toString().toLowerCase()))
//                .thenReturn(List.of(MEETING_3));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/meeting/get/category/teambuilding")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].name", is("name2")));
//    }
//
//    @Test
//    public void searchByType_success() throws Exception{
//        Mockito.when(meetingService
//                        .searchByType(MEETING_1.getType()
//                                .toString().toLowerCase()))
//                .thenReturn(List.of(MEETING_1, MEETING_2, MEETING_3));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/meeting/get/type/inperson")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$", hasSize(3)))
//                .andExpect(jsonPath("$[0].name", is("name")));
//    }
}
