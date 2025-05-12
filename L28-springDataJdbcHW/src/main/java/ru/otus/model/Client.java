package ru.otus.model;

import jakarta.annotation.Nonnull;
import java.util.Set;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("client")
@Getter
public class Client {
    @Id
    private final Long id;

    @Nonnull
    private final String name;

    @MappedCollection(idColumn = "client_id")
    private final Address address;

    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;

    public Client(Long id, @Nonnull String name, Address address, Set<Phone> phones) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.phones = phones;
    }
}
