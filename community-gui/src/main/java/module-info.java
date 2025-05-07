module at.fhtechnikum.communitygui {

    requires javafx.controls;
    requires javafx.fxml;

    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.datatype.jsr310;


    // Öffne für beide: FXML und Jackson
    opens at.fhtechnikum.communitygui to javafx.fxml, com.fasterxml.jackson.databind;

    exports at.fhtechnikum.communitygui;
}