package fr.cda.scraping.model.entities;
import javax.persistence.*;

@Entity
@Table(name = "annonces")
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "type")
    private String type;
    @Column(name = "city")
    private String city;
    @Column(name = "price")
    private String price;
    @Column(name = "size")
    private String size;

    public Annonce() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return city;
    }

    public void setLocation(String location) {
        this.city = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Annonce{");
        sb.append("id=").append(id);
        sb.append(", type='").append(type).append('\'');
        sb.append(", location='").append(city).append('\'');
        sb.append(", price='").append(price).append('\'');
        sb.append(", size='").append(size).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
