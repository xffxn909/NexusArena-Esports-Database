package com.nexus.arena.ui;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.function.Consumer;

/**
 * Role selection screen — Player, Admin, Community.
 * Clean turquoise-only theme. Focus highlight appears ONLY on card border.
 */
public class RoleSelectScreen {

    private static final String CYAN = "#00d4ff";
    private static final String MUTED = "#8b8ba3";
    private static final String BG = "#080810";

    public static StackPane create(Consumer<String> onRoleSelected) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: " + BG + ";");

        VBox layout = new VBox(50);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(60));

        // Title
        Label title = new Label("NEXUS ARENA");
        title.setFont(Font.font("Segoe UI", FontWeight.BLACK, 48));
        title.setStyle("-fx-text-fill: " + CYAN + ";");

        Label subtitle = new Label("SELECT YOUR ROLE");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        subtitle.setStyle("-fx-text-fill: " + MUTED + ";");

        // Role cards
        HBox cardsBox = new HBox(40);
        cardsBox.setAlignment(Pos.CENTER);

        VBox playerCard = createRoleCard(
            "\uD83C\uDFAE", "PLAYER",
            "Browse leaderboards, check rosters,\nand track tournament progress."
        );
        VBox adminCard = createRoleCard(
            "\u26A1", "ADMIN",
            "Full CRUD access to all 10 tables.\nSQL console and system management."
        );
        VBox communityCard = createRoleCard(
            "\uD83D\uDC65", "COMMUNITY",
            "Browse games, teams, matches,\nand sponsor partnerships."
        );

        playerCard.setOnMouseClicked(e -> onRoleSelected.accept("Player"));
        adminCard.setOnMouseClicked(e -> onRoleSelected.accept("Admin"));
        communityCard.setOnMouseClicked(e -> onRoleSelected.accept("Community"));

        cardsBox.getChildren().addAll(playerCard, adminCard, communityCard);

        // Footer
        Label footer = new Label("v3.0  \u00B7  NEXUSARENA DATABASE PROJECT  \u00B7  3NF NORMALIZED");
        footer.setStyle("-fx-text-fill: #4a4a6a; -fx-font-size: 11px;");

        layout.getChildren().addAll(title, subtitle, cardsBox, footer);
        root.getChildren().add(layout);

        // Entrance animation
        layout.setOpacity(0);
        layout.setTranslateY(30);
        FadeTransition fade = new FadeTransition(Duration.millis(800), layout);
        fade.setFromValue(0);
        fade.setToValue(1);
        TranslateTransition slide = new TranslateTransition(Duration.millis(800), layout);
        slide.setFromY(30);
        slide.setToY(0);
        slide.setInterpolator(Interpolator.EASE_OUT);
        new ParallelTransition(fade, slide).play();

        return root;
    }

    private static VBox createRoleCard(String icon, String title, String description) {
        VBox card = new VBox(16);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(40, 36, 40, 36));
        card.setPrefWidth(260);
        card.setPrefHeight(300);
        card.setCursor(Cursor.HAND);
        card.setFocusTraversable(false); // Prevents JavaFX default focus ring

        // Base: subtle glass card with thin dim border
        String baseStyle =
            "-fx-background-color: rgba(255,255,255,0.04);" +
            "-fx-background-radius: 20;" +
            "-fx-border-color: rgba(255,255,255,0.08);" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 20;";

        // Hover: ONLY the border changes to cyan — nothing else
        String hoverStyle =
            "-fx-background-color: rgba(255,255,255,0.06);" +
            "-fx-background-radius: 20;" +
            "-fx-border-color: " + CYAN + ";" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 20;";

        card.setStyle(baseStyle);

        card.setOnMouseEntered(e -> {
            card.setStyle(hoverStyle);
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), card);
            scale.setToX(1.03);
            scale.setToY(1.03);
            scale.setInterpolator(Interpolator.EASE_OUT);
            scale.play();
        });

        card.setOnMouseExited(e -> {
            card.setStyle(baseStyle);
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), card);
            scale.setToX(1.0);
            scale.setToY(1.0);
            scale.setInterpolator(Interpolator.EASE_OUT);
            scale.play();
        });

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 44px;");

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BLACK, 22));
        titleLabel.setStyle("-fx-text-fill: " + CYAN + ";");

        Label descLabel = new Label(description);
        descLabel.setStyle("-fx-text-fill: " + MUTED + "; -fx-font-size: 12px; -fx-text-alignment: center;");
        descLabel.setWrapText(true);

        Label enterLabel = new Label("ENTER \u2192");
        enterLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        enterLabel.setStyle("-fx-text-fill: " + CYAN + "; -fx-padding: 8 0 0 0;");

        card.getChildren().addAll(iconLabel, titleLabel, descLabel, enterLabel);
        return card;
    }
}
