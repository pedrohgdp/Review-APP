package com.example.review_app.pagescontrol;

import com.example.review_app.classes.DataBaseController;
import com.example.review_app.classes.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;

//Controller for see subject page
public class SeeSubjectController {
    //Create fxml things
    @FXML
    private Button returnButton;
    @FXML
    private ListView listViewArea = new ListView<String>();
    //End

    //Method that initialize the fillListView Method and make the text in listViewArea clickable
    //After everything create initialize is started automatically
    @FXML
    private void initialize() {
        System.out.println("Method was been called");
        refreshListView();

        //Take listViewArea when that is clicked and create a function
        //That have EventHandler<MouseEvent> as a event
        //Thats is a lambda function because setOnMouseCLicked recive a abstract method that have a function to be
        //override on that ( handle function ) so we can call lambda function
        listViewArea.setOnMouseClicked(mouseEvent -> {
            //Check if we clicked 2 times in the listViewArea object
            if(mouseEvent.getClickCount() == 2){
                //Pass the selected item to string
                //GetSelectionModel is like a controller of what is selected in the gui
                //It control what can be selecioned, register what is select at the moment, and others.
                String selectedItem = listViewArea.getSelectionModel().getSelectedItem().toString();
                //If selectedItem is not null
                //Open the subjectInfo page
                if(selectedItem != null){
                    //Create a loader to load subjectInfo fxml
                    FXMLLoader loader = new FXMLLoader(MainPageController.class.getResource("/com/example/review_app/subjectInfo.fxml"));
                    //Criate a root
                    //That root is a root of interface tree
                    //That represents the principal component of my new scene
                    //Create the new scene and stage(the windowns) based in the tree that root stores.
                    Parent root;
                    //Load the root with the loader
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    //CALLBACK
                    //Define subInfController a sun controller
                    SubjectInfoController subInfController = loader.getController();

                    //We call the setParentController of subjectInfoController
                    subInfController.setParentController(this);
                    subInfController.alterSubjectName(selectedItem);
                    subInfController.loadSubjectDate(selectedItem);


                    //Create a new stage and a newScene, and load the stage with the scene
                    Stage newStage = new Stage();
                    newStage.setTitle(selectedItem);
                    Scene newScene = new Scene(root, 321, 274);
                    newStage.setScene(newScene);
                    newStage.show();
                }
            }
        });

    }

    //That method is to return to the main page
    @FXML
    private void returnButtonOnClick() throws IOException {
        Stage stage = (Stage) returnButton.getScene().getWindow();
        FXMLLoader seeFxml = new FXMLLoader(SeeSubjectController.class.getResource("/com/example/review_app/main_page.fxml"));
        Scene scene = new Scene(seeFxml.load(), 500, 400);
        stage.setTitle("Main Page!");
        stage.setScene(scene);
        stage.show();
    }

    //This method populates the ListView with subject names from the static PriorityQueue.
    public void refreshListView(){
        ObservableList<String> itemsName = FXCollections.observableArrayList();
        for(Subject subject : DataBaseController.priorityQueue){
            itemsName.add(subject.getName());
        }
        listViewArea.setItems(itemsName);
    }

}
