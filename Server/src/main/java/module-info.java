module com.example.severmodul {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.severmodul to javafx.fxml;
    exports com.example.severmodul;
}