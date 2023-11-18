package fr.cda.scraping.model.entity;

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
    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
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
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
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
        return "Annonce{" +
                "id=" + id +
                ", href='" + href + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", address=" + address +
                ", price=" + price +
                ", size=" + size +
                ", imgUrl='" + imgUrl + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
