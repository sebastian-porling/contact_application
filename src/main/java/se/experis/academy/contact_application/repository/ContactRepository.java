package se.experis.academy.contact_application.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.experis.academy.contact_application.model.Contact;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {

    /**
     * Gets all active contacts
     * @return list of active contacts
     */
    List<Contact> findByActiveTrue();

    /**
     * Finds contact by id
     * @param id the id as an integer
     * @return a contact
     */
    Contact findById(int id);

    /**
     * Deletes a contact
     * @param contact contact to be deleted
     */
    @Override
    void delete(Contact contact);

    /**
     * Saves a contact to the database
     * @param s The contact
     * @param <S>
     * @return The saved contact
     */
    @Override
    <S extends Contact> S save(S s);

    /**
     * Finds contacts that matches name, email or/and contact number
     * @param query string
     * @return list of contacts
     */
    @Query("select c from contacts c where c.active = true and " +
            "(lower(c.name) like %:query% or " +
            "lower(c.email) like %:query% or " +
            "lower(c.contactNumber) like %:query%)")
    List<Contact> search(@Param("query") String query);
}
