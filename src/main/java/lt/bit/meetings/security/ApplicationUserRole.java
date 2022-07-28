package lt.bit.meetings.security;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static lt.bit.meetings.security.ApplicationUserPermission.*;


@Getter
public enum ApplicationUserRole {
    ATTENDEE(Sets.newHashSet()),
    RESPONSIBLEPERSON(Sets.newHashSet(MEETINGS_READ, MEETINGS_WRITE)),
    ADMIN(Sets.newHashSet(
            ATTENDEES_READ, ATTENDEES_WRITE,
            MEETINGS_READ, MEETINGS_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    //TODO responsiblePerson role
    ApplicationUserRole(Set<ApplicationUserPermission> permissions,
                        Long userId, Long meetingId){
        this.permissions = permissions;

    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}