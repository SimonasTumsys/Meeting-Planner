package lt.bit.meetings.security;

import lt.bit.meetings.model.atendee.Attendee;
import lt.bit.meetings.repository.attendee.AttendeeRepository;
import lt.bit.meetings.service.attendee.AttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AttendeeDetailsService implements UserDetailsService {

    @Autowired
    private AttendeeRepository attendeeRepository;
    @Autowired
    private AttendeeService attendeeService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         Attendee attendee = attendeeService.getAttendeeByEmail(email);
         if(attendee == null){
             throw new UsernameNotFoundException("Attendee 404");
         }
         return new AttendeePrincipal(attendee);
    }
}
