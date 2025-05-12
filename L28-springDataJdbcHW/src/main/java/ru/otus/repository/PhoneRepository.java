package ru.otus.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.model.Phone;

@Repository
public interface PhoneRepository extends ListCrudRepository<Phone, Long> {}
