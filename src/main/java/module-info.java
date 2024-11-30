module WatChill.watchit {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;


    opens WatChill to javafx.fxml;
    exports WatChill;
}