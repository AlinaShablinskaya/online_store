package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.CustomerAddressDto;
import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.mappers.CustomerAddressMapper;
import by.it.academy.onlinestore.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class CustomerAddressController {
    private final AddressService addressService;

    @PostMapping("/")
    public CustomerAddressDto saveAddress(@Valid @RequestBody CustomerAddressDto customerAddressDto) {
        CustomerAddress address = addressService.addCustomerAddress
                (CustomerAddressMapper.INSTANCE.convertToAddress(customerAddressDto));
        return CustomerAddressMapper.INSTANCE.convertToAddressDto(address);
    }

    @PutMapping("/")
    public CustomerAddressDto updateAddress(@Valid @RequestBody CustomerAddressDto customerAddressDto) {
        CustomerAddress customerAddress = addressService.updateCustomerAddress
                (CustomerAddressMapper.INSTANCE.convertToAddress(customerAddressDto));
        return CustomerAddressMapper.INSTANCE.convertToAddressDto(customerAddress);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable(value = "id") Integer addressId) {
        addressService.removeCustomerAddressById(addressId);
    }
}
