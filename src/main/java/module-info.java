module com.example.review_app {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.review_app to javafx.fxml;
    exports com.example.review_app;
}