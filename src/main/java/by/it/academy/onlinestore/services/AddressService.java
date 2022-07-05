package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.CustomerAddress;

public interface AddressService {
    CustomerAddress addCustomerAddress(CustomerAddress address);

    CustomerAddress updateCustomerAddress(CustomerAddress address);

    void removeCustomerAddressById(Integer id);
}
