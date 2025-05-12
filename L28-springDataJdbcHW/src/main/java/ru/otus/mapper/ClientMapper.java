package ru.otus.mapper;

import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.dto.ClientDto;
import ru.otus.model.Client;
import ru.otus.model.Phone;

@Component
@RequiredArgsConstructor
public class ClientMapper {

    private final Logger logger = LoggerFactory.getLogger(ClientMapper.class);

    private final AddressMapper addressMapper;
    private final PhoneMapper phoneMapper;

    public ClientDto modelToDto(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setName(client.getName());
        clientDto.setAddressDto(addressMapper.modelToDto(client.getAddress()));
        clientDto.setPhonesDtos(
                client.getPhones().stream().map(phoneMapper::modelToDto).toList());
        clientDto.setPhonesString(client.getPhones().stream().map(Phone::number).collect(Collectors.joining(", ")));
        logger.info(
                "ClientDto created {}, {}, {}",
                clientDto.getName(),
                clientDto.getAddressDto(),
                clientDto.getPhonesDtos());
        return clientDto;
    }

    public Client dtoToModel(ClientDto clientDto) {
        Client client = new Client(
                clientDto.getId(),
                clientDto.getName(),
                addressMapper.dtoToModel(clientDto.getAddressDto()),
                Arrays.stream(clientDto.getPhonesString().split(", "))
                        .map(t -> new Phone(null, clientDto.getId(), t))
                        .collect(Collectors.toSet()));
        logger.info("Client created {}, {}, {}", client.getName(), client.getAddress(), client.getPhones());
        return client;
    }
}
