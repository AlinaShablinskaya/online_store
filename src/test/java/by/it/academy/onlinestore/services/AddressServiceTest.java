package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.repositories.CustomerAddressRepository;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import by.it.academy.onlinestore.services.impl.AddressServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    @Mock
    private CustomerAddressRepository addressRepository;
    @InjectMocks
    private AddressServiceImpl addressService;


    @Test
    void addCustomerAddressShouldAddAddress() {
        CustomerAddress address = new CustomerAddress();
        address.setId(1);
        address.setCountry("Country");
        address.setStreet("Street");

        when(addressRepository.save(address)).thenReturn(address);
        addressService.addCustomerAddress(address);
        verify(addressRepository).save(address);
    }

    @Test
    void updateCustomerAddressShouldUpdateAddress() {
        CustomerAddress address = new CustomerAddress();
        address.setId(1);
        address.setCountry("Country");
        address.setStreet("Street");

        Integer addressId = address.getId();

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        addressService.updateCustomerAddress(address);
        verify(addressRepository).save(address);
    }

    @Test
    void updateCustomerAddressShouldThrowEntityNotFoundExceptionIfNoSuchEntityExists() {
        CustomerAddress address = new CustomerAddress();
        address.setId(1);
        address.setCountry("Country");
        address.setStreet("Street");

        Integer nonExistentId = 1;

        when(addressRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> addressService.updateCustomerAddress(address));
        String expectedMessage = "Specified address is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(addressRepository).findById(address.getId());
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    void removeCustomerAddressByIdShouldRemoveAddress() {
        CustomerAddress address = new CustomerAddress();
        address.setId(1);
        address.setCountry("Country");
        address.setStreet("Street");

        Integer addressId = address.getId();

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        doNothing().when(addressRepository).deleteById(addressId);
        addressService.removeCustomerAddressById(addressId);
        verify(addressRepository).findById(addressId);
        verify(addressRepository).deleteById(addressId);
    }

    @Test
    void removeCustomerAddressByIdShouldThrowEntityNotFoundExceptionIfNoSuchEntityExists() {
        Integer nonExistentId = 1;

        when(addressRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> addressService.removeCustomerAddressById(nonExistentId));
        String expectedMessage = "Specified address is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(addressRepository).findById(nonExistentId);
        verifyNoMoreInteractions(addressRepository);
    }
}
