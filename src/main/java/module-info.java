module WatChill.watchit {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;


    opens WatChill to javafx.fxml, com.fasterxml.jackson.databind;
    opens WatChill.Content.Movie to javafx.fxml, com.fasterxml.jackson.databind;
    opens WatChill.Content to javafx.fxml, com.fasterxml.jackson.databind;
    opens WatChill.Content.Series to javafx.fxml, com.fasterxml.jackson.databind;
    opens WatChill.Subscription to javafx.fxml, com.fasterxml.jackson.databind;
    opens WatChill.Cast to javafx.fxml, com.fasterxml.jackson.databind;
    opens WatChill.Director to javafx.fxml, com.fasterxml.jackson.databind;
    opens WatChill.Review to javafx.fxml, com.fasterxml.jackson.databind;
    opens WatChill.Search to javafx.fxml, com.fasterxml.jackson.databind;

    exports WatChill;
    exports WatChill.Content;
}