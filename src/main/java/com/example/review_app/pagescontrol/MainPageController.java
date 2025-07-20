package com.example.review_app.pagescontrol;

import com.example.review_app.classes.DataBaseController;
import com.example.review_app.classes.Subject;
import com.example.review_app.classes.Timer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class MainPageController {
    //VARIABLES ( BUTTONS, TEXTS, LABEL )
    @FXML
    private Label timer;
    @FXML
    private Button startReviewButton;
    @FXML
    private Button seeSubjectButton;
    @FXML
    private Button qAndAButton;
    @FXML
    private Button sortSubject;
    @FXML
    private Label subjectSorted;
    @FXML
    private final String ORIGINAL_COLOR = "-fx-background-color: #5e0b9e";
    @FXML
    private Button addObjectsButton;

    private boolean buttonClicked = true;
    private Double majorNumber;
    private String nameSubjectMajorNumber;
    Subject subject;
    //END VARIABLE

    //OBJECTS
    Timer timerController = new Timer();
    //END OBJECTS

    //Everything here is executable once when fxml where that controller is associate runs
    @FXML
    public void initialize(){
        //Set label timer invisivel
        timer.setVisible(false);
        timerController.configureTime(timer);
    }


    @FXML
    private void startButtonOnClick(){
        if(buttonClicked){
            timerController.start();
            timer.setVisible(true);
            startReviewButton.setText("Stop review");
            startReviewButton.setStyle("-fx-background-color: #bf0d1c");
            buttonClicked = false;
        }else{
            timerController.stop();
            timer.setVisible(false);
            startReviewButton.setText("Start review");
            startReviewButton.setStyle(ORIGINAL_COLOR);
            buttonClicked = true;
        }
    }

    //If we click in see button we go to that screen
    @FXML
    private void seeButtonOnClick() throws IOException {
       Stage stage = (Stage) seeSubjectButton.getScene().getWindow();
       FXMLLoader seeFxml = new FXMLLoader(MainPageController.class.getResource("/com/example/review_app/see_subject.fxml"));
       Scene scene = new Scene(seeFxml.load(), 700, 700);
       stage.setTitle("See Subjects!");
       stage.setScene(scene);
       stage.show();
    }

    //If click in addSubjectButton we go to that screen
    @FXML
    private void addSubjectButtonOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainPageController.class.getResource("/com/example/review_app/addSubject.fxml"));
        Parent root = loader.load();

        Stage newStage = new Stage();
        newStage.setTitle("Add subject");
        Scene newScene = new Scene(root, 321, 274);
        newStage.setScene(newScene);
        newStage.show();
    }

    //Func for sort the subject
    //I will sort one number for every subject in priorityqueue
    //And for every subject i will sum that number with wrongsquestion
    //SO the major one i will select
    //So i will use worngsquestion like a weight
    @FXML
    private void sortSubjectOnClick(){
        double size = DataBaseController.priorityQueue.size();
        this.majorNumber = -1.0;
        this.nameSubjectMajorNumber = "";
        this.subject = null;


        for(Subject currentSubject : DataBaseController.priorityQueue){
            double elpson = 0.1;
            double random = Math.random() + elpson;
            double weight = random * size;
            currentSubject.setWeight(weight);
            // Divide by size for normalization,
            // because if we don't normalize, when there are many wrong questions,
            // the algorithm may become more biased (tendencioso ) for subjects with many errors
            if((weight + currentSubject.getWrongsQuestions()) / size > majorNumber){
                majorNumber = (currentSubject.getWeight() + currentSubject.getWrongsQuestions()) / size;
                nameSubjectMajorNumber = currentSubject.getName();
                System.out.println("Subject selecioned");
                this.subject = currentSubject;
            }
        }
        System.out.println(nameSubjectMajorNumber);
        System.out.println(majorNumber);
        subjectSorted.setText(nameSubjectMajorNumber);
    }

    @FXML
    private void qAndAButtonOnClick() throws IOException{
        FXMLLoader seeFxml = new FXMLLoader(MainPageController.class.getResource("/com/example/review_app/q_and_a.fxml"));
        Stage stage = (Stage) qAndAButton.getScene().getWindow();
        Scene scene = new Scene(seeFxml.load(), 700, 700);
        QuestionAndAnswerController controllerQuestionsAndAnswer = seeFxml.getController();
        controllerQuestionsAndAnswer.loadAnswers(this.subject);

        stage.setTitle("See Subjects!");
        stage.setScene(scene);
        stage.show();
    }

    public String getNameSubjectMajorNumber() {
        return nameSubjectMajorNumber;
    }

    public void setNameSubjectMajorNumber(String nameSubjectMajorNumber) {
        this.nameSubjectMajorNumber = nameSubjectMajorNumber;
    }

    //TOMORROW IA API AN SEND THAT TO IA GERANDO UM JSON
    //VER NO RIO GSON

    

}
