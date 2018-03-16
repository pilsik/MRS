package by.sivko.miningrigservice.controllers.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ExceptionResponse {

    private String errorMessage;
    private List<String> errors;
    private HttpStatus httpStatus;

    public ExceptionResponse() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
