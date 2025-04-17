package ru.otus.crm.mapper;

import ru.otus.crm.dto.AddressDto;
import ru.otus.crm.model.Address;

public class AddressMapper {
    public AddressDto modelToDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setText(address.getStreet());
        return addressDto;
    }
}
