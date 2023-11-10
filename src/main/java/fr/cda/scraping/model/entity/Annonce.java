package fr.cda.scraping.model.entity;
import fr.cda.scraping.utils.SEARCH;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "annonce")
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "href")
    private String href;
    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private SEARCH type;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "city")
    private String city;
    @Column(name = "department")
    private String department;
    @Column(name = "postcode")
    private String postcode;
    @Column(name = "price")
    private long price;
    @Column(name = "size")
    private long size;
    @Column(name = "imgUrl")
    private String imgUrl;
    @Column(name = "dateCreation", columnDefinition = "DATETIME")
    private LocalDateTime dateCreation;
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }

    public Annonce() {
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getHref() {
        return href;
    }
    public void setHref(String href) {
        this.href = href;
    }
    public SEARCH getType() {
        return type;
    }
    public void setType(SEARCH type) {
        this.type = type;
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
    public long getPrice() {
        return price;
    }
    public void setPrice(long price) {
        this.price = price;
    }
    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Annonce{");
        sb.append("id=").append(id);
        sb.append(", type='").append(type).append('\'');
        sb.append(", location='").append(city).append('\'');
        sb.append(", price='").append(price).append('\'');
        sb.append(", size='").append(size).append('\'');
        sb.append('}');
        return sb.toString();
    }
}