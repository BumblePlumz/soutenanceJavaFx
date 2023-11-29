package fr.cda.immobilier.model.entity;

import fr.cda.immobilier.model.repository.RoleRepository;
import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "lastname", nullable = false)
    private String lastname;
    @Column(name = "firstname", nullable = false)
    private String firstname;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    @PrePersist
    private void prePersist() {
        if (role == null) {
            role = getDefaultRole();
        }
    }

    public User() {
    }

    public User(String lastname, String firstname, String email, String password) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String name) {
        this.lastname = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    private Role getDefaultRole(){
        RoleRepository rr = new RoleRepository();
        rr.start();
        Role r = rr.findById((long)2);
        rr.close();
        return r;
    }
}
