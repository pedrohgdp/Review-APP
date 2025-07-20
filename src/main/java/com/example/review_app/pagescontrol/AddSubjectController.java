package com.example.review_app.pagescontrol;

import com.example.review_app.classes.DataBaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddSubjectController {
    @FXML
    TextField enterSubjectArea;
    @FXML
    Button sendButton;

    DataBaseController dbController = new DataBaseController();

    //add a new object and subject on db
    @FXML
    public void sendButtonOnClick(){
        String subjectName = enterSubjectArea.getText();
        //remove all spaces and see if its is empty
        if(subjectName.trim().isEmpty()){
            //If yes send a alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("INVALID INPUT");
            alert.setContentText("Invalid input, try again");
            alert.showAndWait();
        }else{
            //No we try crate the object and subject on db
            try{
                dbController.addSubject(subjectName);
                Alert alertOk = new Alert(Alert.AlertType.CONFIRMATION);
                alertOk.setTitle("Subject added");
                alertOk.setContentText("The subject was added to the database successfully.");
                alertOk.showAndWait();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}
