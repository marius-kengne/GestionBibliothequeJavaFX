module fr.openjava.gestionbibliothequejavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    requires org.kordamp.bootstrapfx.core;
    requires java.xml.bind;


    opens fr.openjava.gestionbibliothequejavafx to javafx.fxml;
    exports fr.openjava.gestionbibliothequejavafx;
    exports fr.openjava.gestionbibliothequejavafx.controllers;
    opens fr.openjava.gestionbibliothequejavafx.controllers to javafx.fxml;
    opens fr.openjava.gestionbibliothequejavafx.models.generated to java.xml.bind, javafx.base;

}