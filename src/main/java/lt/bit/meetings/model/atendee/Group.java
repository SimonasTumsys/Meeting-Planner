package lt.bit.meetings.model.atendee;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Group {
    ADMIN("admin"),
    ATTENDEE("attendee");

    private String groupName;

    @JsonCreator
    public static Group getGroupFromName(String value){
        for(Group group : Group.values()){
            if(group.getGroupName().equals(value)){
                return group;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return groupName;
    }
}
