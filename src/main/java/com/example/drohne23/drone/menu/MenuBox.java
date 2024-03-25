package com.example.drohne23.drone.menu;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

class MenuBox extends Pane
{
    public MenuBox(int x, MenuItem... items)
    {
        getChildren().add(createSeparator(x));

        for (MenuItem item : items)
        {
            getChildren().addAll(item, createSeparator(x));
        }
    }
    private Line createSeparator(int x)
    {
        Line sep = new Line();
        sep.setEndX(x);
        sep.setStroke(Color.DARKGREY);
        return sep;
    }
}