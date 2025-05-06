package ru.otus.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;

public class ClientResultSetExtractorClass implements ResultSetExtractor<List<Client>> {

    @Override
    public List<Client> extractData(ResultSet rs) throws SQLException, DataAccessException {
        var clientList = new ArrayList<Client>();
        Long prevClientId = null;
        Client client = null;
        while (rs.next()) {
            var clientId = rs.getLong("client_id");
            if (prevClientId == null || !prevClientId.equals(clientId)) {
                client = new Client(
                        clientId,
                        rs.getString("client_name"),
                        new Address(rs.getLong("address_id"), clientId, rs.getString("client_address")),
                        new HashSet<>());
                clientList.add(client);
                prevClientId = clientId;
            }
            Long phoneId = (Long) rs.getObject("phone_id");
            if (phoneId != null) {
                client.getPhones().add(new Phone(rs.getLong("phone_id"), prevClientId, rs.getString("client_phone")));
            }
        }
        return clientList;
    }
}
