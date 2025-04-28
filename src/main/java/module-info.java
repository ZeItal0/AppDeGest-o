module software.consultoria {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.controlsfx.controls;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;

    opens software.consultoria to javafx.fxml;
    opens Models to javafx.base, javafx.fxml;

    exports software.consultoria;
    exports Models;
}