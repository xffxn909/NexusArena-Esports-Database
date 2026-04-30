package com.nexus.arena.ui;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Floating toast notification system for NexusArena.
 * Provides styled info/success/warning/error notifications.
 */
public class ToastNotification {

    private final StackPane parentPane;
    private final VBox toastContainer;

    public ToastNotification(StackPane parentPane) {
        this.parentPane = parentPane;
        this.toastContainer = new VBox(8);
        this.toastContainer.setAlignment(Pos.TOP_RIGHT);
        this.toastContainer.setPadding(new Insets(20, 20, 20, 20));
        this.toastContainer.setPickOnBounds(false);
        this.toastContainer.setMouseTransparent(false);
        this.toastContainer.setMaxWidth(400);
        this.toastContainer.setMaxHeight(300);

        StackPane.setAlignment(toastContainer, Pos.TOP_RIGHT);
        if (!parentPane.getChildren().contains(toastContainer)) {
            parentPane.getChildren().add(toastContainer);
        }
    }

    public void showInfo(String message) {
        show("\u2139", message, "#00d4ff", "#0a2a3f");
    }

    public void showSuccess(String message) {
        show("\u2713", message, "#00ff88", "#0a3f1f");
    }

    public void showWarning(String message) {
        show("\u26A0", message, "#ff006e", "#3f0a2a");
    }

    public void showError(String message) {
        show("\u2717", message, "#ff2d55", "#3f0a15");
    }

    private void show(String icon, String message, String accentColor, String bgTint) {
        HBox toast = new HBox(12);
        toast.setAlignment(Pos.CENTER_LEFT);
        toast.setPadding(new Insets(14, 20, 14, 20));
        toast.setMaxWidth(380);
        toast.setStyle(
            "-fx-background-color: linear-gradient(to right, " + bgTint + ", rgba(13,14,26,0.95));" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: " + accentColor + ";" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 20, 0, 0, 4);"
        );

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-text-fill: " + accentColor + "; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label msgLabel = new Label(message);
        msgLabel.setWrapText(true);
        msgLabel.setMaxWidth(300);
        msgLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 13px;");

        toast.getChildren().addAll(iconLabel, msgLabel);
        toast.setOpacity(0);
        toast.setTranslateX(50);

        toastContainer.getChildren().add(toast);

        // Slide in
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), toast);
        slideIn.setFromX(50);
        slideIn.setToX(0);
        slideIn.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), toast);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ParallelTransition enter = new ParallelTransition(slideIn, fadeIn);
        enter.play();

        // Auto dismiss after 3 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(400), toast);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(ev -> toastContainer.getChildren().remove(toast));
            fadeOut.play();
        });
        pause.play();
    }
}
