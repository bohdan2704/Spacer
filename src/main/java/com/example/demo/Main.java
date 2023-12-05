package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// THREE PRESES WITH -R-
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Spacer");

        // Button to open Earth Window
        Button button1 = new Button("Earth Model");
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
        Earth earth = new Earth();
        earth.start(stage);
    }

    // Method to open a new window
    private void openSpaceWindow(Stage stage) throws Exception {
        Space space = new Space();
        space.start(stage);
    }

}