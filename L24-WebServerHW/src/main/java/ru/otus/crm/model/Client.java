package ru.otus.crm.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phones;

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @SuppressWarnings("this-escape")
    public <E> Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.phones = phones;
        this.address = address;
        setPhonesOwner();
    }

    private void setPhonesOwner() {
        if (this.phones != null) {
            for (Phone phone : this.phones) {
                phone.setClient(this);
            }
        }
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        Client client = new Client(this.id, this.name);
        if (address != null) {
            Address addressClone = address.clone();
            client.setAddress(addressClone);
        }
        if (phones != null) {
            List<Phone> phonesClone = this.phones.stream().map(Phone::clone).toList();
            client.setPhones(phonesClone);
            for (Phone phone : phonesClone) {
                phone.setClient(client);
            }
        }
        return client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return Objects.equals(getId(), client.getId()) && Objects.equals(getName(), client.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
