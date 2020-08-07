package se.experis.academy.contact_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.experis.academy.contact_application.model.Contact;
import se.experis.academy.contact_application.repository.ContactRepository;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    ContactRepository contactRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Contact> contacts = contactRepository.findByActiveTrue();
        model.addAttribute("contacts", contacts);
        return "index";
    }
}
