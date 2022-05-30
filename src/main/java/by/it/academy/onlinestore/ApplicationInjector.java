package by.it.academy.onlinestore;

import by.it.academy.onlinestore.dao.*;
import by.it.academy.onlinestore.dao.impl.jdbc.*;
import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.services.*;
import by.it.academy.onlinestore.services.impl.*;
import by.it.academy.onlinestore.services.validator.*;

public class ApplicationInjector {
    private static final String PROPERTIES = "/database.properties";
    private static final ApplicationInjector INSTANCE = new ApplicationInjector();
    private static final DBConnector CONNECTOR = new DBConnector(PROPERTIES);
    private static final CartDao CART_DAO = new CartDaoImpl(CONNECTOR);
    private static final CatalogDao CATALOG_DAO = new CatalogDaoImpl(CONNECTOR);
    private static final CustomerAddressDao CUSTOMER_ADDRESS_DAO = new CustomerAddressDaoImpl(CONNECTOR);
    private static final OrderItemDao ORDER_ITEM_DAO = new OrderItemDaoImpl(CONNECTOR);
    private static final ProductDao PRODUCT_DAO = new ProductDaoImpl(CONNECTOR);
    private static final UserDao USER_DAO = new UserDaoImpl(CONNECTOR);
    private static final Validator<CustomerAddress> ADDRESS_VALIDATOR = new CustomerAddressValidator();
    private static final Validator<Catalog> CATALOG_VALIDATOR = new CatalogValidator();
    private static final Validator<Product> PRODUCT_VALIDATOR = new ProductValidator();
    private static final Validator<User> USER_VALIDATOR = new UserValidator();
    private static final CartService CART_SERVICE = new CartServiceImpl(CART_DAO);
    private static final CatalogService CATALOG_SERVICE = new CatalogServiceImpl(CATALOG_DAO, CATALOG_VALIDATOR);
    private static final AddressService ADDRESS_SERVICE = new AddressServiceImpl(CUSTOMER_ADDRESS_DAO, ADDRESS_VALIDATOR);
    private static final OrderItemService ORDER_ITEM_SERVICE = new OrderItemServiceImpl(ORDER_ITEM_DAO, CART_DAO);
    private static final ProductService PRODUCT_SERVICE = new ProductServiceImpl(PRODUCT_DAO, CATALOG_DAO, PRODUCT_VALIDATOR);
    private static final PasswordEncryptor PASSWORD_ENCRYPTOR = new PasswordEncryptor();
    private static final UserService USER_SERVICE = new UserServiceImpl(USER_DAO, PASSWORD_ENCRYPTOR, USER_VALIDATOR);

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
