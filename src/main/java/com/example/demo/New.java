package com.example.demo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class New extends Application {
    public void start(Stage stage) {
        //Creating a rectangle
        Rectangle rect = new Rectangle(300, 100, 75, 75);
        rect.setFill(Color.BLUEVIOLET);
        rect.setStrokeWidth(5.0);
        rect.setStroke(Color.BROWN);
        //Setting the slider
        Slider slider = new Slider(0, 360, 0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(90);
        slider.setBlockIncrement(10);
        slider.setOrientation(Orientation.VERTICAL);
        slider.setLayoutX(2);
        slider.setLayoutY(195);
        //creating the rotation transformation
        Rotate rotate = new Rotate();
        //Setting pivot points for the rotation
        rotate.setPivotX(0);
        rotate.setPivotY(0);
        //Adding the transformation to rectangle
        rect.getTransforms().addAll(rotate);
        //Linking the transformation to the slider
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                //Setting the angle for the rotation
                rotate.setAngle((double) newValue);
            }
        });
        //Adding the transformation to the circle
        rect.getTransforms().add(rotate);
        //Creating the pane
        BorderPane pane = new BorderPane();
        pane.setRight(new VBox(new Label("Rotate"), slider));
        pane.setCenter(rect);
        //Preparing the scene
        Scene scene = new Scene(pane, 600, 300);
        stage.setTitle("Rotation Example");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}