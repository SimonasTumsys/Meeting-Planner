package lt.bit.meetings.controller.meeting;

import lt.bit.meetings.exception.ApiException;
import lt.bit.meetings.model.atendee.Attendee;
import lt.bit.meetings.model.meeting.Meeting;
import lt.bit.meetings.service.meeting.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
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
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESPONSIBLEPERSON')")
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

        if(meetings.size() == 0){
            throw new ApiException("No meetings found by these criteria!", 5000);
        }
        return new ResponseEntity<>(meetings, HttpStatus.OK);
    }

    @PutMapping("/addAttendee/{meetingId}")
    @PreAuthorize("hasAuthority('meetings:write')")
    public ResponseEntity<String> addNewAttendeeToMeeting(
            @RequestBody Attendee attendee,
            @PathVariable("meetingId") Long meetingId){

        String responseIndicator =
                meetingService.getResponseIndicatorForAddingAttendees(attendee, meetingId);

        switch (responseIndicator){
            case "responsibleInThisMeeting" -> {
                throw new ApiException("Cannot add this attendee" +
                        " because he/she is responsible for this meeting", 4020);
            }
            case "responsibleInAnotherMeetingNow" -> {
                throw new ApiException("Cannot add this attendee" +
                    " because he/she is responsible for another meeting, which is" +
                    " overlapping with this one", 4021);
            }
            case "success" -> {
                String warningMessage = meetingService.
                        warningIsInAnotherMeeting(attendee, meetingId);
                meetingService.addAttendeeAfterChecks(attendee, meetingId);
                return new ResponseEntity<>(warningMessage, HttpStatus.OK);
            }
            default -> {
                throw new ApiException("The person you are trying to add" +
                        " is already in this meeting", 4002);
            }
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('meetings:write')")
    public ResponseEntity<Meeting> addNewMeeting(
                        @RequestBody Meeting meetingToAdd){
        List<Meeting> allMeetings = meetingService.getAllMeetings();
        String responseIndicator =
                meetingService.getResponseIndicatorForAddingMeetings(
                        meetingToAdd, allMeetings);
        switch(responseIndicator){
            case "isResponsibleInOtherMeetingNow" -> {
                throw new ApiException("Cannot create this meeting because responsible person" +
                        " is responsible for another meeting, which is" +
                        " overlapping with this one", 4022);
            }
            case "success" -> {
                meetingService.addMeetingAfterChecks(meetingToAdd);
                return new ResponseEntity<>(meetingToAdd,
                        HttpStatus.OK);
            }
            default -> {
                throw new ApiException("Meeting with this name already exists", 4001);
            }
        }
    }

    //TODO - unauthorized exception handling
    @DeleteMapping("/delete/{meetingId}")
    @PreAuthorize("hasAuthority('meetings:write')")
    public ResponseEntity<String> deleteMeeting(
            @PathVariable("meetingId") Long meetingId){
        boolean success = meetingService.deleteMeeting(meetingId);
        if(success){
            return new ResponseEntity<>(
                   "Meeting deleted successfully", HttpStatus.OK);
        }
        throw new ApiException("Meeting with this ID does not exist", 4003);
    }

    //TODO respPerson cannot remove himself
    @PutMapping("/removeAttendee/{meetingId}")
    @PreAuthorize("hasAuthority('meetings:write')")
    public ResponseEntity<Meeting> removePersonFromMeeting(
            @PathVariable("meetingId") Long meetingId,
            @RequestParam(name = "attendeeId") Long attendeeId){
        boolean success =
                meetingService.removeAttendeeFromMeeting(meetingId, attendeeId);
        if(success){
            return new ResponseEntity<>(meetingService.getMeetingById(meetingId),
                    HttpStatus.OK);
        }
        throw new ApiException("Attendee with this ID does not exist", 4004);
    }
}