module com.example.review_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;
    requires java.desktop;
    requires java.xml.crypto;
    opens com.example.review_app.PagesController to javafx.fxml;
    opens com.example.review_app.Classes to javafx.fxml;


    opens com.example.review_app to javafx.fxml;
    exports com.example.review_app;
}