package lt.bit.meetings.model.meeting.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter

public enum Type {
    LIVE("Live"),
    IN_PERSON("InPerson");

    private final String typeCode;

    Type(final String type){
        this.typeCode = type;
    }

    @JsonCreator
    public static Type getTypeFromCode(String value){
        for(Type type : Type.values()){
            if(type.getTypeCode().equals(value)){
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return typeCode;
    }
}
