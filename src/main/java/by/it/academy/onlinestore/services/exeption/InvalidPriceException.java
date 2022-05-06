package by.it.academy.onlinestore.services.exeption;

public class InvalidPriceException  extends RuntimeException {

    public InvalidPriceException(String message) {
        super(message);
    }
}
