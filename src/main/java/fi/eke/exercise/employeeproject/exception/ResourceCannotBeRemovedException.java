/***
 * This class represents the ResourceCannotBeRemovedException.
 * This is Custom defined exception when an internal resource cannot be removed.
 *
 * @author : Nilotpal Sarkar
 * @since : 1.0
 */
package fi.eke.exercise.employeeproject.exception;

import static fi.eke.exercise.employeeproject.util.Constants.ERROR_SEPARATOR;

public class ResourceCannotBeRemovedException extends RuntimeException{
    public ResourceCannotBeRemovedException(String errorCode, String message) {
        super(String.format("%s%s%s",errorCode, ERROR_SEPARATOR, message));
    }
}
