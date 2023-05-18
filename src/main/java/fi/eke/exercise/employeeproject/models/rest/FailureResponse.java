package fi.eke.exercise.employeeproject.models.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

public record FailureResponse(String message, HttpStatus httpStatus, String errorCode) implements ErrorResponse {

    @Override
    public HttpStatusCode getStatusCode() {
        return httpStatus;
    }

    @Override
    public ProblemDetail getBody() {
        return null;
    }
}
