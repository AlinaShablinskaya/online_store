package by.it.academy.onlinestore;

import by.it.academy.onlinestore.dao.*;
import by.it.academy.onlinestore.dao.impl.*;
import by.it.academy.onlinestore.services.*;
import by.it.academy.onlinestore.services.impl.*;

public class ApplicationInjector {
    private static final String PROPERTIES = "src/main/resources/database.properties";
    private static final ApplicationInjector INSTANCE = new ApplicationInjector();
    private static final DBConnector CONNECTOR = new DBConnector(PROPERTIES);
    private static final CartDao CART_DAO = new CartDaoImpl(CONNECTOR);
    private static final CatalogDao CATALOG_DAO = new CatalogDaoImpl(CONNECTOR);
    private static final CustomerAddressDao CUSTOMER_ADDRESS_DAO = new CustomerAddressDaoImpl(CONNECTOR);
    private static final OrderItemDao ORDER_ITEM_DAO = new OrderItemDaoImpl(CONNECTOR);
    private static final ProductDao PRODUCT_DAO = new ProductDaoImpl(CONNECTOR);
    private static final UserDao USER_DAO = new UserDaoImpl(CONNECTOR);
    private static final CartService CART_SERVICE = new CartServiceImpl(CART_DAO);
    private static final CatalogService CATALOG_SERVICE = new CatalogServiceImpl(CATALOG_DAO);
    private static final AddressService ADDRESS_SERVICE = new AddressServiceImpl(CUSTOMER_ADDRESS_DAO);
    private static final OrderItemService ORDER_ITEM_SERVICE = new OrderItemServiceImpl(ORDER_ITEM_DAO, CART_DAO);
    private static final ProductService PRODUCT_SERVICE = new ProductServiceImpl(PRODUCT_DAO, CATALOG_DAO);
    private static final UserService USER_SERVICE = new UserServiceImpl(USER_DAO);

    private ApplicationInjector() {
    }

    public static ApplicationInjector getInstance() {
        return INSTANCE;
    }

    public CartService getCartService() {
        return CART_SERVICE;
    }

    public CatalogService getCatalogService() {
        return CATALOG_SERVICE;
    }

    public AddressService getAddressService() {
        return ADDRESS_SERVICE;
    }

    public OrderItemService getOrderItemService() {
        return ORDER_ITEM_SERVICE;
    }

    public ProductService getProductService() {
        return PRODUCT_SERVICE;
    }

    public UserService getUserService() {
        return USER_SERVICE;
    }
}
