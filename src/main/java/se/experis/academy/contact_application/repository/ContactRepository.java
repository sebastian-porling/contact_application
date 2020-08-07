package se.experis.academy.contact_application.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.experis.academy.contact_application.model.Contact;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {
    @Override
    List<Contact> findAll();

    List<Contact> findByActiveTrue();

    Contact findById(int id);

    @Override
    void delete(Contact contact);

    @Override
    <S extends Contact> S save(S s);

    List<Contact> findAllByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrContactNumberContainingIgnoreCase(String name, String email, String contactNumber);
}
