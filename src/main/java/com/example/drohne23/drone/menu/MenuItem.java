package com.example.drohne23.drone.menu;

import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;


import java.io.IOException;

public class MenuItem extends StackPane
{
    private final Menu menu;
    private final Movement movement;

    public MenuItem(String name, int width, int height, Menu menu)
    {
        this.menu = menu;
        this.movement = new Movement(menu.droneClient);

        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.DARKVIOLET),
                new Stop(0.1, Color.BLACK),
                new Stop(0.9, Color.BLACK),
                new Stop(1, Color.DARKVIOLET));

        Rectangle background = new Rectangle(width, height);
        background.setOpacity(0.3);

        Text text = new Text(name);
        text.setFill(Color.DARKGREY);
        text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 30));

        setAlignment(Pos.CENTER);
        getChildren().addAll(background, text);

        setOnMouseEntered(event ->
        {
            background.setFill(gradient);
            text.setFill(Color.WHITE);
            //sound.playSound();
        });

        setOnMouseExited(event ->
        {
            background.setFill(Color.BLACK);
            text.setFill(Color.DARKGREY);
        });

        setOnMousePressed(event ->
        {
            background.setFill(Color.DARKVIOLET);
            //sound.playSound();
        });

        setOnMouseReleased(event ->
        {
            background.setFill(gradient);
        });
    }

    protected void setupMenuItems(Pane root) throws IOException
    {
        MenuItem itemForward = createMenuItem("W", 30, 30, "vorne");
        MenuItem itemBackward = createMenuItem("S", 30, 30, "hinten");
        MenuItem itemLeft = createMenuItem("A", 30, 30, "links");
        MenuItem itemRight = createMenuItem("D", 30, 30, "rechts");
        MenuItem itemUp = createMenuItem("I", 30, 30, "hoch");
        MenuItem itemDown = createMenuItem("K", 30, 30, "unten");
        MenuItem itemLeftRotation = createMenuItem("J", 30, 30, "leftRota");
        MenuItem itemRightRotation = createMenuItem("L", 30, 30, "rightRota");
        MenuItem itemBack = createMenuItem("Back", 100, 30, null);
        MenuItem itemHome = createMenuItem("Home", 100, 30, null);
        MenuItem gravityToggleButton = createMenuItem("Gravity", 100, 30, null);


        setupMousePressAndReleaseEvents(itemForward, "vorne");
        setupMousePressAndReleaseEvents(itemBackward, "hinten");
        setupMousePressAndReleaseEvents(itemLeft, "links");
        setupMousePressAndReleaseEvents(itemRight, "rechts");
        setupMousePressAndReleaseEvents(itemUp, "hoch");
        setupMousePressAndReleaseEvents(itemDown, "unten");
        setupMousePressAndReleaseEvents(itemLeftRotation, "leftRota");
        setupMousePressAndReleaseEvents(itemRightRotation, "rightRota");
        gravityToggleButton.setOnMousePressed(event -> Movement.toggleGravity());

        itemBack.setOnMouseClicked(event -> menu.switchToPreviousView());

        // Add position listener, radar, and dronePoint
        Rectangle radar = Radar.createRadar(root);
        Circle dronePoint = Radar.createDronePoint(radar, Menu.initialX, Menu.initialY);
        menu.addPositionListener(menu.droneProperties, dronePoint, Menu.initialX, Menu.initialY);

        positionMenuItems(gravityToggleButton,itemForward, itemBackward, itemLeft, itemRight, itemUp, itemDown, itemLeftRotation,
                itemRightRotation, itemBack, itemHome);


        // Add menu items to the root pane
        root.getChildren().addAll(gravityToggleButton,radar, dronePoint, itemForward, itemBackward, itemLeft, itemRight, itemUp,
                itemDown, itemLeftRotation, itemRightRotation, itemBack, itemHome);

    }

    private @NotNull MenuItem createMenuItem(String name, int width, int height, String movementDirection)
    {
        MenuItem menuItem = new MenuItem(name, width, height, menu);
        menuItem.setOnMousePressed(e -> movement.startDroneMovement(movementDirection));
        menuItem.setOnMouseReleased(e -> movement.stopDroneMovement(movementDirection));
        return menuItem;
    }
    protected void positionMenuItems(MenuItem @NotNull ... items)
    {
        double[] xPositions = { 10,100, 100, 65, 135, 735, 735, 700, 770, 10, 290 };
        double[] yPositions = { 520,340, 375, 375, 375, 340, 375, 375, 375, 560, 530 };

        for (int i = 0; i < items.length; i++) {
            MenuItem item = items[i];
            item.setTranslateX(xPositions[i]);
            item.setTranslateY(yPositions[i]);
        }
    }
    private void setupMousePressAndReleaseEvents(@NotNull MenuItem menuItem, String movementDirection) {
        menuItem.setOnMousePressed((MouseEvent e) -> movement.startDroneMovement(movementDirection));
        menuItem.setOnMouseReleased((MouseEvent e) -> movement.stopDroneMovement(movementDirection));
    }

}
