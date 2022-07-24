package by.it.academy.onlinestore.dto.address;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CustomerAddressDto {
    private Integer id;
    @NotBlank
    private String zipcode;
    @NotBlank
    private String country;
    @NotBlank
    private String street;
}
