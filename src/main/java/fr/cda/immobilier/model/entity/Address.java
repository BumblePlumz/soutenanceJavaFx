package fr.cda.immobilier.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name = "codeSeloger")
    private String codeSeloger;
    @Column(name = "codeOuestFrance")
    private String codeOuestFrance;
    @Column(name = "city", nullable = false, unique = true)
    private String city;
    @Column(name = "department", nullable = false)
    private String department;
    @Column(name = "postcode", nullable = false)
    private String postcode;

    public Address() {
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getCodeSeloger() {
        return codeSeloger;
    }
    public void setCodeSeloger(String code) {
        this.codeSeloger = code;
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
    public String getCodeOuestFrance() {
        return codeOuestFrance;
    }
    public void setCodeOuestFrance(String codeOuestFrance) {
        this.codeOuestFrance = codeOuestFrance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Address numéro ").append(id).append("\n");
        sb.append("\t\tpropriétés : \n");
        sb.append(formatProperty("codeSeloger", codeSeloger));
        sb.append(formatProperty("codeOuestFrance", codeOuestFrance));
        sb.append(formatProperty("city", city));
        sb.append(formatProperty("department", department));
        sb.append(formatProperty("postcode", postcode));
        return sb.toString();
    }

    private String formatProperty(String propertyName, String propertyValue) {
        return String.format("\t\t\t%-15s: %s\n", propertyName, propertyValue);
    }

}
