package lt.bit.meetings.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException{
    private final int apiErrorCode;
    private final HttpStatus httpStatus;

    public ApiException(String message, int apiErrorCode, HttpStatus httpStatus) {
        super(message);
        this.apiErrorCode = apiErrorCode;
        this.httpStatus = httpStatus;
    }
}
