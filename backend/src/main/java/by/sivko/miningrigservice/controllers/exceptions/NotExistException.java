package by.sivko.miningrigservice.controllers.exceptions;

public class NotExistException extends RuntimeException {
    public NotExistException(String message) {
        super(message);
    }
}
