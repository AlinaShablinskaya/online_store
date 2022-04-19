package by.it.academy.onlinestore.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import by.it.academy.onlinestore.dao.CustomerAddressDao;
import by.it.academy.onlinestore.dao.ProductDao;
import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.impl.AddressServiceImpl;
import by.it.academy.onlinestore.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Mock
    private CustomerAddressDao addressDao;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    void addCustomerAddressShouldReturnCorrectResult() {
        CustomerAddress address = CustomerAddress.builder()
                .withId(1)
                .withZipcode("111111")
                .withCountry("Country")
                .withStreet("Street")
                .build();

        doNothing().when(addressDao).save(address);
        addressService.addCustomerAddress(address);
        verify(addressDao).save(address);
    }

    @Test
    void addCustomerAddressShouldReturnExceptionWhenGetIncorrectParameters() {
        CustomerAddress address = CustomerAddress.builder()
                .withId(1)
                .withZipcode("111111")
                .withCountry("Country")
                .withStreet("Street")
                .build();

        when(addressDao.findById(address.getId())).thenReturn(Optional.of(address));
        assertThrows(EntityAlreadyExistException.class, () -> addressService.addCustomerAddress(address));
        verifyNoMoreInteractions(addressDao);
    }

    @Test
    void updateCustomerAddressShouldReturnCorrectResult() {
        CustomerAddress address = CustomerAddress.builder()
                .withId(1)
                .withZipcode("111111")
                .withCountry("Country")
                .withStreet("Street")
                .build();

        when(addressDao.findById(address.getId())).thenReturn(Optional.of(address));
        doNothing().when(addressDao).update(address);
        addressService.updateCustomerAddress(address);
        verify(addressDao).update(address);
    }

    @Test
    void removeCustomerAddressByIdShouldReturnCorrectResult() {
        CustomerAddress address = CustomerAddress.builder()
                .withId(1)
                .withZipcode("111111")
                .withCountry("Country")
                .withStreet("Street")
                .build();

        when(addressDao.findById(address.getId())).thenReturn(Optional.of(address));
        addressService.removeCustomerAddressById(address.getId());
        verify(addressDao).deleteById(address.getId());
    }
}
