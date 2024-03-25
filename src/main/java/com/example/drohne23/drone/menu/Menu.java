package com.example.drohne23.drone.menu;

import com.example.drohne23.drone.DroneClient;
import com.example.drohne23.drone.DroneProperties;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;


public class Menu extends Application
{
    protected static DroneProperties droneProperties;
    DroneClient droneClient;
    protected static boolean useGravity;
    private Stage primaryStage;
    private MenuBox menu;
    private Stack<Scene> previousViews;
    private MenuItem itemBack;
    private AnimatedBackground animatedBackground;
    private boolean isConnected = false;
    public static double initialX;
    public static double initialY;
    private MenuItem menuItem;
    private Movement movement;
    private Labels labels;
    private @NotNull Parent createContent()
    {
        Pane root = new Pane();
        root.setPrefSize(860, 600);

        try (InputStream is = Files.newInputStream(Paths.get("src/main/java/com/example/drohne23/drone/res/images/menuHintergrund.jpg"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(880);
            img.setFitHeight(620);
            root.getChildren().add(img);
        } catch (IOException e) {
            System.err.println("Couldn't load image");
        }

        Title title = new Title("D R O N E X B 5");
        title.setTranslateX(75);
        title.setTranslateY(230);

        MenuItem itemSimulation = createMenuItem("Simulation", this::switchToSimulationView);
        MenuItem itemEinstellung = createMenuItem("Einstellung", this::switchToEinstellungView);
        MenuItem itemInfo = createMenuItem("Info", this::switchToInfoView);
        MenuItem itemExit = createMenuItem("Beenden", () -> System.exit(0));

        menu = new MenuBox(200, itemSimulation, itemEinstellung, itemInfo, itemExit);
        positionMenuItem(itemSimulation, 90, 300);
        positionMenuItem(itemEinstellung, 90, 340);
        positionMenuItem(itemInfo, 90, 380);
        positionMenuItem(itemExit, 90, 420);

        root.getChildren().addAll(title, menu);
        return root;
    }
    private @NotNull MenuItem createMenuItem(String name, Runnable action)
    {
        MenuItem menuItem = new MenuItem(name, 220, 40, this);
        menuItem.setOnMouseClicked(event -> action.run());
        return menuItem;
    }
    private void positionMenuItem(@NotNull MenuItem menuItem, double x, double y)
    {
        menuItem.setTranslateX(x);
        menuItem.setTranslateY(y);
    }
    @Override
    public void start(@NotNull Stage primaryStage)
    {

        this.primaryStage = primaryStage;
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("DRONEX B5");
        primaryStage.setScene(scene);
        try (InputStream logoStream = Files.newInputStream(Paths.get("src/main/java/com/example/drohne23/drone/res/images/Logo.jpg")))
        {
            Image logoImage = new Image(logoStream);
            primaryStage.getIcons().add(logoImage);
        }
        catch (IOException e)
        {
            System.out.println("Das Bild wurde nicht gefunden!");
        }
        movement = new Movement(droneClient);
        previousViews = new Stack<>();
        primaryStage.show();

        Thread droneClientThread = new Thread(this::startEnhancedDroneClient);
        droneClientThread.setDaemon(true);
        droneClientThread.start();

    }
    private void switchToSimulationView()
    {
        movement = new Movement(droneClient);

        Pane root = new Pane();
        root.setPrefSize(860, 600);
        labels = new Labels(droneProperties);
        labels.setupDroneProperties();
        labels.setupLabels(root);
        labels.setupDroneProperties();

        try {
            ImageView imageView = createBackgroundImage("src/main/java/com/example/drohne23/drone/res/images/SimHin.jpg");
            imageView.setFitWidth(880);
            imageView.setFitHeight(620);
            root.getChildren().add(imageView);
            menuItem = new MenuItem("Simulation", 220, 40, this);
            menuItem.setupMenuItems(root);
            Rectangle radar = Radar.createRadar(root);
            Circle dronePoint = Radar.createDronePoint(radar, initialX, initialY);
            addPositionListener(droneProperties, dronePoint, initialX, initialY);
            labels.setupLabels(root);
            Scene simulationScene = new Scene(root, 860, 600);
            simulationScene.setOnKeyPressed(event -> movement.handleKeyPress(event));
            simulationScene.setOnKeyReleased(event -> movement.handleKeyRelease(event));
            previousViews.push(primaryStage.getScene());
            primaryStage.setScene(simulationScene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Couldn't load image");
        }
    }
    private void switchToEinstellungView()
    {
        Pane root = new Pane();
        root.setPrefSize(860, 600);

        try (InputStream is = Files.newInputStream(Paths.get("src/main/java/com/example/drohne23/drone/res/images/Einstellung.jpg"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(880);
            img.setFitHeight(620);
            root.getChildren().add(img);
        }
        catch (IOException e)
        {
            System.err.println("Couldn't load image");
        }
        itemBack = new MenuItem("Back",200,30,this);
        itemBack.setOnMouseClicked(event -> switchToPreviousView());
        MenuBox subMenu = new MenuBox(30, itemBack);
        subMenu.setTranslateX(10);
        subMenu.setTranslateY(560);
        root.getChildren().addAll(animatedBackground, subMenu);
        Scene simulationScene = new Scene(root, 860, 600);
        previousViews.push(primaryStage.getScene());
        primaryStage.setScene(simulationScene);
        primaryStage.show();
    }
    protected void switchToInfoView()
    {
        Pane root = new Pane();
        root.setPrefSize(860, 600);
        /*
        try (InputStream is = Files.newInputStream(Paths.get("src/main/java/com/example/drohne23/drone/res/images/Einstellung.jpg"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(880);
            img.setFitHeight(620);
            root.getChildren().add(img);
        }
        catch (IOException e)
        {
            System.err.println("Couldn't load image");
        }*/

        itemBack = new MenuItem("Back",200,30,this);
        itemBack.setOnMouseClicked(event -> switchToPreviousView());
        MenuBox subMenu = new MenuBox(30, itemBack);
        subMenu.setTranslateX(10);
        subMenu.setTranslateY(560);
        root.getChildren().addAll(animatedBackground, subMenu);
        Scene simulationScene = new Scene(root, 860, 600);
        previousViews.push(primaryStage.getScene());
        primaryStage.setScene(simulationScene);
        primaryStage.show();
    }
    protected void switchToPreviousView()
    {
        if (!previousViews.isEmpty())
        {
            Scene previousScene = previousViews.pop();
            Stage currentStage = primaryStage;
            currentStage.setScene(previousScene);
            currentStage.show();
        }
    }
    public void startEnhancedDroneClient()
    {
        droneClient = new DroneClient();
        droneProperties = new DroneProperties(); // Neue Instanz von DroneProperties erstellen
        droneClient.setDroneDataListener(data ->
        {
            Platform.runLater(() ->
            {
                droneProperties.updateDroneViewProperties(data);
                isConnected = true;  // Set isConnected to true when the connection is established
            });
        });
        droneClient.start();
    }
    private @NotNull ImageView createBackgroundImage(String path) throws IOException
    {
        InputStream is = Files.newInputStream(Paths.get(path));
            Image image = new Image(is);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(860);
            imageView.setFitHeight(600);
            return imageView;
        }
    protected void addPositionListener(DroneProperties droneProperties, Circle dronePoint, double initialX, double initialY)
    {
        Radar.addPositionListener(droneProperties,dronePoint,initialX,initialY);
    }


}
