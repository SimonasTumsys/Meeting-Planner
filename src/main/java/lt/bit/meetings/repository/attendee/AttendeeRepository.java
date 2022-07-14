package lt.bit.meetings.repository.attendee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import lt.bit.meetings.model.atendee.Attendee;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Repository
@NoArgsConstructor
public class AttendeeRepository {
    private static final String ATTENDEES_JSON =
            "src/main/resources/json/attendees.json";
    private final ObjectMapper mapper = createObjectMapper();

    public Attendee writeAttendeeData(List<Attendee> attendees){
        try {
            mapper.writeValue(Paths.get(ATTENDEES_JSON)
                    .toFile(), attendees);
        } catch (IOException e){
            System.out.println("Attendee JSON file not found");
        }
        return null;
    }

    public List<Attendee> readAttendeeData(){
        try {
            return mapper.readValue(
                    Paths.get(ATTENDEES_JSON).toFile(),
                    new TypeReference<>() {});
        } catch (IOException e){
            System.out.println("Attendee JSON file not found");
        }
        return null;
    }

    private ObjectMapper createObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

}
