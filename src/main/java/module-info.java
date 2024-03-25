module com.example.drohne23 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires lombok;
    requires javafx.media;
    requires org.jetbrains.annotations;


    opens com.example.drohne23 to javafx.fxml;
    exports com.example.drohne23.drone;
    exports com.example.drohne23.drone.menu;

}