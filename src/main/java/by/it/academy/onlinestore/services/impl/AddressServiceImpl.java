package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.CustomerAddressDao;
import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.services.AddressService;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import by.it.academy.onlinestore.services.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class AddressServiceImpl implements AddressService {
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
        log.info("Adding address {} started", address);
        if (customerAddressDao.findById(address.getId()).isPresent()) {
            log.error("Address already exists in database");
            throw new EntityAlreadyExistException(ADDRESS_ALREADY_EXISTS);
        }
        log.info("Validating address: {}", address);
        validator.validate(address);
        customerAddressDao.save(address);
        log.info("Address {} successfully added.", address);
    }

    @Override
    public void updateCustomerAddress(CustomerAddress address) {
        log.info("Updating address {} started", address);
        if (!customerAddressDao.findById(address.getId()).isPresent()) {
            log.error("Address {} is not found", address);
            throw new EntityNotFoundException(ADDRESS_IS_NOT_FOUND);
        }
        log.info("Validating address: {}", address);
        validator.validate(address);
        customerAddressDao.update(address);
        log.info("Address {} successfully updated.", address);
    }

    @Override
    public void removeCustomerAddressById(Integer id) {
        log.info("Address delete by id = {} started", id);
        if (!customerAddressDao.findById(id).isPresent()) {
            log.error("Address id = {} is not found", id);
            throw new EntityNotFoundException(ADDRESS_IS_NOT_FOUND);
        }
        customerAddressDao.deleteById(id);
        log.info("Address successfully deleted by id = {}.", id);
    }
}
