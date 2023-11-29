package fr.cda.immobilier.model.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "annonce")
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Expose()
    private long id;
    @Column(name = "href", nullable = false, unique = true)
    @Expose()
    private String href;
    @Column(name = "title")
    @Expose()
    private String title;
    @Column(name = "description", columnDefinition = "TEXT")
    @Expose()
    private String description;
    @Column(name = "price")
    @Expose()
    private long price;
    @Column(name = "size")
    @Expose()
    private long size;
    @Column(name = "room")
    @Expose()
    private String room;
    @Column(name = "bedroom")
    @Expose()
    private String bedroom;
    @Column(name = "imgUrl")
    @Expose()
    private String imgUrl;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id")
    @Expose()
    private Address address;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "type_id")
    @Expose()
    private Type type;
    @Column(name = "dateUpdate", columnDefinition = "DATETIME", nullable = false)
    @Expose()
    private LocalDateTime dateUpdate;
    @Column(name = "dateCreation", columnDefinition = "DATETIME", nullable = false)
    @Expose()
    private LocalDateTime dateCreation;
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateUpdate = LocalDateTime.now();
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
    }
    public String getBedroom() {
        return bedroom;
    }
    public void setBedroom(String bedroom) {
        this.bedroom = bedroom;
    }
    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }
    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getTypeAsJson(){
        return new Gson().toJson(type);
    }
    public String getAddressAsJson(){
        return new Gson().toJson(address);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Annonce numéro ").append(id).append("\n");
        sb.append("\t\ttitre : ").append(title).append("\n");
        sb.append("\t\thref : ").append(href).append("\n");
        sb.append("\t\tpropriétés : \n");
        sb.append(formatProperty("description", description));
        sb.append(formatProperty("price", String.valueOf(price)));
        sb.append(formatProperty("size", String.valueOf(size)));
        sb.append(formatProperty("room", room));
        sb.append(formatProperty("bedroom", bedroom));
        sb.append(formatProperty("imgUrl", imgUrl));
        sb.append(formatProperty("address", address.toString()));
        sb.append(formatProperty("type", type.toString()));
        sb.append(formatProperty("dateUpdate", String.valueOf(dateUpdate)));
        sb.append(formatProperty("dateCreation", String.valueOf(dateCreation)));
        return sb.toString();
    }

    private String formatProperty(String propertyName, String propertyValue) {
        return String.format("\t\t\t%-15s: %s\n", propertyName, propertyValue);
    }

}
