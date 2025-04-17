package ru.otus.crm.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.dto.ClientDto;
import ru.otus.crm.mapper.ClientMapper;
import ru.otus.crm.model.Client;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final ClientMapper clientMapper;

    public DbServiceClientImpl(
            TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate, ClientMapper clientMapper) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientDto saveClient(ClientDto clientDto) {
        var client = clientMapper.dtoToModel(clientDto);
        return transactionManager.doInTransaction(session -> {
            if (client.getId() == null) {
                var savedClient = clientDataTemplate.insert(session, client);
                log.info("created client: {}", savedClient);
                return clientMapper.modelToDto(savedClient);
            }
            var savedClient = clientDataTemplate.update(session, client);
            log.info("updated client: {}", savedClient);
            return clientMapper.modelToDto(savedClient);
        });
    }

    @Override
    public Optional<ClientDto> getClient(long id) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientOptional = clientDataTemplate.findById(session, id);
            log.info("client: {}", clientOptional);
            return clientOptional.map(clientMapper::modelToDto);
        });
    }

    @Override
    public List<ClientDto> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList.stream().map(clientMapper::modelToDto).toList();
        });
    }
}
