package ru.otus.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.model.Address;

@Repository
public interface AddressRepository extends ListCrudRepository<Address, Long> {}
