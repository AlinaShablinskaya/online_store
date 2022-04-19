package by.it.academy.onlinestore;

import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.services.CatalogService;
import by.it.academy.onlinestore.services.UserService;

public class OnlineStoreMain {
    public static void main(String[] args) {
        CatalogService catalogService;
        ApplicationInjector injector = ApplicationInjector.getInstance();
        catalogService = injector.getCatalogService();

        Catalog catalog = Catalog.builder()
                .withId(1)
                .withName("Name")
                .build();

        catalogService.createNewCatalog(catalog);

        Catalog findCatalog = catalogService.findCatalogById(1);

        System.out.println(findCatalog.toString());
    }
}
