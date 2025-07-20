package com.example.review_app.classes;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class QuestionAndAnswer {
    private String question;
    @SerializedName("incorrect_answers")
    private List<String> incorrectAnswers;
    @SerializedName("correct_answer")
    private String correctAnswer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
