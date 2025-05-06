module at.fhtechnikum.communitygui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens at.fhtechnikum.communitygui to javafx.fxml;
    exports at.fhtechnikum.communitygui;
}