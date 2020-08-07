package se.experis.academy.contact_application.model;

import javax.persistence.*;

/**
 * Representing a contact for the database and data object
 */
@Entity(name = "contacts")
public class Contact {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

    @Column(name = "active", nullable = false)
    private boolean active = true ;

    /**
     * Constructor
     * @param name
     * @param email
     * @param contactNumber
     */
    public Contact(String name, String email, String contactNumber) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
    }

    public Contact() { }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getContactNumber() { return contactNumber; }

    public void setInactive() { active = false;}

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", active=" + active +
                '}';
    }
}
