package ru.otus.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.model.Client;

@Repository
public interface ClientRepository extends ListCrudRepository<Client, Long> {

    @Override
    @Query(
            value =
                    """
                select c.id           as client_id,
                       c.name         as client_name,
                       a.id           as address_id,
                       a.street       as client_address,
                       p.id           as phone_id,
                       p.number       as client_phone
                from client c
                         left outer join address a
                                         on c.id = a.client_id
                         left outer join phone p
                                         on p.client_id = c.id
                order by c.id
                                                              """,
            resultSetExtractorClass = ClientResultSetExtractorClass.class)
    List<Client> findAll();

    @Query(
            value =
                    """
                select c.id           as client_id,
                       c.name         as client_name,
                       a.id           as address_id,
                       a.street       as client_address,
                       p.id           as phone_id,
                       p.number       as client_phone
                from client c
                         left outer join address a
                                         on c.id = a.client_id
                         left outer join phone p
                                         on p.client_id = c.id
                where c.name = :name
                                                              """,
            resultSetExtractorClass = OptionalClientExtractorClass.class)
    Optional<Client> findByName(String name);

    @Override
    @Query(
            value =
                    """
                select c.id           as client_id,
                       c.name         as client_name,
                       a.id           as address_id,
                       a.street       as client_address,
                       p.id           as phone_id,
                       p.number       as client_phone
                from client c
                         left outer join address a
                                         on c.id = a.client_id
                         left outer join phone p
                                         on p.client_id = c.id
                where c.id = :id
                                                              """,
            resultSetExtractorClass = OptionalClientExtractorClass.class)
    Optional<Client> findById(Long id);

    @Query("select * from client where upper(name) = upper(:name)")
    Optional<Client> findByNameIgnoreCase(@Param("name") String name);

    @Modifying
    @Query("update client set name = :newName where id = :id")
    void updateName(@Param("id") long id, @Param("newName") String newName);
}
