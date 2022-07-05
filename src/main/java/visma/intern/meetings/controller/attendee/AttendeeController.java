package visma.intern.meetings.controller.attendee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import visma.intern.meetings.model.atendee.Attendee;
import visma.intern.meetings.service.attendee.AttendeeService;

import java.util.List;

@RestController
@RequestMapping("/attendee")
public class AttendeeController {
    private final AttendeeService attendeeService;

    @Autowired
    public AttendeeController(AttendeeService attendeeService){
        this.attendeeService = attendeeService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Attendee>> getAllAttendeesFromDb(){
        return new ResponseEntity<>(attendeeService.getAllAttendees(),
                HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewAttendeeToDb(
            @RequestBody Attendee attendee){
        if(attendeeService.isUniqueAttendee(attendee)) {
            Attendee newAttendee = attendeeService.addAttendeeToDb(attendee);
            return new ResponseEntity<>(
                    "The attendee was successfully added to the database",
                    HttpStatus.OK);
        }
        return new ResponseEntity<>("Attendee with this e-mail is" +
                "already in the database", HttpStatus.BAD_REQUEST);
    }

    //865755663 - Mindaugas

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeAttendeeFromDb(
            @PathVariable("id") Long id){
        if(attendeeService.attendeeIdExists(id)) {
            attendeeService.removeAttendeeFromDb(id);
            return new ResponseEntity<>("Attendee removed successfully",
                    HttpStatus.OK);
        }
        return new ResponseEntity<>("Attendee with this ID not found",
                HttpStatus.BAD_REQUEST);
    }
}
