package by.it.academy.onlinestore.mappers;

import by.it.academy.onlinestore.dto.address.CustomerAddressDto;
import by.it.academy.onlinestore.entities.CustomerAddress;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper class for customer address
 */
@Mapper
public interface CustomerAddressMapper {
    CustomerAddressMapper INSTANCE = Mappers.getMapper(CustomerAddressMapper.class);

    /**
     * Converts entity to Dto
     * @param customerAddress entity to convert
     * @return CustomerAddressDto
     */
    CustomerAddressDto convertToAddressDto(CustomerAddress customerAddress);

    /**
     * Converts Dto to entity
     * @param customerAddressDto Dto to convert
     * @return customerAddress entity
     */
    CustomerAddress convertToAddress(CustomerAddressDto customerAddressDto);
}
