package by.it.academy.onlinestore.mappers;

import by.it.academy.onlinestore.dto.CustomerAddressDto;
import by.it.academy.onlinestore.entities.CustomerAddress;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerAddressMapper {
    CustomerAddressMapper INSTANCE = Mappers.getMapper(CustomerAddressMapper.class);

    CustomerAddressDto convertToAddressDto(CustomerAddress customerAddress);

    CustomerAddress convertToAddress(CustomerAddressDto customerAddressDto);
}
