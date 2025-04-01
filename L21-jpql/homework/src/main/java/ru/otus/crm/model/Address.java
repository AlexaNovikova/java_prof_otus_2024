package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "address")
public class Address implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    //    @EqualsAndHashCode.Exclude
    //    @ToString.Exclude
    //    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    //    private Client client;

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    public Address(String street) {
        this.id = null;
        this.street = street;
    }

    @Override
    public Address clone() {
        return new Address(this.id, this.street);
    }
}
