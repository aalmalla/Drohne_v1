package com.example.drohne23.drone;

import javafx.application.Platform;
import javafx.beans.property.*;
import lombok.Getter;


@Getter
public class DroneProperties
{
    private final StringProperty positionProperty = new SimpleStringProperty();
    private final StringProperty rotationProperty = new SimpleStringProperty();
    private final StringProperty scaleProperty = new SimpleStringProperty();
    private final DoubleProperty geschwindigkeitProperty = new SimpleDoubleProperty();
    private final DoubleProperty hoeheProperty = new SimpleDoubleProperty();
    private final DoubleProperty dragProperty = new SimpleDoubleProperty();
    private final BooleanProperty useGravityProperty = new SimpleBooleanProperty();
    private final DoubleProperty massProperty = new SimpleDoubleProperty();
    private final StringProperty timeProperty = new SimpleStringProperty();
    private final StringProperty aktionProperty = new SimpleStringProperty();
    private final DoubleProperty wertProperty = new SimpleDoubleProperty();
    private final BooleanProperty bewegungProperty = new SimpleBooleanProperty();
    private final BooleanProperty collisionProperty = new SimpleBooleanProperty();

    public void updateDroneViewProperties(DroneData data)
    {
        if (data != null) {
            Platform.runLater(() -> {
                positionProperty.set("Position: " + data.getPosition().getX() + ", "
                        + data.getPosition().getY() + ", "
                        + data.getPosition().getZ());
                rotationProperty.set("Rotation: " + data.getRotation().getX() + ", "
                        + data.getRotation().getY() + ", "
                        + data.getRotation().getZ() + ", "
                        + data.getRotation().getW());
                scaleProperty.set("Scale: " + data.getScale().getX() + ", "
                        + data.getScale().getY() + ", "
                        + data.getScale().getZ());
                geschwindigkeitProperty.set(data.getGeschwindigkeit());
                hoeheProperty.set(data.getHoehe());
                dragProperty.set(data.getDrag());
                useGravityProperty.set(data.isUseGravity());
                massProperty.set(data.getMass());
                timeProperty.set(data.getTime());
                aktionProperty.set(data.getAktion());
                wertProperty.set(data.getWert());
                bewegungProperty.set(data.isBewegung());
                collisionProperty.set(data.isCollision());
            });
        }
    }

}
