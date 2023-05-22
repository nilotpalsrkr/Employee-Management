/***
 * This class represents the ApiException.
 * This class centrally handles all the application thrown errors.
 *
 * @author : Nilotpal Sarkar
 * @since : 1.0
 */
package fi.eke.exercise.employeeproject.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import fi.eke.exercise.employeeproject.models.rest.FailureResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static fi.eke.exercise.employeeproject.util.Constants.ERROR_SEPARATOR;

@RestControllerAdvice
public class ApiException {
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<FailureResponse> handleIllegalArgumentException(Exception ex) {
        ErrorResponse errorResponse = null;
        String[] message = ex.getLocalizedMessage().split(ERROR_SEPARATOR);
        errorResponse = new FailureResponse(message[1], HttpStatus.BAD_REQUEST, message[0]);
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceCannotBeRemovedException.class})
    public ResponseEntity<FailureResponse> handleResourceCannotBeRemovedException(Exception ex) {
        ErrorResponse errorResponse = null;
        String[] message = ex.getLocalizedMessage().split(ERROR_SEPARATOR);
        errorResponse = new FailureResponse(message[1], HttpStatus.CONFLICT, message[0]);
        return new ResponseEntity(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<FailureResponse> handleCannotCreateTransactionException(Exception ex) {
        ErrorResponse errorResponse = null;
        Throwable cause = ex.getCause().getCause();
        errorResponse = new FailureResponse(cause.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "DB_SERVER_NOT_REACHABLE");
        return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FailureResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = null;
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            errorResponse = new FailureResponse(cause.getLocalizedMessage(), HttpStatus.BAD_REQUEST, "");
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
        Throwable cause1 = ex.getCause().getCause();
        if (cause1 instanceof IllegalArgumentException) {
             errorResponse = new FailureResponse(cause1.getLocalizedMessage(), HttpStatus.BAD_REQUEST, "");
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
        else {
            errorResponse = new FailureResponse(cause.getLocalizedMessage(), HttpStatus.BAD_REQUEST, "");
            return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
