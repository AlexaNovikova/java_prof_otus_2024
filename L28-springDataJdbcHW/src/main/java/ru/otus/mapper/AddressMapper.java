package ru.otus.mapper;

import org.springframework.stereotype.Component;
import ru.otus.dto.AddressDto;
import ru.otus.model.Address;

@Component
public class AddressMapper {
    public AddressDto modelToDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.id());
        addressDto.setText(address.street());
        addressDto.setClientId(address.clientId());
        return addressDto;
    }

    public Address dtoToModel(AddressDto addressDto) {
        return new Address(addressDto.getId(), addressDto.getClientId(), addressDto.getText());
    }
}
