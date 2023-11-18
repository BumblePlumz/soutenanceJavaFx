package fr.cda.scraping.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "label")
    private String label;

    public Type() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", label='" + label + '\'' +
//                ", annonces=" + annonces +
                '}';
    }
}
