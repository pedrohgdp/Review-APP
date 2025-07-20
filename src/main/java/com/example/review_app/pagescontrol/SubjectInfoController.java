package com.example.review_app.pagescontrol;

import com.example.review_app.classes.DataBaseController;
import com.example.review_app.classes.Subject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SubjectInfoController {
    @FXML
    private Button deleteButton;
    @FXML
    private Label dateLabel;
    @FXML
    private Label alreadyReviewLabel;
    //Took my actual root ( of the tree I am )
    @FXML
    private Label questionsWrong;

    private String subjectName;
    DataBaseController db_controller = new DataBaseController();

    //We create a father object
    private SeeSubjectController parentController;

    //Create a method to set the father of that class
    public void setParentController(SeeSubjectController parentController){
        this.parentController = parentController;
    }

    public void alterSubjectName(String name){
        this.subjectName = name;
    }

    @FXML
    private void deleteButtonOnClick(){
        //We romeve the subject on db and priorityqueue
        db_controller.removeSubject(subjectName);
        DataBaseController.priorityQueue.removeIf(subject -> subject.getName().equals(subjectName));
        System.out.println("Removed from database and queue");
        //If we have parent we refreshListView
        if(parentController != null){
            parentController.refreshListView();
        }
        // We took the scene and window that deleteButton existe
        // And put that in a stage variable of the type Stage
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        //And close that stage
        //Stage is what we see in the screen ( the window and gui components in the window )
        // One stage display a scene.
        stage.close();
    }

    public void loadSubjectDate(String name){
        for(Subject subject : DataBaseController.priorityQueue){
            if(subject.getName().equals(name)){
                dateLabel.setText("Date placed: " + subject.getDate());
                questionsWrong.setText("Wrongs questions: " + subject.getWrongsQuestions());
                break;
            }
        }
    }

}
