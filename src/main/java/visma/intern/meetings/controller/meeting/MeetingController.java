package visma.intern.meetings.controller.meeting;

import lombok.Getter;
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
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Meeting>> getFilterMeetings(
            @RequestParam(name = "description", required = false) String desc,
            @RequestParam(name = "resId", required = false) Long id,
            @RequestParam(name = "category", required = false) String cat,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "dateFrom", required = false) String dateFrom,
            @RequestParam(name = "dateTo", required = false) String dateTo,
            @RequestParam(name = "countTo", required = false) Integer countTo,
            @RequestParam(name = "countFrom", required = false) Integer countFrom){
        List<Meeting> meetings = meetingService.getAllMeetings();
        if(desc != null){
            meetings = meetingService.searchByDescription(desc, meetings);
        }
        if(id != null){
            meetings = meetingService.searchByResponsiblePerson(id, meetings);
        }
        if(cat != null){
            meetings = meetingService.searchByCategory(cat, meetings);
        }
        if(type != null){
            meetings = meetingService.searchByType(type, meetings);
        }
        if(dateFrom != null){
            meetings = meetingService.searchByDate(dateFrom, meetings);
        }
        if(dateTo != null){
            meetings = meetingService.searchByDateTo(dateTo, meetings);
        }
        if(countFrom != null){
            meetings = meetingService.searchByNumberOfAttendeesFrom(countFrom, meetings);
        }
        if(countTo != null){
            meetings = meetingService.searchByNumberOfAttendeesTo(countTo, meetings);
        }
        return new ResponseEntity<>(meetings, HttpStatus.OK);
    }

    @PutMapping("/addAttendee/{time}/{meetingName}")
    public ResponseEntity<String> addNewAttendeeToMeeting(
            @RequestBody Attendee attendee,
            @PathVariable("time") String time,
            @PathVariable("meetingName") String meetingName){

        LocalDateTime timeDt = LocalDateTime.parse(time);
        String warningMessage =
                meetingService.addAttendeeToMeeting(attendee, timeDt, meetingName);
        return new ResponseEntity<>(warningMessage,
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
