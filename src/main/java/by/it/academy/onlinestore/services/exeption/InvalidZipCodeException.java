package by.it.academy.onlinestore.services.exeption;

public class InvalidZipCodeException extends RuntimeException {

    public InvalidZipCodeException(String message) {
        super(message);
    }
}
