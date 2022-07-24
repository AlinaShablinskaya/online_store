package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.address.CustomerAddressDto;
import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.mappers.CustomerAddressMapper;
import by.it.academy.onlinestore.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST controller for managing customer address.
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class CustomerAddressController {
    private final AddressService addressService;

    /**
     * POST : create a new customer address
     * @param customerAddressDto the customerAddressDto to create
     * @return the new customer address in body
     */
    @PostMapping("/")
    @PreAuthorize("hasAuthority('USER')")
    public CustomerAddressDto saveAddress(@Valid @RequestBody CustomerAddressDto customerAddressDto) {
        CustomerAddress address = addressService.addCustomerAddress
                (CustomerAddressMapper.INSTANCE.convertToAddress(customerAddressDto));
        return CustomerAddressMapper.INSTANCE.convertToAddressDto(address);
    }

    /**
     * PUT : update an existing customer address
     * @param customerAddressDto the customerAddressDto to update customer address in database
     * @return updates customer address in body
     */
    @PutMapping("/")
    @PreAuthorize("hasAuthority('USER')")
    public CustomerAddressDto updateAddress(@Valid @RequestBody CustomerAddressDto customerAddressDto) {
        CustomerAddress customerAddress = addressService.updateCustomerAddress
                (CustomerAddressMapper.INSTANCE.convertToAddress(customerAddressDto));
        return CustomerAddressMapper.INSTANCE.convertToAddressDto(customerAddress);
    }

    /**
     * DELETE /{id} : delete customer address by specified id
     * @param addressId the id of the customer address to delete
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public void deleteAddress(@PathVariable(value = "id") Integer addressId) {
        addressService.removeCustomerAddressById(addressId);
    }
}
