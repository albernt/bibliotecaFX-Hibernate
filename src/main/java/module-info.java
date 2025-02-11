module com.example.biblioteca {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.hibernate.orm.core;
    requires java.sql;

    opens com.example.biblioteca to javafx.fxml;
    opens com.example.biblioteca.entities to org.hibernate.orm.core;

    opens controllers to javafx.fxml;
    exports controllers;
}
