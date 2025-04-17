package ru.otus;

import org.hibernate.cfg.Configuration;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.mapper.ClientMapper;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.model.User;
import ru.otus.crm.service.DBServiceUserImpl;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.server.ClientWebServer;
import ru.otus.server.ClientsWebServerWithFilterBasedSecurity;
import ru.otus.services.*;

// Стартовая страница
// http://localhost:8080

// Страница пользователей
// http://localhost:8080/clients

public class WebServerWithBasicSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String FLYWAY_PATH_MIGRATION = "classpath:/db/migration/prod";

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {

        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword, FLYWAY_PATH_MIGRATION).executeMigrations();

        var sessionFactory =
                HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class, User.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        ///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var userTemplate = new DataTemplateHibernate<>(User.class);

        var clientMapper = new ClientMapper();
        ///
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate, clientMapper);
        var dbServiceUser = new DBServiceUserImpl(transactionManager, userTemplate);

        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        UserAuthService authService = new UserAuthServiceImpl(dbServiceUser);

        ClientWebServer clientWebServer = new ClientsWebServerWithFilterBasedSecurity(
                WEB_SERVER_PORT, authService, dbServiceClient, templateProcessor);

        clientWebServer.start();
        clientWebServer.join();
    }
}
