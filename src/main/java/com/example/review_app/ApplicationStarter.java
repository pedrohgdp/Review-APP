package com.example.review_app;

import com.example.review_app.classes.DataBaseController;
import com.example.review_app.classes.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ApplicationStarter extends Application{
    DataBaseController db_controller = new DataBaseController();
    Player player = new Player();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStarter.class.getResource("main_page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        stage.setTitle("Review App!");
        stage.setScene(scene);
        stage.show();
        db_controller.loadSubjectsFromDb();
    }

    @Override
    public void init() throws IOException{
        FileOutputStream fos = new FileOutputStream("src/main/resources/DB/PlayerDate.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oss.writeString("");
    }

    @Override
    public void stop() throws IOException{

    }

    public static void main(String[] args) {
        launch();
    }

}