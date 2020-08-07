package se.experis.academy.contact_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.contact_application.model.Contact;
import se.experis.academy.contact_application.repository.ContactRepository;

import java.util.List;

/**
 * The Rest api controller
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    ContactRepository contactRepository;

    /**
     * Gets all contacts
     * @return contacts in a list
     */
    @GetMapping("/contacts")
    public List<Contact> getContacts() {
        return contactRepository.findByActiveTrue();
    }

    @DeleteMapping("/contact/delete/{id}")
    public void deleteContact(@PathVariable("id") int id) {
        if (contactRepository.existsById(id)){
            Contact contact = contactRepository.findById(id);
            contact.setInactive();
            contactRepository.save(contact);
        }
    }

    /**
     * Adds a contact to the database
     * @param contact
     * @return The contact if succesfull
     */
    @PutMapping("/contact/create")
    public Contact addContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    /**
     * Updates the contact
     * @param contact
     * @return the updated contact if successfull
     */
    @PutMapping("/contact/update")
    public Contact updateContact(@RequestBody Contact contact) {
        if (contactRepository.existsById(contact.getId())) return contactRepository.save(contact);
        return new Contact();
    }

    /**
     * Finds contacts that matches the name, email or/and email.
     * @param query search string
     * @return list of contacts
     */
    @GetMapping("/contact/search")
    public List<Contact> search(@RequestParam("query") String query) {
        return contactRepository.search(query.toLowerCase());
    }
}
