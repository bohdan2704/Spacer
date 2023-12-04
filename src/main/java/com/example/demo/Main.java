package com.example.demo;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;
import java.util.Random;


// THREE PRESES WITH -R-
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Spacer");

        // Button to open Earth Window
        Button button1 = new Button("Earth simulator");
        button1.setOnAction(e -> {
            try {
                openEarthWindow(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Button to open Space Window
        Button button2 = new Button("Experimental Space Model");
        button2.setOnAction(e -> {
            try {
                openSpaceWindow(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Layout for the main window
        VBox layout = new VBox(10);
        layout.getChildren().addAll(button1, button2);
        layout.setAlignment(Pos.CENTER); // Center the buttons vertically

        // Set up the main scene
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void openEarthWindow(Stage stage) throws Exception {
        Earh earh = new Earh();
        earh.start(stage);
    }

    // Method to open a new window
    private void openSpaceWindow(Stage stage) throws Exception {
        Space space = new Space();
        space.start(stage);
    }

}