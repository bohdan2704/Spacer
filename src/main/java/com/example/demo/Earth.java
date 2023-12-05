package com.example.demo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Button;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

import static com.example.demo.Space.*;

public class Earth extends Application {

    private static final float WIDTH = 1000;
    private static final float HEIGHT = 600;
    private static Label astrounauts;
    private static Label issCoordinates;
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private final Sphere earth = new Sphere(150);


    @Override
    public void start(Stage primaryStage) {

        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(10000);
        camera.translateZProperty().set(-1000);

        Group world = new Group();
        world.getChildren().add(prepareEarth());

        Group root = new Group();
        root.getChildren().add(world);
        root.getChildren().add(prepareImageView());

        // Create a label
        issCoordinates = prepareLabel(-60, 150);
        astrounauts = prepareLabel(-320, 190);
        issCoordinates.setText(ISSTracker.updateISSPosition());
        astrounauts.setText(ISSTracker.displayAstronautInfo());
        root.getChildren().add(astrounauts);
        root.getChildren().add(issCoordinates);

        String audioFilePath = "C:\\Users\\Bohdan\\Desktop\\Space\\src\\main\\resources\\First-Moon-Landing-1969.mp3"; // Replace with the actual path to your audio file
        Media media = new Media(new File(audioFilePath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        Button playButton = new Button("Play");
        playButton.setOnAction(e -> mediaPlayer.play());
        Button stopButton = new Button("Stop");
        stopButton.setOnAction(e -> mediaPlayer.stop());

        playButton.setLayoutY(210);
        stopButton.setLayoutY(240);

        root.getChildren().addAll(playButton, stopButton);

        Sphere spaceStation = prepareISS();
        root.getChildren().add(spaceStation);

        Sphere moon = prepareMoon();
        root.getChildren().add(moon);

        Scene scene = new Scene(root, WIDTH, HEIGHT, true);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);

        initMouseControl(world, scene, primaryStage);

        primaryStage.setTitle("Earth");
        primaryStage.setScene(scene);

        rotate(earth, 24);
        prepareTimer();
        primaryStage.show();
    }

    private void prepareTimer() {
        // Create a Timeline that triggers every 5 seconds
        Duration duration = Duration.seconds(5);
        Timeline timeline = new Timeline(new KeyFrame(duration, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                issCoordinates.setText(ISSTracker.updateISSPosition());
            }
        }));

        // Set the timeline to repeat indefinitely
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Start the timeline
        timeline.play();

        Duration duration2 = Duration.hours(24);
        Timeline timeline2 = new Timeline(new KeyFrame(duration2, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                astrounauts.setText(ISSTracker.displayAstronautInfo());
            }
        }));

        // Set the timeline to repeat indefinitely
        timeline2.setCycleCount(Timeline.INDEFINITE);

        // Start the timeline
        timeline2.play();
    }

    private ImageView prepareImageView() {
        Image image = new Image(Earth.class.getResourceAsStream("/galaxy.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.getTransforms().add(new Translate(-image.getWidth() / 2, -image.getHeight() / 2, 800));
        return imageView;
    }

    private Label prepareLabel(int x, int y) {
        Label slider = new Label();
        slider.setText("Hello World In Space!");
        slider.setPrefWidth(700d);
        slider.setLayoutX(x);
        slider.setLayoutY(y);
        slider.setTranslateZ(5);
        slider.setStyle("-fx-base: black");
        return slider;
    }

    private Node prepareEarth() {
        PhongMaterial earthMaterial = new PhongMaterial();
        earthMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("/earth/earth-d.jpg")));
        earthMaterial.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/earth/earth-l.jpg")));
        earthMaterial.setSpecularMap(new Image(getClass().getResourceAsStream("/earth/earth-s.jpg")));
        earthMaterial.setBumpMap(new Image(getClass().getResourceAsStream("/earth/earth-n.jpg")));

        earth.setRotationAxis(Rotate.Y_AXIS);
        earth.setMaterial(earthMaterial);
        return earth;
    }

    private Sphere prepareISS() {
        Sphere spaceStation = new Sphere(2);
        PhongMaterial material = new PhongMaterial(Color.BLUE);
        spaceStation.setMaterial(material);

        spaceStation.setTranslateZ( -earth.getRadius() - spaceStation.getRadius() );

        Rotate rotate = new Rotate();
        rotate.setAxis(new Point3D(1, 1, 0));
        rotate.setPivotZ(-spaceStation.getTranslateZ());

        // Adding the transformation to rectangle
        spaceStation.getTransforms().addAll(rotate);

        // Create a Timeline for the rotation animation
        Timeline timeline = getTimeline(10, rotate);

        // Play the animation
        timeline.play();

        // Adding the transformation to rectangle
        return spaceStation;
    }

    private Sphere prepareMoon() {
        Sphere moon = new Sphere(earth.getRadius()*0.27);
        moon.setTranslateZ( -earth.getRadius() - moon.getRadius());

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(this.getClass().getResourceAsStream("/moonmap1k.jpg")));
        moon.setMaterial(material);

        Rotate rotate = new Rotate();
        rotate.setAxis(new Point3D(0, 1, 0));

        rotate.setPivotZ(-3 * moon.getTranslateZ());

        // Adding the transformation to rectangle
        moon.getTransforms().addAll(rotate);

        // Create a Timeline for the rotation animation
        Timeline timeline = getTimeline(27*88, rotate);

        // Play the animation
        timeline.play();

        // Adding the transformation to rectangle
        return moon;
    }

    private void initMouseControl(Group group, Scene scene, Stage stage) {
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });

        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY();
            group.translateZProperty().set(group.getTranslateZ() + delta);
        });
    }
}