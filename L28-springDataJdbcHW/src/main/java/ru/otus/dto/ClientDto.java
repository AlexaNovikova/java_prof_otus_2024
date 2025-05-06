package ru.otus.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private Long id;
    private String name;
    private AddressDto addressDto;
    private List<PhoneDto> phonesDtos;
    private String phonesString;
}
