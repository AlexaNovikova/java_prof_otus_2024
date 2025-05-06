package ru.otus.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.dto.ClientDto;
import ru.otus.mapper.ClientMapper;
import ru.otus.model.Client;
import ru.otus.repository.ClientRepository;
import ru.otus.sessionmanager.TransactionManager;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final TransactionManager transactionManager;

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream().map(clientMapper::modelToDto).toList();
    }

    @Override
    public Optional<ClientDto> findById(long id) {
        return clientRepository.findById(id).map(clientMapper::modelToDto);
    }

    @Override
    public Optional<ClientDto> findByName(String name) {
        return clientRepository.findByName(name).map(clientMapper::modelToDto);
    }

    @Override
    public ClientDto save(ClientDto clientDto) {
        return transactionManager.doInTransaction(() -> {
            Client client = clientMapper.dtoToModel(clientDto);
            var savedClient = clientRepository.save(client);
            log.info("saved client: {}", savedClient);
            return clientMapper.modelToDto(savedClient);
        });
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }
}
