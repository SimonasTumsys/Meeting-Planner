package lt.bit.meetings.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationUserPermission {
    ATTENDEES_READ("attendees:read"),
    ATTENDEES_WRITE("attendees:write"),
    ATTENDEES_DELETE("attendees:delete"),
    MEETINGS_READ("meetings:read"),
    MEETINGS_WRITE("meetings:write"),
    MEETINGS_DELETE("meetings:delete"),
    RESPONSIBLE_DELETE("responsible:delete");

    private final String permission;
}

