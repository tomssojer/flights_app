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

    opens org.openjfx.demo to javafx.fxml;
    exports org.openjfx.demo;
    exports org.openjfx.demo.models;
    opens org.openjfx.demo.models to javafx.fxml;
    exports org.openjfx.demo.database;
    opens org.openjfx.demo.database to javafx.fxml;
    exports org.openjfx.demo.dao;
    opens org.openjfx.demo.dao to javafx.fxml;
}