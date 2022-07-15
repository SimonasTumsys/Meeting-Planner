package lt.bit.meetings.security;

import lombok.AllArgsConstructor;
import lt.bit.meetings.model.atendee.Attendee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;



// NOT USED RN


@AllArgsConstructor
public class AttendeePrincipal implements UserDetails {

    private Attendee attendee;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return attendee.getPassword();
    }

    @Override
    public String getUsername() {
        return attendee.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
