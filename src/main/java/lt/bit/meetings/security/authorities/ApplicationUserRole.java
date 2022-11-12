package lt.bit.meetings.security.authorities;

import com.google.common.collect.Sets;
import lombok.Getter;
import lt.bit.meetings.security.authorities.ApplicationUserPermission;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static lt.bit.meetings.security.authorities.ApplicationUserPermission.*;


@Getter
public enum ApplicationUserRole {
    ATTENDEE(Sets.newHashSet()),
    RESPONSIBLEPERSON(Sets.newHashSet(MEETINGS_READ, MEETINGS_WRITE)),
    ADMIN(Sets.newHashSet());

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
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