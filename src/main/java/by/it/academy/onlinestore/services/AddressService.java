package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.CustomerAddress;

public interface AddressService {
    void addCustomerAddress(CustomerAddress address);

    void updateCustomerAddress(CustomerAddress address);

    void removeCustomerAddressById(Integer id);
}
