package fr.cda.scraping.model.entity;

import fr.cda.scraping.model.repository.RoleRepository;
import fr.cda.scraping.utils.JPATools;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @PrePersist
    private void prePersist() {
        if (role == null) {
            role = getDefaultRole();
        }
    }

    public User() {
    }

    public User(String name, String firstname, String email, String password) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
