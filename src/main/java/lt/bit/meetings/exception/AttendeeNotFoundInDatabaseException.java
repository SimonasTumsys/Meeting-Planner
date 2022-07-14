package lt.bit.meetings.exception;

public class AttendeeNotFoundInDatabaseException extends Exception{
    public AttendeeNotFoundInDatabaseException() {
    }

    public AttendeeNotFoundInDatabaseException(String message) {
        super(message);
    }

    public AttendeeNotFoundInDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public AttendeeNotFoundInDatabaseException(Throwable cause) {
        super(cause);
    }

}
