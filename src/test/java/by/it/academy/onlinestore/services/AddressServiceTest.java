package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.repositories.CustomerAddressRepository;
import by.it.academy.onlinestore.services.impl.AddressServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    @Mock
    private CustomerAddressRepository addressRepository;
    @InjectMocks
    private AddressServiceImpl addressService;


    @Test
    void addCustomerAddressShouldReturnCorrectResult() {
        CustomerAddress address = new CustomerAddress();
        address.setId(1);
        address.setCountry("Country");
        address.setStreet("Street");

        when(addressRepository.save(address)).thenReturn(address);
        addressService.addCustomerAddress(address);
        verify(addressRepository).save(address);
    }

    @Test
    void updateCustomerAddressShouldReturnCorrectResult() {
        CustomerAddress address = new CustomerAddress();
        address.setId(1);
        address.setCountry("Country");
        address.setStreet("Street");

        when(addressRepository.findById(1)).thenReturn(Optional.of(address));
        when(addressRepository.save(address)).thenReturn(address);
        addressService.updateCustomerAddress(address);
        verify(addressRepository).save(address);
    }

    @Test
    void removeCustomerAddressByIdShouldReturnCorrectResult() {
        CustomerAddress address = new CustomerAddress();
        address.setId(1);
        address.setCountry("Country");
        address.setStreet("Street");

        when(addressRepository.findById(1)).thenReturn(Optional.of(address));
        addressService.removeCustomerAddressById(1);
        verify(addressRepository).deleteById(1);
    }
}
