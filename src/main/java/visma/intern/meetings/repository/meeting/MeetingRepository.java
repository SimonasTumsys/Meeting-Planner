package visma.intern.meetings.repository.meeting;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import visma.intern.meetings.model.meeting.Meeting;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Repository
@NoArgsConstructor
public class MeetingRepository {
    private static final String MEETINGS_JSON =
            "src/main/resources/json/meetings.json";
    private final ObjectMapper mapper = createObjectMapper();

    public Meeting writeMeetingData(List<Meeting> meetings){
        try {
            mapper.writeValue(Paths.get(MEETINGS_JSON)
                    .toFile(), meetings);
        } catch (IOException e){
            System.out.println("Meeting JSON file not found");
        }
        return null;
    }

    public List<Meeting> readMeetingData(){
        try {
            return mapper.readValue(
                    Paths.get(MEETINGS_JSON).toFile(),
                    new TypeReference<>() {});
        } catch (IOException e){
            System.out.println("Meeting JSON file not found");
        }
        return null;
    }

    private ObjectMapper createObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

}
