package fr.cda.scraping.utils;

public enum SEARCH {
    HOUSE("Maison"),
    FLAT("Appartement"),
    LAND("terrain"),
    BUILDING("Immeuble"),
    PARKING("Parking"),
    BOX("Box");

    private String desc;
    SEARCH(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
