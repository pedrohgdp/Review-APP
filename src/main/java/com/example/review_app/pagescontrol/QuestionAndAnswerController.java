    package com.example.review_app.pagescontrol;

    import com.example.review_app.classes.DataBaseController;
    import com.example.review_app.classes.IaApi;
    import com.example.review_app.classes.QuestionAndAnswer;
    import com.example.review_app.classes.Subject;
    import com.google.gson.Gson;
    import com.google.gson.reflect.TypeToken;
    import javafx.animation.PauseTransition;
    import javafx.fxml.FXML;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import java.lang.reflect.Type;
    import com.google.gson.reflect.TypeToken;
    import javafx.util.Duration;

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
        private List<QuestionAndAnswer> questionsList;
        private int currentQuestion = 0; //Start in the first question

        @FXML
        Label currentQuestionLabel;
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
        public void initialize() {
            System.out.println("This method was called");
            this.gson = new Gson();
            this.iaApi = new IaApi();
        }

        public void initializeQuestionList(Subject sortedSubject) {
            if (sortedSubject == null || sortedSubject.getName().isEmpty()) {
                System.out.println("None subject sorted");
                return;
            }

            try {
                //I create a jsonreponse for ia and create the object
                String jsonResponse = iaApi.getAnswer(sortedSubject.getName());
                // TypeToken saves the full type of the list (e.g., List<MyClass>).
                // This is needed because the JVM erases this specific type at runtime,
                // leaving just a plain "List". TypeToken preserves the original type
                // so that Gson knows exactly what kind of objects to create in the list.
                Type listType = new TypeToken<List<QuestionAndAnswer>>() {}.getType();
                this.questionsList = gson.fromJson(jsonResponse, listType);

                //IF THE LIST IS NOT NULL, DISPLAY FIRST QUESTION
                if (questionsList != null && !questionsList.isEmpty()) {
                    displayQuestion(sortedSubject, currentQuestion);
                } else {
                    question.setText("Unable to send questions.");
                    disableButtons();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void displayQuestion(Subject sortedSubject, int indexQuestion) {
            try {
                System.out.println("Question: " + questionsList.get(indexQuestion).getQuestion());
                currentQuestionLabel.setText("Current question: " + currentQuestion + 1);

                resetButtons();

                //set the text of the label
                question.setText(questionsList.get(indexQuestion).getQuestion());

                //create a string of the answers
                List<String> shuffleAnswers = new ArrayList<>();
                shuffleAnswers.add(questionsList.get(indexQuestion).getCorrectAnswer());
                shuffleAnswers.addAll(questionsList.get(indexQuestion).getIncorrectAnswers());
                //shuffle that
                Collections.shuffle(shuffleAnswers);
                //create a list of button
                List<Button> buttonList = List.of(optionOne, optionTwo, optionThree, optionFour, optionFive);
                for (int i = 0; i < buttonList.size(); i++) { //for every button we put the answer suffled
                    buttonList.get(i).setText(shuffleAnswers.get(i));
                }

                for (Button button : buttonList) {
                    if (button.getText().equals(questionsList.get(currentQuestion).getCorrectAnswer())) {
                        button.setOnMouseClicked(mouseEvent -> {
                            button.setStyle("-fx-background-color: #00FF00");
                            disableButtons();
                            double actualWrongsQuestion = sortedSubject.getWrongsQuestions();
                            sortedSubject.setWrongsQuestions(actualWrongsQuestion - 1);
                            DataBaseController dbController = new DataBaseController();
                            dbController.updateSubject(sortedSubject);
                            //Do a pause before go to next question
                            PauseTransition pause = new PauseTransition(Duration.seconds(1));
                            pause.setOnFinished(actionEvent -> goToNextQuestion(sortedSubject));
                            pause.play();
                        });
                    } else {
                        button.setOnMouseClicked(mouseEvent -> {
                            button.setStyle("-fx-background-color: #FF0000");
                            disableButtons();
                            double actualWrongsQuestion = sortedSubject.getWrongsQuestions();
                            sortedSubject.setWrongsQuestions(actualWrongsQuestion + 1);
                            DataBaseController dbController = new DataBaseController();
                            dbController.updateSubject(sortedSubject);
                            PauseTransition pause = new PauseTransition(Duration.seconds(1));
                            pause.setOnFinished(actionEvent -> goToNextQuestion(sortedSubject));
                            pause.play();
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void goToNextQuestion(Subject sortedSubject){
            currentQuestion++;

            if(currentQuestion < questionsList.size()){
                displayQuestion(sortedSubject, currentQuestion);
            }else{
                question.setText("Dont have more question");
                disableButtons();
            }
        }



        private void disableButtons() {
            List.of(optionOne, optionTwo, optionThree, optionFour, optionFive).forEach(btn -> btn.setDisable(true));
        }

        private void resetButtons() {
            List.of(optionOne, optionTwo, optionThree, optionFour, optionFive).forEach(btn -> {
                btn.setDisable(false);
                btn.setStyle(null); // Reseta o estilo para o padrão do CSS
            });
        }
    }

