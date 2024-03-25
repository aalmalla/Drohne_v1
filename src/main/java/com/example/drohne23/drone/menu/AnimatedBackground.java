package com.example.drohne23.drone.menu;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class AnimatedBackground extends Pane {
    private double mouseX;
    private double mouseY;

    private List<Arrow> arrows = new ArrayList<>();
    private Circle circle;

    public AnimatedBackground() {
        setPrefSize(1280, 720);

        for (int y = 0; y < 720 / 24; y++) {
            for (int x = 0; x < 1280 / 50; x++) {
                var a = new Arrow();
                a.setTranslateX(x * 50);
                a.setTranslateY(y * 24);

                arrows.add(a);
                getChildren().add(a);
            }
        }

        circle = new Circle(1);
        getChildren().add(circle);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();

        setOnMouseMoved(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
        });
    }

    private void onUpdate() {
        circle.setCenterX(mouseX);
        circle.setCenterY(mouseY);

        arrows.forEach(a -> {
            var vy = mouseY - a.getTranslateY();
            var vx = mouseX - a.getTranslateX();

            var angle = Math.toDegrees(Math.atan2(vy, vx));

            a.setRotate(angle);
        });
    }

    private static class Arrow extends Parent {
        Arrow() {
            var scale = 0.5;

            var lineTop = new Line(15 * scale, 5 * scale, 12.5 * scale, 2.5 * scale);
            var lineMid = new Line(0 * scale, 5 * scale, 15 * scale, 5 * scale);
            var lineBot = new Line(15 * scale, 5 * scale, 12.5 * scale, 7.5 * scale);

            getChildren().addAll(lineMid, lineTop, lineBot);
        }
    }
}
