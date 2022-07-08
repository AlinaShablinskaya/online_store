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
        return customerAddressRepository.save(address);
    }

    @Override
    public CustomerAddress updateCustomerAddress(CustomerAddress address) {
        if (!customerAddressRepository.findById(address.getId()).isPresent()) {
            throw new EntityNotFoundException(ADDRESS_IS_NOT_FOUND);
        }
        address = customerAddressRepository.save(address);
        return address;
    }

    @Override
    public void removeCustomerAddressById(Integer id) {
        if (!customerAddressRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException(ADDRESS_IS_NOT_FOUND);
        }
        customerAddressRepository.deleteById(id);
    }
}
