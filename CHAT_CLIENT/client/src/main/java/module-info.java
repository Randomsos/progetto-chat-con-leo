module com.samu.leo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens com.samu.leo to javafx.fxml;
    exports com.samu.leo;
}
