package lt.bit.meetings.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    //handle specific exceptions
    @ExceptionHandler(ApiException.class)
    public ResponseEntity <?> handleApiException(
            ApiException exception, WebRequest request){

        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                exception.getMessage(),
                request.getDescription(false), exception.getApiErrorCode());
        return new ResponseEntity<>(errorDetails, exception.getHttpStatus());
    }

    //handle global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity <?> handleGlobalException(
            Exception exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                request.getDescription(false), 500);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
