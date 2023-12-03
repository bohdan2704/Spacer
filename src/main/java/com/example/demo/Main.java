package com.example.demo;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class Main extends Application {
    private double width;
    private static final int PLANET_K = 50;
    private static final int SPEED = 150;
    public static final int SUN_SIZE = 86400 / 100;
    public static final int MERCURY_SIZE = 2439 / 100;
    public static final int VENUS_SIZE = 12104 / 100;
    public static final int EARTH_SIZE = 6371 / 100;
    public static final int MARS_SIZE = 3389 / 100;
    public static final int JUPITER_SIZE = 69911 / 100;
    public static final int SATURN_SIZE = 58232 / 100;
    public static final int URANUS_SIZE = 25362 / 100;
    public static final int NEPTUNE_SIZE = 24622 / 100;

    public static final int MERCURY_SUN_DISTANCE = 57_900 / PLANET_K;
    public static final int VENUS_SUN_DISTANCE = 108_200 / PLANET_K;
    public static final int EARTH_SUN_DISTANCE = 149_600 / PLANET_K;
    public static final int MARS_SUN_DISTANCE = 375_350 / PLANET_K;
    public static final int JUPITER_SUN_DISTANCE = 778_350 / PLANET_K;
    public static final int SATURN_SUN_DISTANCE = 1_456_600 / PLANET_K;
    public static final int URANUS_SUN_DISTANCE = 2_933_700 / PLANET_K;
    public static final int NEPTUNE_SUN_DISTANCE = 4_433_700 / PLANET_K;

    public static final int NEPTUNE_SPIN_IN_HOURS = 16;
    public static final int URANUS_SPIN_IN_HOURS = 17;
    public static final int SATURN_SPIN_IN_HOURS = 10;
    public static final int JUPITER_SPIN_IN_HOURS = 10;
    public static final int MARS_SPIN_IN_HOURS = 24;
    public static final int EARTH_SPIN_IN_HOURS = 24;
    public static final int VENUS_SPIN_IN_HOURS = 24 * 243;
    public static final int MERCURY_SPIN_IN_HOURS = 58 * 243;

    public void start(Stage primaryStage) {



        PerspectiveCamera camera = new PerspectiveCamera();
        camera.setTranslateZ(MARS_SUN_DISTANCE);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        //        camera.setRotationAxis(Rotate.Y_AXIS);
        //        camera.setRotate(180);

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case W:
                    camera.setTranslateZ(camera.getTranslateZ() + SPEED);
                    break;
                case S:
                    camera.setTranslateZ(camera.getTranslateZ() - SPEED);
                    break;
                case A:
                    camera.setTranslateX(camera.getTranslateX() - SPEED);
                    break;
                case D:
                    camera.setTranslateX(camera.getTranslateX() + SPEED);
                    break;
            }
        });
        Group mainNode = fillWithPlanets(primaryStage);
        addAxes(mainNode);

        Scene scene = createScene(mainNode);
        scene.setCamera(camera);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Scene createScene(Group mainNode) {
        ImagePattern background = new ImagePattern(getImageFromResource("/sky.png"));
        return new Scene(mainNode, background);
    }

    private Group fillWithPlanets(Stage primaryStage) {

        Sphere sun = createPlanet(SUN_SIZE, 0, "/sunmap.jpg", 0, primaryStage);
        Sphere mercury = createPlanet(MERCURY_SIZE, MERCURY_SUN_DISTANCE, "/mercurymap.jpg", MERCURY_SPIN_IN_HOURS, primaryStage);
        Sphere venus = createPlanet(VENUS_SIZE, VENUS_SUN_DISTANCE, "/venusmap.jpg", VENUS_SPIN_IN_HOURS, primaryStage);
        Sphere earth = createPlanet(EARTH_SIZE, EARTH_SUN_DISTANCE, "/earthmap1k.jpg", EARTH_SPIN_IN_HOURS, primaryStage);
        Sphere mars = createPlanet(MARS_SIZE, MARS_SUN_DISTANCE, "/mars_1k_color.jpg", MARS_SPIN_IN_HOURS, primaryStage);
        Sphere jupiter = createPlanet(JUPITER_SIZE, JUPITER_SUN_DISTANCE, "/jupiter2_1k.jpg", JUPITER_SPIN_IN_HOURS, primaryStage);
        Sphere saturn = createPlanet(SATURN_SIZE, SATURN_SUN_DISTANCE, "/saturnmap.jpg", SATURN_SPIN_IN_HOURS, primaryStage);
        Sphere uranus = createPlanet(URANUS_SIZE, URANUS_SUN_DISTANCE, "/uranusmap.jpg", URANUS_SPIN_IN_HOURS, primaryStage);
        Sphere neptune = createPlanet(NEPTUNE_SIZE, NEPTUNE_SUN_DISTANCE, "/neptunemap.jpg", NEPTUNE_SPIN_IN_HOURS, primaryStage);

        return new Group(neptune, uranus, saturn, jupiter, mars, earth, venus, mercury, sun);
    }

    private Image getImageFromResource(String resourcePath) {
        return new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(resourcePath)));
    }

//    private void rotateAroundSun(Sphere planet) {
//        earthOrbitAngle += earthOrbitSpeed; //advance angle in degrees
//        var orbitAngleInRadians = earthOrbitAngle * Math.PI / 180; //convert to radians
//
//        //update position of earth...
//        earth.position.x = Math.cos(orbitAngleInRadians) * earthOrbitRadius;
//        earth.position.z = Math.sin(orbitAngleInRadians) * earthOrbitRadius;
//    }

    private void addAxes(Group group) {
        double axisLength =5000;

        // X-axis (Red)
        Line xAxis = new Line(0, axisLength, 0, 0);
        xAxis.setStroke(Color.RED);
        group.getChildren().add(xAxis);

        // Y-axis (Green)
        Line yAxis = new Line(0, 0, axisLength, 0);
        yAxis.setStroke(Color.GREEN);
        group.getChildren().add(yAxis);

        // Z-axis (Blue)
        Line zAxis = new Line(0, 0, 0, axisLength); // Negative Z-axis to face into the screen
        zAxis.setStroke(Color.BLUE);
        group.getChildren().add(zAxis);
    }



    private Sphere createPlanet(double size, double distance, String texturePath, int spinInHours, Stage primaryStage) {
        Sphere planet = new Sphere(relativeSize(size));
//        planet.setLayoutX(primaryStage.getWidth() / 2);
//        planet.setLayoutY(primaryStage.getHeight() / 2);
        planet.setTranslateZ(distance);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(getImageFromResource(texturePath));
        planet.setMaterial(material);

        rotate(planet, spinInHours);
//        rotateAroundSun();
//        createPlanetOrbit(planet, spinInHours*10);
        return planet;
    }

    private RotateTransition createPlanetOrbit(Sphere planet, int durationInSeconds) {
        RotateTransition rotate = new RotateTransition(Duration.seconds(durationInSeconds), planet);
        rotate.setByAngle(360); // One full orbit
        rotate.setCycleCount(RotateTransition.INDEFINITE); // Repeat indefinitely
        return rotate;
    }

//    private void initStage(Stage primaryStage) {
//        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
//        primaryStage.setX(bounds.getMinX());
//        primaryStage.setY(bounds.getMinY());
//        primaryStage.setWidth(bounds.getWidth());
//        primaryStage.setHeight(bounds.getHeight());
//
//        this.width = primaryStage.getWidth();
//    }

    private double relativeSize(double value) {
        return value * (1440d / width);
    }

    private void rotate(Sphere planet, int totalSpinInHours) {
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(planet);
        rotateTransition.setDuration(Duration.seconds(totalSpinInHours));
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.play();
    }

    public static void main(String[] args) {
        launch();
    }

}