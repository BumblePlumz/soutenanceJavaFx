package fr.cda.scraping.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column
    private String code;
    @Column(name = "city")
    private String city;
    @Column(name = "department")
    private String department;
    @Column(name = "postcode")
    private String postcode;
    @OneToMany(mappedBy = "address")
    private List<Annonce> annonces;
    public Address() {
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", city='" + city + '\'' +
                ", department='" + department + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
