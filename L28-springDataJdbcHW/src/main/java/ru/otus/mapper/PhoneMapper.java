package ru.otus.mapper;

import org.springframework.stereotype.Component;
import ru.otus.dto.PhoneDto;
import ru.otus.model.Phone;

@Component
public class PhoneMapper {
    public PhoneDto modelToDto(Phone phone) {
        PhoneDto phoneDto = new PhoneDto();
        phoneDto.setId(phone.id());
        phoneDto.setNumber(phone.number());
        phoneDto.setClientId(phone.clientId());
        return phoneDto;
    }

    public Phone dtoToModel(PhoneDto phoneDto) {
        return new Phone(phoneDto.getId(), phoneDto.getClientId(), phoneDto.getNumber());
    }
}
