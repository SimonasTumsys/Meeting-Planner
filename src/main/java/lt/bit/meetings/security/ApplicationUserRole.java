package lt.bit.meetings.security;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

import static lt.bit.meetings.security.ApplicationUserPermission.*;


@Getter
@AllArgsConstructor
public enum ApplicationUserRole {
    ATTENDEE(Sets.newHashSet()),
    RESPONSIBLE_PERSON(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(
            ATTENDEES_DELETE, ATTENDEES_READ, ATTENDEES_WRITE,
            MEETINGS_DELETE, MEETINGS_READ, MEETINGS_WRITE));

    private final Set<ApplicationUserPermission> permissions;

}