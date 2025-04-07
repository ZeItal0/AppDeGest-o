module software.consultoria {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens software.consultoria to javafx.fxml;
    opens Models to javafx.base, javafx.fxml;

    exports software.consultoria;
    exports Models;
}