module at.fhtechnikum.communitygui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;

    opens at.fhtechnikum.communitygui to javafx.fxml;
    exports at.fhtechnikum.communitygui;
}