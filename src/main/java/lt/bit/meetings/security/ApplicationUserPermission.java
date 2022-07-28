package lt.bit.meetings.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationUserPermission {
    ATTENDEES_READ("attendees:read"),
    ATTENDEES_WRITE("attendees:write"),
    MEETINGS_READ("meetings:read"),
    MEETINGS_WRITE("meetings:write");

    private final String permission;
}

