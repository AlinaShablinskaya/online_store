package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.repositories.CustomerAddressRepository;
import by.it.academy.onlinestore.services.AddressService;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class for managing customer address.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private static final String ADDRESS_IS_NOT_FOUND_EXCEPTION = "Specified address is not found.";
    private final CustomerAddressRepository customerAddressRepository;

    /**
     * Returns created customer address in table mapped by calling CustomerAddressRepository.save(address).
     * @param address the entity to save
     * @return non-null address with defined id
     */
    @Override
    public CustomerAddress addCustomerAddress(CustomerAddress address) {
        return customerAddressRepository.save(address);
    }

    /**
     * Updates customer address in table mapped by calling CustomerAddressRepository.save(address)
     * if specified address exists, otherwise throws EntityNotFoundException
     * @param address value to update address in database
     * @throws EntityNotFoundException if address with id defined in param address is not present in database
     * @return updates customer address
     */
    @Override
    public CustomerAddress updateCustomerAddress(CustomerAddress address) {
        if (!customerAddressRepository.findById(address.getId()).isPresent()) {
            throw new EntityNotFoundException(ADDRESS_IS_NOT_FOUND_EXCEPTION);
        }
        address = customerAddressRepository.save(address);
        return address;
    }

    /**
     * Deletes customer address with param id from the table mapped by calling CustomerAddressRepository.deleteById(id)
     * if specified address exists, otherwise throws EntityNotFoundException
     * @throws EntityNotFoundException if address with specified id is not present in database
     * @param id the id of the customer address to delete
     */
    @Override
    public void removeCustomerAddressById(Integer id) {
        if (!customerAddressRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException(ADDRESS_IS_NOT_FOUND_EXCEPTION);
        }
        customerAddressRepository.deleteById(id);
    }
}
