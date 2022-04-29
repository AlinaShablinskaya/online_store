package by.it.academy.onlinestore;

import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.services.CatalogService;
import by.it.academy.onlinestore.services.ProductService;
import by.it.academy.onlinestore.services.UserService;

public class OnlineStoreMain {
    public static void main(String[] args) {
        CatalogService catalogService;
        ProductService productService;
        ApplicationInjector injector = ApplicationInjector.getInstance();
        catalogService = injector.getCatalogService();
        productService = injector.getProductService();

        System.out.println(productService.findAllByCatalogName("РОМ"));
    }
}
