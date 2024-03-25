package com.example.drohne23.drone.menu;

import com.example.drohne23.drone.DroneProperties;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Radar {
    private double initialX, initialY;
    private Menu menu;

    public Radar() {
        menu = new Menu();
    }

    public static void addPositionListener(DroneProperties droneProperties, Circle dronePoint, double initialX, double initialY)
    {
        droneProperties.getPositionProperty().addListener((observable, oldValue, newValue) ->
        {
            try
            {
                String[] positionParts = newValue.split(", ");
                double x = Double.parseDouble(positionParts[0].substring(10));
                double y = Double.parseDouble(positionParts[2]);
                double z = Double.parseDouble(positionParts[1]);
                dronePoint.setVisible(true);

                double clampedX = Math.max(-874, Math.min(874, x));
                double clampedY = Math.max(149.9, Math.min(1898, y));

                double scaledX = ((clampedX + 874) * 0.2287) + 30;
                double scaledY = (clampedY - 149.9) * 0.1717;
                Platform.runLater(() -> {
                    dronePoint.setCenterX(initialX + scaledX);
                    dronePoint.setCenterY(initialY + scaledY);
                });

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }
    public static @NotNull Circle createDronePoint(@NotNull Rectangle radar, double initialX, double initialY)
    {
        Circle dronePoint = new Circle(5, Color.RED);
        dronePoint.setVisible(false);
        Menu.initialX = initialX = (radar.getX() + radar.getWidth()) / 2;
        Menu.initialY = initialY = (radar.getY() + radar.getHeight()) / 2;
        dronePoint.setCenterX(initialX);
        dronePoint.setCenterY(initialY);
        return dronePoint;
    }
    public static @NotNull Rectangle createRadar(Pane root) throws IOException
    {
        Rectangle radar = new Rectangle(400, 300);
        try (InputStream is = Files.newInputStream(Paths.get("src/main/java/com/example/drohne23/drone/res/images/map1.jpg")))
        {
            Image image = new Image(is);
            radar.setFill(new ImagePattern(image));
        }
        radar.setStroke(Color.GRAY);
        radar.setStrokeWidth(2);
        radar.relocate((root.getPrefWidth() - radar.getWidth()) / 2, (root.getPrefHeight() - radar.getHeight()) / 2);
        return radar;
    }
}
