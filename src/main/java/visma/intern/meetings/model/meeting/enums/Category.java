package visma.intern.meetings.model.meeting.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum Category {
    CODE_MONKEY("CodeMonkey"),
    HUB("Hub"),
    SHORT("Short"),
    TEAM_BUILDING("TeamBuilding");

    private final String catCode;

    Category(final String category) {
        this.catCode = category;
    }

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

