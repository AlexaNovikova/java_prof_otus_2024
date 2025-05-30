package ru.otus.crm.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.model.Client;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final HwCache<String, Client> clientHwCache;

    public DbServiceClientImpl(
            TransactionManager transactionManager,
            DataTemplate<Client> clientDataTemplate,
            HwCache<String, Client> clientHwCache) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.clientHwCache = clientHwCache;
    }

    @Override
    public Client saveClient(Client client) {
        var result = transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                var savedClient = clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                return savedClient;
            }
            var savedClient = clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", savedClient);
            return savedClient;
        });
        clientHwCache.put(toKey(result.getId()), result);
        return result;
    }

    @Override
    public Optional<Client> getClient(long id) {
        Client clientFromCache = clientHwCache.get(toKey(id));
        if (clientFromCache != null) {
            return Optional.of(clientFromCache);
        }
        var resultOptional = transactionManager.doInReadOnlyTransaction(session -> {
            var clientOptional = clientDataTemplate.findById(session, id);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
        if (resultOptional.isPresent()) {
            Client client = resultOptional.get();
            clientHwCache.put(toKey(client.getId()), client);
        }
        return resultOptional;
    }

    @Override
    public List<Client> findAll() {
        var clientList = transactionManager.doInReadOnlyTransaction(session -> {
            var clientListFromDb = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientListFromDb);
            return clientListFromDb;
        });
        for (Client client : clientList) {
            clientHwCache.put(toKey(client.getId()), client);
        }
        return clientList;
    }

    private String toKey(Long id) {
        return String.valueOf(id);
    }
}
