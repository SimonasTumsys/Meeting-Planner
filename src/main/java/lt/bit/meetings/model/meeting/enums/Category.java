package lt.bit.meetings.model.meeting.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    CODE_MONKEY("CodeMonkey"),
    HUB("Hub"),
    SHORT("Short"),
    TEAM_BUILDING("TeamBuilding");

    private final String catCode;

    @JsonCreator
    public static Category getCategoryFromCode(String value){
        for(Category cat : Category.values()){
            if(cat.getCatCode().equals(value)){
                return cat;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return catCode;
    }
}

