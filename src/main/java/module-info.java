module org.openjfx.demo {
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.hibernate.orm.core;

    opens org.openjfx.flights to javafx.fxml;
    exports org.openjfx.flights;
    exports org.openjfx.flights.models;
    opens org.openjfx.flights.models to javafx.fxml;
    exports org.openjfx.flights.database;
    opens org.openjfx.flights.database to javafx.fxml;
    exports org.openjfx.flights.dao;
    opens org.openjfx.flights.dao to javafx.fxml;
    exports org.openjfx.flights.controllers;
    opens org.openjfx.flights.controllers to javafx.fxml;
}