package ru.otus.crm.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.model.User;

public class DBServiceUserImpl implements DBServiceUser {

    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<User> userDataTemplate;
    private final TransactionManager transactionManager;

    public DBServiceUserImpl(TransactionManager transactionManager, DataTemplate<User> userDataTemplate) {
        this.userDataTemplate = userDataTemplate;
        this.transactionManager = transactionManager;
    }

    @Override
    public Optional<User> getUser(long id) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var userOptional = userDataTemplate.findById(session, id);
            log.info("user: {}", userOptional);
            return userOptional;
        });
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var usersList = userDataTemplate.findByEntityField(session, "login", login);
            if (usersList.isEmpty()) {
                log.info("User with login {} not found in DB", login);
                return Optional.empty();
            } else {
                log.info("User with login {} exists in DB", login);
                return Optional.ofNullable(usersList.getFirst());
            }
        });
    }
}
