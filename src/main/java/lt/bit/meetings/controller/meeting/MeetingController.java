package lt.bit.meetings.controller.meeting;

import lt.bit.meetings.model.atendee.Attendee;
import lt.bit.meetings.model.meeting.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lt.bit.meetings.service.meeting.MeetingService;

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
            @RequestParam(name = "resId", required = false) Long resId,
            @RequestParam(name = "category", required = false) String cat,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "dateFrom", required = false) String dateFrom,
            @RequestParam(name = "dateTo", required = false) String dateTo,
            @RequestParam(name = "countTo", required = false) Integer countTo,
            @RequestParam(name = "countFrom", required = false) Integer countFrom){
        List<Meeting> meetings = meetingService.getAllMeetings();
        if(desc != null){
            meetings = meetingService.getMeetingsByDescription(desc, meetings);
        }
        if(resId != null){
            meetings = meetingService.getMeetingsByResponsiblePerson(resId, meetings);
        }
        if(cat != null){
            meetings = meetingService.getMeetingsByCategory(cat, meetings);
        }
        if(type != null){
            meetings = meetingService.getMeetingsByType(type, meetings);
        }
        if(dateFrom != null){
            meetings = meetingService.getMeetingsByDateFrom(dateFrom, meetings);
        }
        if(dateTo != null){
            meetings = meetingService.getMeetingsByDateTo(dateTo, meetings);
        }
        if(countFrom != null){
            meetings = meetingService.getMeetingsByNumberOfAttendeesFrom(countFrom, meetings);
        }
        if(countTo != null){
            meetings = meetingService.getMeetingsByNumberOfAttendeesTo(countTo, meetings);
        }
        return new ResponseEntity<>(meetings, HttpStatus.OK);
    }

    @PutMapping("/addAttendee/{meetingId}")
    public ResponseEntity<String> addNewAttendeeToMeeting(
            @RequestBody Attendee attendee,
            @PathVariable("meetingId") Long meetingId){

        String responseIndicator =
                meetingService.getResponseIndicatorForAddingAttendees(attendee, meetingId);

        switch (responseIndicator){
            case "responsibleInThisMeeting" -> {
                return new ResponseEntity<>("Cannot add this attendee" +
                    " because he/she is responsible for this meeting",
                    HttpStatus.FORBIDDEN);
            }
            case "responsibleInAnotherMeetingNow" -> {
                return new ResponseEntity<>("Cannot add this attendee" +
                    " because he/she is responsible for another meeting, which is" +
                    " overlapping with this one",
                    HttpStatus.FORBIDDEN);
            }
            case "success" -> {
                String warningMessage = meetingService.
                        warningIsInAnotherMeeting(attendee, meetingId);
                meetingService.addAttendeeAfterChecks(attendee, meetingId);
                return new ResponseEntity<>(warningMessage, HttpStatus.OK);
            }
            default -> {
                return new ResponseEntity<>("The person you are trying to add" +
                        " is already in this meeting", HttpStatus.FORBIDDEN);
            }
        }

    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewMeeting(
                        @RequestBody Meeting meetingToAdd){
        List<Meeting> allMeetings = meetingService.getAllMeetings();
        String responseIndicator =
                meetingService.getResponseIndicatorForAddingMeetings(
                        meetingToAdd, allMeetings);
        switch(responseIndicator){
            case "isResponsibleInOtherMeetingNow" -> {
                return new ResponseEntity<>("Failed. The responsible person " +
                        "of this meeting is responsible for another meeting " +
                        "that is overlapping with this one",
                        HttpStatus.FORBIDDEN);
            }
            case "success" -> {
                meetingService.addMeetingAfterChecks(meetingToAdd);
                return new ResponseEntity<>("Meeting added successfully!",
                        HttpStatus.OK);
            }
            default -> {
                return new ResponseEntity<>("Meeting with this name already exists",
                        HttpStatus.FORBIDDEN);
            }
        }
    }

    @DeleteMapping("/delete/{meetingId}")
    public ResponseEntity<String> deleteMeeting(
            @PathVariable("meetingId") Long meetingId){
        boolean success = meetingService.deleteMeeting(meetingId);
        if(success){
            return new ResponseEntity<>(
                   "Meeting deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("No such meeting!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/removeAttendee/{meetingId}")
    public ResponseEntity<String> removePersonFromMeeting(
            @PathVariable("meetingId") Long meetingId,
            @RequestParam(name = "attendeeId") Long attendeeId){
        boolean success =
                meetingService.removeAttendeeFromMeeting(meetingId, attendeeId);
        if(success){
            return new ResponseEntity<>("Attendee removed successfully",
                    HttpStatus.OK);
        }
        return new ResponseEntity<>("No such attendee in this meeting!",
                HttpStatus.BAD_REQUEST);
    }
}