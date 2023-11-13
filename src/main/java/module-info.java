module fr.cda.scraping {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.sql;
    requires spring.security.crypto;
    requires org.apache.logging.log4j;
    requires annotations;
    requires osmosis.core;
    requires osmosis.pbf;
    requires osmosis.osm.binary;
    requires osmosis.xml;
    requires htmlunit;
    requires com.google.gson;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;

    opens fr.cda.scraping to javafx.fxml;
    exports fr.cda.scraping;
    exports fr.cda.scraping.controller;
    opens fr.cda.scraping.controller to javafx.fxml;
    exports fr.cda.scraping.model.entity;
    opens fr.cda.scraping.model.entity to javafx.fxml, org.hibernate.orm.core;

}