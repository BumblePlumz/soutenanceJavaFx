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
    requires sib.api.v3.sdk;
    requires java.sql;
    requires spring.security.crypto;
    requires org.apache.logging.log4j;
    requires annotations;
    requires osmosis.xml;
    requires htmlunit;
    requires com.google.gson;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires mysql.connector.j;
    requires org.apache.commons.lang3;

    opens fr.cda.immobilier to javafx.fxml;
    exports fr.cda.immobilier;
    exports fr.cda.immobilier.controller;
    opens fr.cda.immobilier.controller to javafx.fxml;
    exports fr.cda.immobilier.model.entity;
    opens fr.cda.immobilier.model.entity to javafx.fxml, org.hibernate.orm.core, com.google.gson;

}