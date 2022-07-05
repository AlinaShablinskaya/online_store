package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.repositories.CustomerAddressRepository;
import by.it.academy.onlinestore.services.AddressService;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private static final String ADDRESS_ALREADY_EXISTS = "Specified address already exists.";
    private static final String ADDRESS_IS_NOT_FOUND = "Specified address is not found.";
    private final CustomerAddressRepository customerAddressRepository;

    @Override
    public CustomerAddress addCustomerAddress(CustomerAddress address) {
        log.info("Adding address {} started", address);
        CustomerAddress newAddress = customerAddressRepository.save(address);
        log.info("Address {} successfully added.", address);
        return newAddress;
    }

    @Override
    public CustomerAddress updateCustomerAddress(CustomerAddress address) {
        log.info("Updating address {} started", address);
        if (!customerAddressRepository.findById(address.getId()).isPresent()) {
            log.error("Address {} is not found", address);
            throw new EntityNotFoundException(ADDRESS_IS_NOT_FOUND);
        }
        address = customerAddressRepository.save(address);
        log.info("Address {} successfully updated.", address);
        return address;
    }

    @Override
    public void removeCustomerAddressById(Integer id) {
        log.info("Address delete by id = {} started", id);
        if (!customerAddressRepository.findById(id).isPresent()) {
            log.error("Address id = {} is not found", id);
            throw new EntityNotFoundException(ADDRESS_IS_NOT_FOUND);
        }
        customerAddressRepository.deleteById(id);
        log.info("Address successfully deleted by id = {}.", id);
    }
}
