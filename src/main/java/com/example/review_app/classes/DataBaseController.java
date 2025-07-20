package com.example.review_app.classes;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.PriorityQueue;

public class DataBaseController {
    private final String URL = "jdbc:sqlite:/home/pedrohgdp/Projetos/Review-APP/src/main/resources/DB/DataBase.db";
    public static PriorityQueue<Subject> priorityQueue = new PriorityQueue<>();
    private LocalDate today = LocalDate.now();
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private String formattedDate = today.format(FORMATTER);

    //ADD SUBJECT ON DATABASE AND HEAP (PriorityQueue)
    public void addSubject(String name){
        String addSubjectQuery = "INSERT INTO subjects (name, datePlaced, wrongsQuestions) VALUES (?, ?, ?);";
        try(Connection conn = DriverManager.getConnection(URL)){
            // We set the values for the '?' placeholders in the prepared statement.
            PreparedStatement prepSt = conn.prepareStatement(addSubjectQuery);
            prepSt.setString(1, name);
            prepSt.setString(2, formattedDate);
            prepSt.setInt(3, 0);
            prepSt.setInt(4, 0);
            //If we only want to execute the update, we don't need to store the result in a variable.
            //But we want store that for add that as a object
            int manyRowsAffected = prepSt.executeUpdate();
            //If one row was affected, we also add the new Subject object to the PriorityQueue
            if(manyRowsAffected == 1){
                Subject subjectObj = new Subject();
                subjectObj.setName(name);
                subjectObj.setDate(formattedDate);
                subjectObj.setAlreadyReviewed("False");
                subjectObj.setWrongsQuestions(0);
                priorityQueue.add(subjectObj);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Query to remove the object
    public void removeSubject(String name){
        String deleteSubjectQuery = "DELETE FROM subjects WHERE name = ?";
            try(Connection conn = DriverManager.getConnection(URL)){
                PreparedStatement prepSt = conn.prepareStatement(deleteSubjectQuery);
                prepSt.setString(1, name);
                prepSt.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    // Loads all subjects from the database into the PriorityQueue, typically when the app starts.
    public void loadSubjectsFromDb(){
        String loadQuery = "SELECT * FROM subjects";
        priorityQueue.clear();
        try(Connection conn = DriverManager.getConnection(URL)){
            PreparedStatement prepSt = conn.prepareStatement(loadQuery);
            ResultSet rs = prepSt.executeQuery();
            while(rs.next()){
                Subject subject = new Subject();
                subject.setName(rs.getString("name"));
                subject.setDate(rs.getString("datePlaced"));
                subject.setWrongsQuestions(rs.getInt("wrongsQuestions"));
                priorityQueue.add(subject);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateSubject(Subject subject) {
        String sql = "UPDATE subjects SET wrongsQuestions = ? WHERE name = ?";

        try(Connection conn = DriverManager.getConnection(URL)){
            // We set the values for the '?' placeholders in the prepared statement.
            PreparedStatement prepSt = conn.prepareStatement(sql);
            prepSt.setDouble(1, subject.getWrongsQuestions());
            prepSt.setString(3, subject.getName());
            prepSt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
