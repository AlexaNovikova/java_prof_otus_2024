package ru.otus.crm.mapper;

import ru.otus.crm.dto.PhoneDto;
import ru.otus.crm.model.Phone;

public class PhoneMapper {
    public PhoneDto modelToDto(Phone phone) {
        PhoneDto phoneDto = new PhoneDto();
        phoneDto.setId(phone.getId());
        phoneDto.setNumber(phone.getNumber());
        return phoneDto;
    }
}
