package lt.bit.meetings.security;

import lt.bit.meetings.model.atendee.Attendee;
import lt.bit.meetings.repository.attendee.AttendeeRepository;
import lt.bit.meetings.security.authorities.ApplicationUserRole;
import lt.bit.meetings.service.attendee.AttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Service
public class AttendeeDetailsService implements UserDetailsService {

    @Autowired
    private AttendeeRepository attendeeRepository;
    @Autowired
    private AttendeeService attendeeService;

    @Override
    public UserDetails loadUserByUsername(String email)  {
         Attendee attendee = attendeeService.getAttendeeByEmail(email);
         if(attendee == null){
             throw new UsernameNotFoundException(email);
         }
        return User.withUsername(attendee.getEmail())
                .password(attendee.getPassword())
                .authorities(getAllAuthoritiesFromRoles(attendee)).build();
    }

    private Collection<GrantedAuthority> getAllAuthoritiesFromRoles(Attendee attendee){
        Set<ApplicationUserRole> userRoles = attendee.getUserRoles();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(ApplicationUserRole role : userRoles){
            authorities.addAll(role.getGrantedAuthorities());
        }
        return authorities;
    }
}
