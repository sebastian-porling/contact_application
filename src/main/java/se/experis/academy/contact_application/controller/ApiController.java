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

    @DeleteMapping("/delete/{id}")
    public void deleteContact(@PathVariable("id") int id) {
        if (contactRepository.existsById(id)){
            Contact contact = contactRepository.findById(id);
            contact.setInactive();
            contactRepository.save(contact);
        }
    }

    @PutMapping("/contact/create")
    public void addContact(@RequestBody Contact contact) {
        contactRepository.save(contact);
    }

    @PutMapping("/contact/update")
    public void updateContact(@RequestBody Contact contact) {
        if (contactRepository.existsById(contact.getId())) contactRepository.save(contact);
    }

    @GetMapping("/contact/search")
    public List<Contact> search(@RequestParam("query") String query) {
        return contactRepository.findAllByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrContactNumberContainingIgnoreCase(query, query, query);
    }
}
