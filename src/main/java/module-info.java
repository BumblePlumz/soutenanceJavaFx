module fr.cda.scraping {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires hibernate.jpa;
    requires spring.data.jpa;
    requires spring.data.commons;
    requires spring.beans;
    requires spring.context;

    opens fr.cda.scraping to javafx.fxml;
    exports fr.cda.scraping;
    exports fr.cda.scraping.controller;
    opens fr.cda.scraping.controller to javafx.fxml;
    exports fr.cda.scraping.model.entities;
    opens fr.cda.scraping.model.entities to javafx.fxml;
}