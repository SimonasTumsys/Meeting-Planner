package lt.bit.meetings.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
    private final int apiErrorCode;

    public ApiException(String message, int apiErrorCode) {
        super(message);
        this.apiErrorCode = apiErrorCode;
    }
}
