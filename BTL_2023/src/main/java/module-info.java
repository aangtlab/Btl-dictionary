module com.example.btl_2023 {
    requires javafx.controls;
    requires javafx.fxml;
    requires freetts;


    opens com.example.btl_2023 to javafx.fxml;
    exports com.example.btl_2023;
}