package by.it.academy.onlinestore.services.exeption;

public class EntityAlreadyExistException extends RuntimeException {

    public EntityAlreadyExistException(String message) {
        super(message);
    }
}
