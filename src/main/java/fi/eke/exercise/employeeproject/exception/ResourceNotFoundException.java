/***
 * This class represents the ResourceNotFoundException.
 * This is Custom defined exception that is a Runtime Exception and is raised when the
 * Resource is not found or incorrectly defined in the parameters. This would take 3 parameters
 * in the constructor. The ID of the resource not found is also an parameter so that
 * whenever this exception is thrown we client is also aware of the ID that dosent exist.
 *
 * @author : Nilotpal Sarkar
 * @since : 1.0
 */
package fi.eke.exercise.employeeproject.exception;

import static fi.eke.exercise.employeeproject.util.Constants.*;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String errorCode, String message, Object resource) {
        super(String.format("%s%s%s%s%s",errorCode, ERROR_SEPARATOR, message, " - ", resource));
    }
}
