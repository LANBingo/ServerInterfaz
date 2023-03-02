module com.lanbingo.servidorbingo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.lanbingo.servidorbingo to javafx.fxml;
    exports com.lanbingo.servidorbingo;
    exports com.lanbingo.servidorbingo.controller;
    opens com.lanbingo.servidorbingo.controller to javafx.fxml;
}