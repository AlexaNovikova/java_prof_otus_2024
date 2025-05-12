package ru.otus.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;

public class OptionalClientExtractorClass implements ResultSetExtractor<Optional<Client>> {

    @Override
    public Optional<Client> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Client client = null;
        while (rs.next()) {
            var clientId = rs.getLong("client_id");
            if (client == null) {
                client = new Client(
                        clientId,
                        rs.getString("client_name"),
                        new Address(rs.getLong("address_id"), clientId, rs.getString("client_address")),
                        new HashSet<>());
            }
            Long phoneId = (Long) rs.getObject("phone_id");
            if (phoneId != null) {
                client.getPhones().add(new Phone(rs.getLong("phone_id"), clientId, rs.getString("client_phone")));
            }
        }
        return Optional.ofNullable(client);
    }
}
