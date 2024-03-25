package com.example.drohne23.drone.menu;

import com.example.drohne23.drone.DroneProperties;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Labels implements Strings{
    Label labelBattary;
    Label labelPosition;
    Label labelHohe;
    Label labelGeschwindigkeit;
    private DroneProperties droneProperties;

    public Labels(DroneProperties droneProperties)
    {
        this.droneProperties = droneProperties;
    }


    public void setupDroneProperties() {

try{
    droneProperties.getPositionProperty().addListener((observable, oldValue, newValue) -> {
        String[] positionParts = newValue.split(REGEX);
        double x = Double.parseDouble(positionParts[0].substring(10));
        double y = Double.parseDouble(positionParts[2]);
        double z = Double.parseDouble(positionParts[1]);
        String formattedPosition = String.format("Position: %.1f| %.1f| %.1f", x, y, z);
        labelPosition.setText(formattedPosition);
    });
    droneProperties.getHoeheProperty().addListener((observable, oldValue, newValue) -> {
        String hoeheProperty = String.format("Hohe: %.1f", newValue);
        labelHohe.setText(hoeheProperty);
    });
    droneProperties.getGeschwindigkeitProperty().addListener((observable, oldValue, newValue) -> {
        String geschwindigkeit = String.format("Geschwindigkeit: %.1f", newValue);
        labelGeschwindigkeit.setText(geschwindigkeit);
    });
}catch (Exception E)
{
    System.err.println("Kol 5ara");
}

    }
    public void setupLabels(Pane root)
    {
        labelBattary = new Label();
        labelBattary.setTranslateX(0);
        labelBattary.setTranslateY(0);
        labelBattary.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        labelBattary.setText("Batterie: 100%");
        root.getChildren().add(labelBattary);

        labelPosition = new Label();
        labelPosition.setTranslateX(0);
        labelPosition.setTranslateY(15);
        labelPosition.setText("Position");
        labelPosition.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        root.getChildren().add(labelPosition);

        labelHohe = new Label();
        labelHohe.setTranslateX(0);
        labelHohe.setTranslateY(30);
        labelHohe.setText("Hohe");
        labelHohe.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        root.getChildren().add(labelHohe);

        labelGeschwindigkeit = new Label();
        labelGeschwindigkeit.setTranslateX(0);
        labelGeschwindigkeit.setTranslateY(45);
        labelGeschwindigkeit.setText("Geschwindigkeit");
        labelGeschwindigkeit.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        root.getChildren().add(labelGeschwindigkeit);
    }
}
