package com.example.review_app;

import com.example.review_app.classes.DataBaseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ApplicationStarter extends Application{
    DataBaseController db_controller = new DataBaseController();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStarter.class.getResource("main_page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        stage.setTitle("Review App!");
        stage.setScene(scene);
        stage.show();
        db_controller.loadSubjectsFromDb();
    }

    public static void main(String[] args) {
        launch();
    }

}