module com.example.batchapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.logging;
    requires java.desktop;

    opens com.example.batchapp to javafx.fxml;
    exports com.example.batchapp;
}