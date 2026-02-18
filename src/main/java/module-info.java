module com.example.gestionbibliotheque {
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
    requires java.sql;
    requires static lombok;
    requires java.persistence;
    exports com.example.gestionbibliotheque.Model;
    opens com.example.gestionbibliotheque.Controller to javafx.fxml;
    requires org.hibernate.orm.core;
    opens com.example.gestionbibliotheque.Model to org.hibernate.orm.core, java.persistence;

    opens com.example.gestionbibliotheque to javafx.fxml;
    exports com.example.gestionbibliotheque;
}