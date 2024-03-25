package com.example.drohne23.drone.menu;

import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;

public class Sound {
    public void playSound() {
        try {
            String musicFile = "D:\\HSB\\semester_4\\Prog2\\MenuNew\\Drohne_V1\\src\\main\\java\\com\\example\\drohne23\\drone\\res\\click.mp3";
            AudioClip sound = new AudioClip(musicFile);
            sound.play();
        } catch (Exception e) {
            System.err.println("Failed to play sound file: " + e.getMessage());
        }
    }
}
