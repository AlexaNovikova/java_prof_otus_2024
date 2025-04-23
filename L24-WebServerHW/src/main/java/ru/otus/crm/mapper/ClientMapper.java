package ru.otus.crm.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.crm.dto.ClientDto;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

public class ClientMapper {

    private final Logger logger = LoggerFactory.getLogger(ClientMapper.class);

    public ClientDto modelToDto(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setName(client.getName());
        clientDto.setAddress(client.getAddress().getStreet());
        clientDto.setPhones(client.getPhones().stream().map(Phone::getNumber).collect(Collectors.joining(", ")));
        logger.info("ClientDto created {}, {}, {}", clientDto.getName(), clientDto.getAddress(), clientDto.getPhones());
        return clientDto;
    }

    public Client dtoToModel(ClientDto clientDto) {
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setName(clientDto.getName());
        client.setAddress(new Address(clientDto.getAddress()));
        List<Phone> phones =
                Arrays.stream(clientDto.getPhones().split(", ")).map(Phone::new).toList();
        client.setPhones(phones);
        for (Phone phone : phones) {
            phone.setClient(client);
        }
        return client;
    }
}
