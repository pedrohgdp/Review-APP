module com.example.review_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;
    requires java.desktop;
    requires java.xml.crypto;
    requires google.genai;
    requires java.dotenv;
    requires com.google.gson;
    opens com.example.review_app.pagescontrol to javafx.fxml;
    opens com.example.review_app.classes to javafx.fxml, com.google.gson;



    opens com.example.review_app to javafx.fxml;
    exports com.example.review_app;
}