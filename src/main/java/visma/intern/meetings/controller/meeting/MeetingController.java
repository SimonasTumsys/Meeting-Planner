package visma.intern.meetings.controller.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import visma.intern.meetings.model.atendee.Attendee;
import visma.intern.meetings.model.meeting.Meeting;
import visma.intern.meetings.repository.meeting.MeetingRepository;
import visma.intern.meetings.service.meeting.MeetingService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/meeting")
public class MeetingController {
    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService,
                             MeetingRepository meetingRepository) {
        this.meetingService = meetingService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Meeting>> getAllMeetings() {
        List<Meeting> meetings = meetingService.getAllMeetings();
        return new ResponseEntity<>(meetings, HttpStatus.OK);
    }

    @GetMapping("/get/description/{desc}")
    public ResponseEntity<List<Meeting>> filterByDescription(
            @PathVariable("desc") String desc){
        return new ResponseEntity<>(
                meetingService.searchByDescription(desc), HttpStatus.OK);
    }

    @GetMapping("/get/byResponsiblePerson/{id}")
    public ResponseEntity<List<Meeting>> filterByResponsiblePerson(
            @PathVariable("id") Long id){
        return new ResponseEntity<>(
                meetingService.searchByResponsiblePerson(id),
                HttpStatus.OK);
    }

    @GetMapping("/get/category/{cat}")
    public ResponseEntity<List<Meeting>> filterByCategory(
            @PathVariable("cat") String cat){
        return new ResponseEntity<>(
                meetingService.searchByCategory(cat),
                HttpStatus.OK);
    }

    @GetMapping("/get/type/{type}")
    public ResponseEntity<List<Meeting>> filterByType(
            @PathVariable("type") String type){
        return new ResponseEntity<>(
                meetingService.searchByType(type),
                HttpStatus.OK);
    }

    @GetMapping("/get/date/from/{dateFrom}")
    public ResponseEntity<List<Meeting>> filterByDateFrom(
            @PathVariable("dateFrom") String dateFrom) {
        return new ResponseEntity<>(meetingService.searchByDate(dateFrom),
                HttpStatus.OK);
    }

    @GetMapping("/get/date/between/{dateFrom}/{dateTo}")
    public ResponseEntity<List<Meeting>> filterByDateBetween(
            @PathVariable("dateFrom") String dateFrom,
            @PathVariable("dateTo") String dateTo) {
        return new ResponseEntity<>(meetingService.searchByDate(dateFrom, dateTo),
                HttpStatus.OK);
    }

    @GetMapping("/get/date/upTo/{dateTo}")
    public ResponseEntity<List<Meeting>> filterByDateUpTo(
            @PathVariable("dateTo") String dateTo) {
        return new ResponseEntity<>(meetingService.searchByDateTo(dateTo),
                HttpStatus.OK);
    }

    @GetMapping("/get/attendees/from/{nr}")
    public ResponseEntity<List<Meeting>> filterByNumberOfAttendeesFrom(
            @PathVariable("nr") int nr){
        return new ResponseEntity<>(meetingService.searchByNumberOfAttendeesFrom(nr),
                HttpStatus.OK);
    }

    @GetMapping("/get/attendees/to/{nr}")
    public ResponseEntity<List<Meeting>> filterByNumberOfAttendeesTo(
            @PathVariable("nr") int nr){
        return new ResponseEntity<>(meetingService.searchByNumberOfAttendeesTo(nr),
                HttpStatus.OK);
    }

    @PutMapping("/addAttendee/{time}/{meetingName}")
    public ResponseEntity<Meeting> addNewAttendeeToMeeting(
            @RequestBody Attendee attendee,
            @PathVariable("time") String time,
            @PathVariable("meetingName") String meetingName){

        LocalDateTime timeDt = LocalDateTime.parse(time);
        return new ResponseEntity<>(
                meetingService.addAttendeeToMeeting(attendee, timeDt, meetingName),
                    HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Meeting> addNewMeeting(
                        @RequestBody Meeting meeting){
        if(meetingService.isUniqueMeetingName(meeting)){
            Meeting newMeeting = meetingService.addMeeting(meeting);
            return new ResponseEntity<>(newMeeting, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Meeting> deleteMeeting(
            @PathVariable("name") String meetingName){
            return new ResponseEntity<>(
                    meetingService.deleteMeeting(meetingName), HttpStatus.OK);
    }

    @PutMapping("/removeAttendee/{meetingName}")
    public ResponseEntity<Meeting> removePersonFromMeeting(
            @PathVariable("meetingName") String name,
            @RequestBody Attendee attendee){
        return new ResponseEntity<>(
                meetingService.removePersonFromMeeting(name, attendee), HttpStatus.OK);
    }
}
