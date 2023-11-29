package fr.cda.immobilier.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "label",unique = true, nullable = false)
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
        StringBuilder sb = new StringBuilder();
        sb.append("Type numéro ").append(id).append("\n");
        sb.append("\t\tpropriétés : \n");
        sb.append(formatProperty("label", label));
        return sb.toString();
    }

    private String formatProperty(String propertyName, String propertyValue) {
        return String.format("\t\t\t%-15s: %s\n", propertyName, propertyValue);
    }

}
