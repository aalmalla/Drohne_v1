package com.example.drohne23.drone.menu;

import com.example.drohne23.drone.DroneClient;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.io.PrintWriter;
import java.util.HashMap;

public class Movement
{
    private static DroneClient droneClient;
    private Timeline movementTimeline;

    public Movement(DroneClient droneClient) {
        Movement.droneClient = droneClient;
    }

    public void startMovement(String direction) {
        if (droneClient != null) {
            if (movementTimeline != null) {
                movementTimeline.stop();
            }
            movementTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> startDroneMovement(direction)));
            movementTimeline.setCycleCount(Timeline.INDEFINITE);
            movementTimeline.play();
        }
    }

    public void stopMovement(String direction) {
        if (droneClient != null) {
            if (movementTimeline != null) {
                movementTimeline.stop();
            }
            stopDroneMovement(direction);
        }
    }

    void startDroneMovement(String direction) {
        DroneClient.startMovement(new HashMap<>(), direction, new PrintWriter(droneClient.getOut(), true));
    }

    void stopDroneMovement(String direction) {
        DroneClient.stopMovement(new HashMap<>(), direction, new PrintWriter(droneClient.getOut(), true));
    }

    public void handleKeyPress(KeyEvent e) {
        switch (e.getCode()) {
            case W -> startMovement("vorne");
            case S -> startMovement("hinten");
            case A -> startMovement("links");
            case D -> startMovement("rechts");
            case I -> startMovement("hoch");
            case K -> startMovement("unten");
            case L -> startMovement("rightrota");
            case J -> startMovement("leftrota");
        }
    }

    public void handleKeyRelease(KeyEvent e) {
        switch (e.getCode()) {
            case W -> stopMovement("vorne");
            case S -> stopMovement("hinten");
            case A -> stopMovement("links");
            case D -> stopMovement("rechts");
            case I -> stopMovement("hoch");
            case K -> stopMovement("unten");
            case L -> stopMovement("rightrota");
            case J -> stopMovement("leftrota");
        }
    }
    public static  void toggleGravity()
    {
        Menu.useGravity = !Menu.useGravity;
        Menu.droneProperties.getUseGravityProperty().set(Menu.useGravity);
        if (droneClient != null)
        {
            DroneClient.changeGravity(new HashMap<>(), Menu.useGravity, new PrintWriter(droneClient.getOut(), true));
        }
    }
}
