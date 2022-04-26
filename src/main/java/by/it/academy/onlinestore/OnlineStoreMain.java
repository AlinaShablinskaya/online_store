package by.it.academy.onlinestore;

import by.it.academy.onlinestore.services.UserService;

public class OnlineStoreMain {
    public static void main(String[] args) {
        UserService userService;
        ApplicationInjector injector = ApplicationInjector.getInstance();
        userService = injector.getUserService();

    }
}
