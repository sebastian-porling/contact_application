package se.experis.academy.contact_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.contact_application.model.Contact;
import se.experis.academy.contact_application.repository.ContactRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    ContactRepository contactRepository;

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

    @PutMapping("/contact/create")
    public Contact addContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    @PutMapping("/contact/update")
    public Contact updateContact(@RequestBody Contact contact) {
        if (contactRepository.existsById(contact.getId())) return contactRepository.save(contact);
        return new Contact();
    }

    @GetMapping("/contact/search")
    public List<Contact> search(@RequestParam("query") String query) {
        return contactRepository.findAllByActiveTrueAndNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrContactNumberContainingIgnoreCase(query, query, query);
    }
}
