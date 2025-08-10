    package com.example.review_app.pagescontrol;

    import com.example.review_app.classes.DataBaseController;
    import com.example.review_app.classes.IaApi;
    import com.example.review_app.classes.QuestionAndAnswer;
    import com.example.review_app.classes.Subject;
    import com.google.gson.Gson;
    import javafx.fxml.FXML;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.text.TextFlow;

    import javax.xml.crypto.Data;
    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.List;

    public class QuestionAndAnswerController {
        //This classe will do the convert of string in a java OBJ
        //And put in the text area and button the questions

        // VARIABLES / OBJECTS
        private Gson gson;
        private IaApi iaApi;
        private QuestionAndAnswer questionAndAnswer;
        MainPageController mainPageController;
        @FXML
        Label question;
        @FXML
        Button optionOne;
        @FXML
        Button optionTwo;
        @FXML
        Button optionThree;
        @FXML
        Button optionFour;
        @FXML
        Button optionFive;
        // END VARIABLES / OBJECTS

        @FXML
        public void initialize(){
            System.out.println("This method was called");
            this.gson = new Gson();
            this.iaApi = new IaApi();
        }

        public void loadAnswers(Subject sortedSubject){
            if (sortedSubject == null || sortedSubject.getName().isEmpty()){
                System.out.println("None subject sorted");
                return;
            }

            try{
                //I create a jsonreponse for ia and create the object
                String jsonResponse = iaApi.getAnswer(sortedSubject.getName());
                this.questionAndAnswer = gson.fromJson(jsonResponse, QuestionAndAnswer.class);
                System.out.println("Question: " + questionAndAnswer.getQuestion());

                //set the text of the label
                question.setText(questionAndAnswer.getQuestion());

                //create a string of the answers
                List<String> shuffleAnswers = new ArrayList<>();
                shuffleAnswers.add(questionAndAnswer.getCorrectAnswer());
                shuffleAnswers.addAll(questionAndAnswer.getIncorrectAnswers());
                //shuffle that
                Collections.shuffle(shuffleAnswers);
                //create a list of button
                List<Button> buttonList = List.of(optionOne, optionTwo, optionThree, optionFour, optionFive);
                for(int i = 0; i < buttonList.size(); i++){ //for every button we put the answer suffled
                    buttonList.get(i).setText(shuffleAnswers.get(i));
                }

                for(Button button : buttonList){
                    if(button.getText().equals(questionAndAnswer.getCorrectAnswer())){
                        button.setOnMouseClicked(mouseEvent -> {
                            button.setStyle("-fx-background-color: #00FF00");
                            disableButtons();
                            double actualWrongsQuestion = sortedSubject.getWrongsQuestions();
                            sortedSubject.setWrongsQuestions(actualWrongsQuestion - 1);
                            DataBaseController dbController = new DataBaseController();
                            dbController.updateSubject(sortedSubject);
                        });
                    }else{
                        button.setOnMouseClicked(mouseEvent -> {
                            button.setStyle("-fx-background-color: #FF0000");
                            disableButtons();
                            double actualWrongsQuestion = sortedSubject.getWrongsQuestions();
                            sortedSubject.setWrongsQuestions(actualWrongsQuestion + 1);
                            DataBaseController dbController = new DataBaseController();
                            dbController.updateSubject(sortedSubject);
                        });
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        private void disableButtons() {
            List.of(optionOne, optionTwo, optionThree, optionFour, optionFive).forEach(btn -> btn.setDisable(true));
        }

    }
