package ru.otus.services;

import ru.otus.crm.service.DBServiceUser;

public class UserAuthServiceImpl implements UserAuthService {

    private final DBServiceUser dbServiceUser;

    public UserAuthServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return dbServiceUser
                .findByLogin(login)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }
}
