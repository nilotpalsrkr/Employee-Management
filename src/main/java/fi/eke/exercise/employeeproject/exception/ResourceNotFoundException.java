package fi.eke.exercise.employeeproject.exception;

import static fi.eke.exercise.employeeproject.util.Constants.*;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String errorCode, String message, Object resource) {
        super(String.format("%s%s%s%s%s",errorCode, ERROR_SEPARATOR, message, " - ", resource));
    }
}
