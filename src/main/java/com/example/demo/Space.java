package com.example.demo;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class Space extends Application {
    private double width;

    private static Sphere SUN;
    private static final int PLANET_K = 50;
    private static final int SPEED = 500;
    public static final int SUN_SIZE = 86400 / 50;
    public static final int MERCURY_SIZE = 2439 / 100;
    public static final int VENUS_SIZE = 12104 / 100;
    public static final int EARTH_SIZE = 6371 / 100;
    public static final int MARS_SIZE = 3389 / 100;
    public static final int JUPITER_SIZE = 69911 / 100;
    public static final int SATURN_SIZE = 58232 / 100;
    public static final int URANUS_SIZE = 25362 / 100;
    public static final int NEPTUNE_SIZE = 24622 / 100;

    public static final int MERCURY_SUN_DISTANCE = 77_900 / PLANET_K;
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

    public static final int NEPTUNE_SPIN_IN_DAYS = 60190;
    public static final int URANUS_SPIN_IN_DAYS = 30687;
    public static final int SATURN_SPIN_IN_DAYS = 10759;
    public static final int JUPITER_SPIN_IN_DAYS = 4333;
    public static final int MARS_SPIN_IN_DAYS = 687;
    public static final int EARTH_SPIN_IN_DAYS = 365;
    public static final int VENUS_SPIN_IN_DAYS = 225;
    public static final int MERCURY_SPIN_IN_DAYS = 88;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Camera camera = new PerspectiveCamera();
        camera.setTranslateZ( -10000);

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case W:
                    camera.setTranslateZ(camera.getTranslateZ() + SPEED);
                    System.out.print("Z: ");
                    System.out.println(camera.getTranslateZ());
                    break;
                case S:
                    camera.setTranslateZ(camera.getTranslateZ() - SPEED);
                    System.out.print("Z: ");
                    System.out.println(camera.getTranslateZ());
                    break;
                case A:
                    camera.setTranslateX(camera.getTranslateX() - SPEED);
                    System.out.print("X: ");
                    System.out.println(camera.getTranslateX());
                    break;
                case D:
                    camera.setTranslateX(camera.getTranslateX() + SPEED);
                    System.out.print("X: ");
                    System.out.println(camera.getTranslateX());
                    break;
                case R:
                    camera.setTranslateY(camera.getTranslateY() - SPEED);
                    System.out.print("Y: ");
                    System.out.println(camera.getTranslateY());
                    break;
                case F:
                    camera.setTranslateY(camera.getTranslateY() + SPEED);
                    System.out.print("Y: ");
                    System.out.println(camera.getTranslateY());
                    break;
                case P:
                    camera.setTranslateZ(camera.getTranslateZ() - 0.01);
                    break;
            }
        });
        Group mainNode = createMainNode(primaryStage);
        Scene scene = createScene(mainNode);
        scene.setCamera(camera);

        camera.setTranslateX(0);
        camera.setTranslateY(0);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Scene createScene(Group mainNode) {
        ImagePattern background = new ImagePattern(getImageFromResource("/sky.png"));
        return new Scene(mainNode, background);
    }

    private Group createMainNode(Stage primaryStage) {
        initStage(primaryStage);

        SUN = createPlanet(SUN_SIZE, 0, "/sunmap.jpg", 0, 0, primaryStage);
        SUN.setTranslateZ(5000);
        rotate(SUN, JUPITER_SPIN_IN_HOURS);

        Sphere mercury = createPlanet(MERCURY_SIZE, MERCURY_SUN_DISTANCE, "/mercurymap.jpg", MERCURY_SPIN_IN_HOURS, MERCURY_SPIN_IN_DAYS, primaryStage);
        Sphere venus = createPlanet(VENUS_SIZE, VENUS_SUN_DISTANCE, "/venusmap.jpg", VENUS_SPIN_IN_HOURS, VENUS_SPIN_IN_DAYS, primaryStage);
        Sphere earth = createPlanet(EARTH_SIZE, EARTH_SUN_DISTANCE, "/earthmap1k.jpg", EARTH_SPIN_IN_HOURS, EARTH_SPIN_IN_DAYS, primaryStage);
        Sphere mars = createPlanet(MARS_SIZE, MARS_SUN_DISTANCE, "/mars_1k_color.jpg", MARS_SPIN_IN_HOURS, MARS_SPIN_IN_DAYS, primaryStage);
        Sphere jupiter = createPlanet(JUPITER_SIZE, JUPITER_SUN_DISTANCE, "/jupiter2_1k.jpg", JUPITER_SPIN_IN_HOURS, JUPITER_SPIN_IN_DAYS, primaryStage);
        Sphere saturn = createPlanet(SATURN_SIZE, SATURN_SUN_DISTANCE, "/saturnmap.jpg", SATURN_SPIN_IN_HOURS, SATURN_SPIN_IN_DAYS, primaryStage);
        Sphere uranus = createPlanet(URANUS_SIZE, URANUS_SUN_DISTANCE, "/uranusmap.jpg", URANUS_SPIN_IN_HOURS, URANUS_SPIN_IN_DAYS, primaryStage);
        Sphere neptune = createPlanet(NEPTUNE_SIZE, NEPTUNE_SUN_DISTANCE, "/neptunemap.jpg", NEPTUNE_SPIN_IN_HOURS, NEPTUNE_SPIN_IN_DAYS, primaryStage);

        return new Group(neptune, uranus, saturn, jupiter, mars, earth, venus, mercury, SUN);
    }

    private Image getImageFromResource(String resourcePath) {
        return new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(resourcePath)));
    }

    private void rotateAroundSun(Sphere planet, int spinInDaysAroundSun) {
        Rotate rotate = new Rotate();
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setPivotZ(planet.getTranslateZ()*-2);

        // Adding the transformation to rectangle
        planet.getTransforms().addAll(rotate);

        // Create a Timeline for the rotation animation
        Timeline timeline = getTimeline(spinInDaysAroundSun, rotate);

        // Play the animation
        timeline.play();
    }

    public static Timeline getTimeline(int spinInDaysAroundSun, Rotate rotate) {
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis((double) spinInDaysAroundSun / MERCURY_SPIN_IN_DAYS),
                        event -> {
                            // Increment the rotation angle
                            rotate.setAngle(rotate.getAngle() + 0.05);
                        }
                )
        );

        // Set the cycle count to indefinite for continuous rotation
        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }


    private Sphere createPlanet(double size, double distance, String texturePath, int spinInHours, int spinAroundSunInDays, Stage primaryStage) {
        Sphere planet = new Sphere(relativeSize(size));
        planet.setTranslateZ(distance);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(getImageFromResource(texturePath));
        planet.setMaterial(material);

//        rotate(planet, spinInHours);
        rotateAroundSun(planet, spinAroundSunInDays);
        return planet;
    }

    private void initStage(Stage primaryStage) {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        this.width = primaryStage.getWidth();
    }

    private double relativeSize(double value) {
        return value * (1440d / width);
    }

    public static void rotate(Sphere planet, int totalSpinInHours) {
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
}
