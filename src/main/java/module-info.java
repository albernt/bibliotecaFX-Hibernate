module org.example.bibliotecafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    opens controllers to javafx.fxml;
    opens entities to org.hibernate.orm.core;

    exports controllers;
    exports entities;


    exports Main;
    opens Main to javafx.graphics;

}