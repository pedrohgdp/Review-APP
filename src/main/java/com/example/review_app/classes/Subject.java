package com.example.review_app.classes;


public class Subject implements Comparable<Subject>{

    //6 HIGH PRIORITY
    //3 MEDIUM PRIORITY
    //1 LOW PRIORITY

    private String name;
    private String date;
    private double wrongsQuestions;
    private double weight;

    private int calculatePriority(double wrongsQuestions){
        if (wrongsQuestions >= 6) {
            return 0; // High
        } else if (wrongsQuestions >= 3) {
            return 1; // Medium
        } else {
            return 2; // Low
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getWrongsQuestions() {
        return wrongsQuestions;
    }

    public void setWrongsQuestions(double wrongsQuestions) {
        this.wrongsQuestions = wrongsQuestions;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", wrongsQuestions=" + wrongsQuestions +
                '}';
    }

    @Override
    public int compareTo(Subject otherSubject){
        int myPriotity = this.calculatePriority(this.getWrongsQuestions());
        int otherPriority = otherSubject.calculatePriority(otherSubject.getWrongsQuestions());

        return myPriotity - otherPriority;
    }
}
