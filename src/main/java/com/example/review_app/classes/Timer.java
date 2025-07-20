package com.example.review_app.classes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

//Here I put the code for count the seconds and minutes and update label text

public class Timer {
    //Variables
    //Timeline is something that run ins thread of UI
    final Timeline timeLine = new Timeline();
    private Integer seconds = 0;
    private Integer minutes = 0;
    //I create a label for put my label for update
    private Label labelTime;

    public void configureTime(Label labelOfMyClass){
        //I will pass my label in main_page for that function
        //And label in that class became the label of main_page
        labelTime = labelOfMyClass;
        //KeyFrame is something that can run in TimeLine and we can set a seconds to that happen
        //Like that is executed and 1 second before is executed again
        //KeyFrame have duration argument and a action
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            //The action is count the seconds and minutes and update the label text
            if(seconds == 59){
                minutes++;
                seconds = 0;
                System.out.println(seconds);
            }else{
                seconds++;
                System.out.println(seconds);
            }

            labelTime.setText(minutes + "m " + seconds + "s");
        });
        //We send your keyframe to timeline
        //And set INDEFINITE for run as much as we want
        timeLine.getKeyFrames().add(keyFrame);
        timeLine.setCycleCount(Animation.INDEFINITE);
    }


    //Functions for start and stop the timeline
    public void start(){
        System.out.println("contando");
        seconds = 0;
        timeLine.play();
    }

    public void stop(){
        System.out.println("parando contagem");
        timeLine.stop();
    }
}


// Explicando a timeline nao faz nada sem keyframe
// Ela e cini yn cribine ela conta o tempo e quando chegar o tempo de executar uma keyframe ela executa
// Esse tempo e determinado na keyframe
// Keyframe recebe um tempo e uma funcao lambda event e o que vai ser feito ( ou seja o evento em si )