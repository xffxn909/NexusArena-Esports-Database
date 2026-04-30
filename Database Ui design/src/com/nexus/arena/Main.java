package com.nexus.arena;

import com.nexus.arena.dao.DatabaseConnection;
import com.nexus.arena.ui.NexusArenaUI;
import com.nexus.arena.ui.ParticleSplashScreen;
import com.nexus.arena.ui.RoleSelectScreen;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application {

    private StackPane sceneRoot;
    private Stage primaryStage;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("NexusArena — Competitive Gaming Platform");

        // Scene root
        sceneRoot = new StackPane();
        sceneRoot.setStyle("-fx-background-color: #080810;");

        // CUSTOM TITLE BAR

        HBox titleBar = new HBox(12);
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setPadding(new Insets(8, 20, 8, 20));
        titleBar.setStyle(
            "-fx-background-color: #080810;" +
            "-fx-border-color: rgba(0,212,255,0.1);" +
            "-fx-border-width: 0 0 1 0;"
        );

        Label titleBrand = new Label("NEXUS");
        titleBrand.setStyle(
            "-fx-text-fill: #00d4ff; -fx-font-weight: 900;" +
            "-fx-letter-spacing: 4px; -fx-font-size: 11px;"
        );
        Label titleSuffix = new Label("ARENA");
        titleSuffix.setStyle(
            "-fx-text-fill: #4a4a6a; -fx-font-weight: 900;" +
            "-fx-letter-spacing: 4px; -fx-font-size: 11px;"
        );

        Region titleSpacer = new Region();
        HBox.setHgrow(titleSpacer, Priority.ALWAYS);

        // Minimize button
        Button minBtn = new Button("─");
        minBtn.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: #8b8ba3;" +
            "-fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 14px;"
        );
        minBtn.setOnMouseEntered(e -> minBtn.setStyle(
            "-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white;" +
            "-fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 14px;"
        ));
        minBtn.setOnMouseExited(e -> minBtn.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: #8b8ba3;" +
            "-fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 14px;"
        ));
        minBtn.setOnAction(e -> primaryStage.setIconified(true));

        // Close button
        Button closeBtn = new Button("\u2715");
        closeBtn.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: #8b8ba3;" +
            "-fx-font-weight: bold; -fx-cursor: hand;"
        );
        closeBtn.setOnMouseEntered(e -> closeBtn.setStyle(
            "-fx-background-color: #ff2d55; -fx-text-fill: white;" +
            "-fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 6;"
        ));
        closeBtn.setOnMouseExited(e -> closeBtn.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: #8b8ba3;" +
            "-fx-font-weight: bold; -fx-cursor: hand;"
        ));
        closeBtn.setOnAction(e -> {
            DatabaseConnection.closeConnection();
            Platform.exit();
        });

        titleBar.getChildren().addAll(titleBrand, titleSuffix, titleSpacer, minBtn, closeBtn);

        // Draggable title bar
        titleBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        titleBar.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        // ═══════════════════════════════════════════════════════════════
        // PARTICLE SPLASH SCREEN
        // ═══════════════════════════════════════════════════════════════

        StackPane splashRoot = new StackPane();
        splashRoot.setStyle("-fx-background-color: #080810;");
        Canvas particleCanvas = new Canvas(1920, 1080);
        particleCanvas.widthProperty().bind(splashRoot.widthProperty());
        particleCanvas.heightProperty().bind(splashRoot.heightProperty());

        ParticleSplashScreen splash = new ParticleSplashScreen(particleCanvas);
        splashRoot.getChildren().add(particleCanvas);

        // Mouse interaction
        particleCanvas.setOnMouseMoved(e -> splash.setMouse(e.getX(), e.getY()));
        particleCanvas.setOnMouseExited(e -> splash.clearMouse());

        sceneRoot.getChildren().add(splashRoot);
        splash.start();

        // Form text after a brief delay
        javafx.animation.Timeline textDelay = new javafx.animation.Timeline(
            new javafx.animation.KeyFrame(Duration.millis(100), e -> splash.setTargetText("NEXUS ARENA", 2.0))
        );
        textDelay.play();

        // Click to enter → Role Select
        splashRoot.setOnMouseClicked(e -> {
            splash.explode();
            javafx.animation.PauseTransition wait = new javafx.animation.PauseTransition(Duration.millis(400));
            wait.setOnFinished(ev -> showRoleSelect());
            wait.play();
        });

        // ═══════════════════════════════════════════════════════════════
        // WINDOW ASSEMBLY
        // ═══════════════════════════════════════════════════════════════

        VBox windowRoot = new VBox();
        windowRoot.setStyle("-fx-background-color: #080810;");
        VBox.setVgrow(sceneRoot, Priority.ALWAYS);
        windowRoot.getChildren().addAll(titleBar, sceneRoot);

        Scene scene = new Scene(windowRoot, 1366, 768);
        try {
            java.net.URL cssUrl = getClass().getResource("/styles/nexus-theme.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                java.io.File cssFile = new java.io.File("resources/styles/nexus-theme.css");
                if (cssFile.exists()) {
                    scene.getStylesheets().add(cssFile.toURI().toURL().toExternalForm());
                } else {
                    System.err.println("Could not find stylesheet: nexus-theme.css");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showRoleSelect() {
        StackPane roleScreen = RoleSelectScreen.create(selectedRole -> {
            showMainUI(selectedRole);
        });
        crossFade(roleScreen);
    }

    private void showMainUI(String role) {
        NexusArenaUI ui = new NexusArenaUI(role, v -> {
            // Logout callback → return to role select
            showRoleSelect();
        });
        crossFade(ui.getRoot());
    }

    private void crossFade(javafx.scene.Node newContent) {
        if (!sceneRoot.getChildren().isEmpty()) {
            javafx.scene.Node old = sceneRoot.getChildren().get(0);
            FadeTransition fadeOut = new FadeTransition(Duration.millis(250), old);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> {
                sceneRoot.getChildren().setAll(newContent);
                newContent.setOpacity(0);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(700), newContent);
                fadeIn.setToValue(1);
                fadeIn.play();
            });
            fadeOut.play();
        } else {
            sceneRoot.getChildren().setAll(newContent);
        }
    }

    @Override
    public void stop() {
        DatabaseConnection.closeConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
