package by.it.academy.onlinestore.services.exeption;

public class InvalidCatalogNameException extends RuntimeException {

    public InvalidCatalogNameException(String message) {
        super(message);
    }
}
