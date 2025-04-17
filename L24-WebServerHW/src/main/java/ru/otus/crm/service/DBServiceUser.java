package ru.otus.crm.service;

import java.util.Optional;
import ru.otus.crm.model.User;

public interface DBServiceUser {

    Optional<User> getUser(long id);

    Optional<User> findByLogin(String login);
}
