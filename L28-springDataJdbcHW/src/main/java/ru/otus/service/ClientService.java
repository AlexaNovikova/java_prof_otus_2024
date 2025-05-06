package ru.otus.service;

import java.util.List;
import java.util.Optional;
import ru.otus.dto.ClientDto;

public interface ClientService {
    List<ClientDto> findAll();

    Optional<ClientDto> findById(long id);

    Optional<ClientDto> findByName(String name);

    ClientDto save(ClientDto clientDto);

    void deleteById(Long id);
}
