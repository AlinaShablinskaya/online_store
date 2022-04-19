package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.CustomerAddressDao;
import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.services.AddressService;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddressServiceImpl implements AddressService {
    private static final Logger lOGGER = LoggerFactory.getLogger(AddressServiceImpl.class);
    private static final String ADDRESS_ALREADY_EXISTS = "Specified address already exists.";
    private static final String ADDRESS_IS_NOT_FOUND = "Specified address is not found.";
    private final CustomerAddressDao customerAddressDao;

    public AddressServiceImpl(CustomerAddressDao customerAddressDao) {
        this.customerAddressDao = customerAddressDao;
    }

    @Override
    public void addCustomerAddress(CustomerAddress address) {
        if (customerAddressDao.findById(address.getId()).isPresent()) {
            throw new EntityAlreadyExistException(ADDRESS_ALREADY_EXISTS);
        }
        customerAddressDao.save(address);

        lOGGER.info("Address successfully added.");
    }

    @Override
    public void updateCustomerAddress(CustomerAddress address) {
        if (!customerAddressDao.findById(address.getId()).isPresent()) {
            throw new EntityNotFoundException(ADDRESS_IS_NOT_FOUND);
        }
        customerAddressDao.update(address);

        lOGGER.info("Address successfully updated.");
    }

    @Override
    public void removeCustomerAddressById(Integer id) {
        if (!customerAddressDao.findById(id).isPresent()) {
            throw new EntityNotFoundException(ADDRESS_IS_NOT_FOUND);
        }
        customerAddressDao.deleteById(id);

        lOGGER.info("Address successfully deleted.");
    }
}
