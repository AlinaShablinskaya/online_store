package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.CustomerAddressDao;
import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.services.AddressService;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import by.it.academy.onlinestore.services.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddressServiceImpl implements AddressService {
    private static final Logger lOGGER = LoggerFactory.getLogger(AddressServiceImpl.class);
    private static final String ADDRESS_ALREADY_EXISTS = "Specified address already exists.";
    private static final String ADDRESS_IS_NOT_FOUND = "Specified address is not found.";
    private final CustomerAddressDao customerAddressDao;
    private final Validator<CustomerAddress> validator;

    public AddressServiceImpl(CustomerAddressDao customerAddressDao, Validator<CustomerAddress> validator) {
        this.customerAddressDao = customerAddressDao;
        this.validator = validator;
    }

    @Override
    public void addCustomerAddress(CustomerAddress address) {
        lOGGER.info("Adding address started");
        if (customerAddressDao.findById(address.getId()).isPresent()) {
            throw new EntityAlreadyExistException(ADDRESS_ALREADY_EXISTS);
        }
        lOGGER.info("Validating address");
        validator.validate(address);
        customerAddressDao.save(address);
        lOGGER.info("Address successfully added.");
    }

    @Override
    public void updateCustomerAddress(CustomerAddress address) {
        lOGGER.info("Updating address started");
        if (!customerAddressDao.findById(address.getId()).isPresent()) {
            throw new EntityNotFoundException(ADDRESS_IS_NOT_FOUND);
        }
        lOGGER.info("Validating address");
        validator.validate(address);
        customerAddressDao.update(address);
        lOGGER.info("Address successfully updated.");
    }

    @Override
    public void removeCustomerAddressById(Integer id) {
        lOGGER.info("Address delete by id started");
        if (!customerAddressDao.findById(id).isPresent()) {
            throw new EntityNotFoundException(ADDRESS_IS_NOT_FOUND);
        }
        customerAddressDao.deleteById(id);
        lOGGER.info("Address successfully deleted.");
    }
}
