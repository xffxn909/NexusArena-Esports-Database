package com.nexus.arena.ui;

import com.nexus.arena.dao.DatabaseConnection;
import com.nexus.arena.dao.NexusDAO;
import com.nexus.arena.model.*;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

/**
 * NexusArena Main Dashboard — Role-based interfaces with glassmorphism tables.
 *
 * PLAYER:    Read-only tables for Games, Tourneys, Leaderboard, Earnings
 * ADMIN:     Full CRUD on all 10 tables + SQL console + system overview
 * COMMUNITY: Read-only tables for all data, avoiding SQL Console and forms
 */
public class NexusArenaUI {

    private static final String CYAN = "#00d4ff";
    private static final String BG = "#0d0e1a";
    private static final String SIDEBAR_BG = "#0a0b14";
    private static final String MUTED = "#8b8ba3";
    private static final String GLASS = "rgba(255,255,255,0.04)";
    private static final String GLASS_HOVER = "rgba(255,255,255,0.07)";
    private static final String GLASS_BORDER = "rgba(0,212,255,0.12)";
    private static final String CARD_BG = "rgba(255,255,255,0.035)";
    private static final String DELETE_COLOR = "#ff2d55";

    private final NexusDAO dao = new NexusDAO();
    private final String role;
    private final StackPane rootPane;
    private final BorderPane mainLayout;
    private ToastNotification toast;
    private StackPane contentArea;
    private String activeNav = "";
    private final Consumer<Void> onLogout;
    private final java.util.Map<String, HBox> navButtons = new java.util.LinkedHashMap<>();

    public NexusArenaUI(String role, Consumer<Void> onLogout) {
        this.role = role;
        this.onLogout = onLogout;
        this.rootPane = new StackPane();
        this.mainLayout = new BorderPane();
        this.mainLayout.setStyle("-fx-background-color: " + BG + ";");
        rootPane.getChildren().add(mainLayout);
        this.toast = new ToastNotification(rootPane);
        buildUI();
    }

    public StackPane getRoot() { return rootPane; }

    private void buildUI() {
        mainLayout.setTop(buildTopBar());
        mainLayout.setLeft(buildSidebar());
        contentArea = new StackPane();
        contentArea.setStyle("-fx-background-color: " + BG + ";");
        mainLayout.setCenter(contentArea);

        switch (role.toLowerCase()) {
            case "player": navigateTo("DASHBOARD"); break;
            case "community": navigateTo("HOME"); break;
            default: navigateTo("OVERVIEW"); break;
        }
    }

    private HBox buildTopBar() {
        HBox topBar = new HBox(16);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10, 24, 10, 24));
        topBar.setStyle("-fx-background-color: " + BG + ";" +
                "-fx-border-color: " + GLASS_BORDER + ";" +
                "-fx-border-width: 0 0 1 0;");

        Label logo = new Label("NEXUSARENA");
        logo.setFont(Font.font("Segoe UI", FontWeight.BLACK, 18));
        logo.setStyle("-fx-text-fill: " + CYAN + "; -fx-font-style: italic;");
        Region spacer = new Region(); HBox.setHgrow(spacer, Priority.ALWAYS);

        boolean connected = DatabaseConnection.testConnection();
        Label dbStatus = new Label(connected ? "\u25CF DB CONNECTED" : "\u25CF OFFLINE");
        dbStatus.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
        dbStatus.setPadding(new Insets(5, 12, 5, 12));
        dbStatus.setStyle("-fx-background-color: " + (connected ? "rgba(0,212,255,0.1)" : "rgba(255,45,85,0.1)") + ";" +
                "-fx-text-fill: " + (connected ? CYAN : DELETE_COLOR) + "; -fx-background-radius: 6;");

        String roleIcon = role.equalsIgnoreCase("admin") ? "\u26A1" : role.equalsIgnoreCase("player") ? "\uD83C\uDFAE" : "\uD83D\uDC65";
        Label roleBadge = new Label(roleIcon + "  " + role.toUpperCase());
        roleBadge.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
        roleBadge.setPadding(new Insets(5, 12, 5, 12));
        roleBadge.setStyle("-fx-background-color: rgba(0,212,255,0.08); -fx-text-fill: " + CYAN + "; -fx-background-radius: 6;" +
                "-fx-border-color: " + GLASS_BORDER + "; -fx-border-radius: 6; -fx-border-width: 1;");

        Button logoutBtn = new Button("LOG OUT");
        logoutBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
        logoutBtn.setCursor(Cursor.HAND);
        logoutBtn.setStyle("-fx-background-color: " + GLASS + "; -fx-text-fill: " + MUTED + "; -fx-background-radius: 6; -fx-padding: 5 12; -fx-border-color: rgba(255,255,255,0.06); -fx-border-radius: 6;");
        logoutBtn.setOnMouseEntered(e -> logoutBtn.setStyle("-fx-background-color: rgba(255,45,85,0.12); -fx-text-fill: " + DELETE_COLOR + "; -fx-background-radius: 6; -fx-padding: 5 12; -fx-border-color: " + DELETE_COLOR + "; -fx-border-radius: 6;"));
        logoutBtn.setOnMouseExited(e -> logoutBtn.setStyle("-fx-background-color: " + GLASS + "; -fx-text-fill: " + MUTED + "; -fx-background-radius: 6; -fx-padding: 5 12; -fx-border-color: rgba(255,255,255,0.06); -fx-border-radius: 6;"));
        logoutBtn.setOnAction(e -> { if (onLogout != null) onLogout.accept(null); });

        topBar.getChildren().addAll(logo, spacer, dbStatus, roleBadge, logoutBtn);
        return topBar;
    }

    private VBox buildSidebar() {
        VBox sidebar = new VBox(2);
        sidebar.setPrefWidth(190);
        sidebar.setStyle("-fx-background-color: " + SIDEBAR_BG + "; -fx-border-color: " + GLASS_BORDER + "; -fx-border-width: 0 1 0 0;");

        VBox profile = new VBox(3);
        profile.setPadding(new Insets(18, 14, 14, 14));

        Label pRole = new Label(role.toUpperCase() + " ACCESS");
        pRole.setStyle("-fx-text-fill: " + MUTED + "; -fx-font-size: 9px;");
        profile.getChildren().addAll( pRole);
        sidebar.getChildren().addAll(profile, sep());

        boolean isAdmin = role.equalsIgnoreCase("admin");
        boolean isComm = role.equalsIgnoreCase("community");

        if (isAdmin) addNav(sidebar, "\uD83D\uDCCA", "OVERVIEW");
        if (role.equalsIgnoreCase("player")) addNav(sidebar, "\uD83C\uDFE0", "DASHBOARD");
        if (isComm) addNav(sidebar, "\uD83C\uDFE0", "HOME");

        sidebar.getChildren().add(sep());
        
        // Data tabs
        addNav(sidebar, "\uD83C\uDFAE", "GAMES");
        addNav(sidebar, "\uD83C\uDFC6", "TOURNAMENTS");
        addNav(sidebar, "\uD83D\uDCCA", "LEADERBOARD");
        
        if (isAdmin || isComm) {
            addNav(sidebar, "\uD83D\uDC64", "PLAYERS");
            addNav(sidebar, "\uD83D\uDC65", "TEAMS");
            addNav(sidebar, "\u2694", "MATCHES");
            addNav(sidebar, "\uD83C\uDFE2", "SPONSORS");
        }
        if (role.equalsIgnoreCase("player")) {
            addNav(sidebar, "\uD83D\uDCB0", "EARNINGS");
        }

        if (isAdmin) {
            sidebar.getChildren().add(sep());
            addNav(sidebar, "\uD83D\uDCCB", "ROSTERS");
            addNav(sidebar, "\uD83C\uDFC5", "PRIZES");
            addNav(sidebar, "\uD83D\uDCDD", "REGISTRATIONS");
            addNav(sidebar, "\uD83E\uDD1D", "SPONSORSHIPS");
            sidebar.getChildren().add(sep());
            addNav(sidebar, "\u26A1", "SQL CONSOLE");
        }

        Region s = new Region(); VBox.setVgrow(s, Priority.ALWAYS); sidebar.getChildren().add(s);
        return sidebar;
    }

    private Region sep() {
        Region s = new Region(); s.setPrefHeight(1); s.setMaxHeight(1);
        s.setStyle("-fx-background-color: rgba(255,255,255,0.05);");
        VBox.setMargin(s, new Insets(6, 12, 6, 12)); return s;
    }

    private void addNav(VBox s, String ic, String n) {
        HBox b = new HBox(10); b.setAlignment(Pos.CENTER_LEFT); b.setPadding(new Insets(9, 14, 9, 14)); b.setCursor(Cursor.HAND);
        Label iL = new Label(ic); iL.setStyle("-fx-font-size: 13px;");
        Label tL = new Label(n); tL.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11)); tL.setStyle("-fx-text-fill: " + MUTED + ";");
        b.getChildren().addAll(iL, tL); b.setStyle("-fx-background-color: transparent;");
        b.setOnMouseEntered(e -> { if (!n.equals(activeNav)) b.setStyle("-fx-background-color: rgba(255,255,255,0.03);"); });
        b.setOnMouseExited(e -> { if (!n.equals(activeNav)) b.setStyle("-fx-background-color: transparent;"); });
        b.setOnMouseClicked(e -> navigateTo(n));
        navButtons.put(n, b); s.getChildren().add(b);
    }

    private void setActive(HBox b) {
        b.setStyle("-fx-background-color: rgba(0,212,255,0.08); -fx-border-color: " + CYAN + "; -fx-border-width: 0 0 0 3;");
        for (Node c : b.getChildren()) if (c instanceof Label) ((Label) c).setStyle("-fx-text-fill: " + CYAN + "; -fx-font-weight: bold;");
    }

    private void setInactive(HBox b) {
        b.setStyle("-fx-background-color: transparent;");
        for (Node c : b.getChildren()) if (c instanceof Label) ((Label) c).setStyle("-fx-text-fill: " + MUTED + "; -fx-font-weight: bold;");
    }

    private void navigateTo(String section) {
        activeNav = section;
        navButtons.forEach((n, b) -> { if (n.equals(section)) setActive(b); else setInactive(b); });

        Node content;
        boolean ro = !role.equalsIgnoreCase("admin"); 
        switch (section) {
            case "DASHBOARD":      content = buildPlayerDashboard(); break;
            case "HOME":           content = buildCommunityHome(); break;
            case "EARNINGS":       content = buildPlayerEarnings(); break;
            case "OVERVIEW":       content = buildAdminOverview(); break;
            case "LEADERBOARD":    content = buildLeaderboard(); break;
            case "SQL CONSOLE":    content = buildSqlConsole(); break;
            
            // CRUD / ReadOnly tables (Glassmorphism for all roles)
            case "PLAYERS":        content = ro ? buildRoPage("PLAYERS", "\uD83D\uDC64", buildPlayerTable(true)) : buildCrudPage("PLAYER", "\uD83D\uDC64", buildPlayerForm(), buildPlayerTable(false)); break;
            case "TEAMS":          content = ro ? buildRoPage("TEAMS", "\uD83D\uDC65", buildTeamTable(true)) : buildCrudPage("TEAM", "\uD83D\uDC65", buildTeamForm(), buildTeamTable(false)); break;
            case "GAMES":          content = ro ? buildRoPage("GAMES", "\uD83C\uDFAE", buildGameTable(true)) : buildCrudPage("GAME", "\uD83C\uDFAE", buildGameForm(), buildGameTable(false)); break;
            case "TOURNAMENTS":    content = ro ? buildRoPage("TOURNAMENTS", "\uD83C\uDFC6", buildTournamentTable(true)) : buildCrudPage("TOURNAMENT", "\uD83C\uDFC6", buildTournamentForm(), buildTournamentTable(false)); break;
            case "MATCHES":        content = ro ? buildRoPage("MATCH RESULTS", "\u2694", buildMatchTable(true)) : buildCrudPage("MATCH", "\u2694", buildMatchForm(), buildMatchTable(false)); break;
            case "SPONSORS":       content = ro ? buildRoPage("SPONSORS", "\uD83C\uDFE2", buildSponsorTable(true)) : buildCrudPage("SPONSOR", "\uD83C\uDFE2", buildSponsorForm(), buildSponsorTable(false)); break;
            case "ROSTERS":        content = buildCrudPage("ROSTER", "\uD83D\uDCCB", buildRosterForm(), buildRosterTable(false)); break;
            case "PRIZES":         content = buildCrudPage("PRIZE_DISTRIBUTION", "\uD83C\uDFC5", buildPrizeForm(), buildPrizeTable(false)); break;
            case "REGISTRATIONS":  content = buildCrudPage("TOURNAMENT_REGISTRATION", "\uD83D\uDCDD", buildRegForm(), buildRegTable(false)); break;
            case "SPONSORSHIPS":   content = buildCrudPage("SPONSORSHIP", "\uD83E\uDD1D", buildSponsorshipForm(), buildSponsorshipTable(false)); break;
            default: return;
        }

        ScrollPane sp = new ScrollPane(content);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background-color: " + BG + ";");
        sp.getStyleClass().add("edge-to-edge");

        // --- Smooth Scroll Hijacking ---
        final double[] targetV = {0};
        sp.vvalueProperty().addListener((obs, oldVal, newVal) -> {
            if (Math.abs(targetV[0] - newVal.doubleValue()) > 0.02) {
                targetV[0] = newVal.doubleValue(); 
            }
        });
        sp.addEventFilter(javafx.scene.input.ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() != 0) {
                Node viewNode = sp.getContent();
                if (viewNode != null) {
                    double maxScroll = viewNode.getBoundsInLocal().getHeight() - sp.getViewportBounds().getHeight();
                    if (maxScroll > 0) {
                        targetV[0] = Math.max(0, Math.min(1, targetV[0] - (event.getDeltaY() * 4.0) / maxScroll));
                        Timeline smoothScroll = new Timeline(new KeyFrame(Duration.millis(400), new KeyValue(sp.vvalueProperty(), targetV[0], Interpolator.EASE_OUT)));
                        smoothScroll.play();
                        event.consume();
                    }
                }
            }
        });

        if (!contentArea.getChildren().isEmpty()) {
            Node old = contentArea.getChildren().get(0);
            FadeTransition fo = new FadeTransition(Duration.millis(200), old);
            fo.setToValue(0);
            fo.setOnFinished(e -> {
                contentArea.getChildren().setAll(sp);
                sp.setOpacity(0);
                FadeTransition fi = new FadeTransition(Duration.millis(700), sp);
                fi.setToValue(1); fi.play();
            });
            fo.play();
        } else {
            contentArea.getChildren().setAll(sp);
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  DASHBOARDS
    // ════════════════════════════════════════════════════════════════

    private VBox buildPlayerDashboard() {
        VBox page = pageContainer();
        Label w = new Label("WELCOME TO NEXUSARENA"); w.setFont(Font.font("Segoe UI", FontWeight.BLACK, 28)); w.setStyle("-fx-text-fill: white;");
        Label sub = new Label("Your competitive gaming command center. Track tournaments and your overall ranking."); sub.setStyle("-fx-text-fill: " + MUTED + "; -fx-font-size: 13px;");
        HBox stats = new HBox(16); stats.setPadding(new Insets(10, 0, 0, 0));
        stats.getChildren().addAll(glassStatCard("GAMES", String.valueOf(dao.getTableRowCount("GAME")), "\uD83C\uDFAE"), glassStatCard("TOURNAMENTS", String.valueOf(dao.getTableRowCount("TOURNAMENT")), "\uD83C\uDFC6"));
        page.getChildren().addAll(w, sub, stats, sectionTitle("ACTIVE GAMES"), glassCard(buildGameTable(true)));
        return page;
    }

    private VBox buildCommunityHome() {
        VBox page = pageContainer();
        Label t = new Label("COMMUNITY HUB"); t.setFont(Font.font("Segoe UI", FontWeight.BLACK, 28)); t.setStyle("-fx-text-fill: white;");
        Label sub = new Label("Welcome to the NexusArena community."); sub.setStyle("-fx-text-fill: " + MUTED + "; -fx-font-size: 13px;");
        HBox stats = new HBox(16); stats.setPadding(new Insets(10, 0, 0, 0));
        stats.getChildren().addAll(glassStatCard("TEAMS", String.valueOf(dao.getTableRowCount("TEAM")), "\uD83D\uDC65"), glassStatCard("PLAYERS", String.valueOf(dao.getTableRowCount("PLAYER")), "\uD83D\uDC64"), glassStatCard("SPONSORS", String.valueOf(dao.getTableRowCount("SPONSOR")), "\uD83C\uDFE2"), glassStatCard("PRIZE POOL", "$" + formatNumber(dao.getTotalPrizePool().toPlainString()), "\uD83D\uDCB0"));
        page.getChildren().addAll(t, sub, stats, sectionTitle("RECENT OVERVIEW"), glassCard(buildMatchTable(true)));
        return page;
    }

    private VBox buildAdminOverview() {
        VBox page = pageContainer();
        Label t = new Label("\u26A1 SYSTEM OVERVIEW"); t.setFont(Font.font("Segoe UI", FontWeight.BLACK, 28)); t.setStyle("-fx-text-fill: white;");
        Label sub = new Label("NEXUSARENA DATABASE CLUSTER"); sub.setStyle("-fx-text-fill: #4a4a6a; -fx-font-size: 11px;");
        HBox s1 = new HBox(16); s1.getChildren().addAll(glassStatCard("PLAYERS", String.valueOf(dao.getTableRowCount("PLAYER")), "\uD83D\uDC64"), glassStatCard("TEAMS", String.valueOf(dao.getTableRowCount("TEAM")), "\uD83D\uDC65"), glassStatCard("GAMES", String.valueOf(dao.getTableRowCount("GAME")), "\uD83C\uDFAE"), glassStatCard("TOURNAMENTS", String.valueOf(dao.getTableRowCount("TOURNAMENT")), "\uD83C\uDFC6"));
        HBox s2 = new HBox(16); s2.getChildren().addAll(glassStatCard("MATCHES", String.valueOf(dao.getTableRowCount("MATCH")), "\u2694"), glassStatCard("ROSTERS", String.valueOf(dao.getTableRowCount("ROSTER")), "\uD83D\uDCCB"), glassStatCard("SPONSORS", String.valueOf(dao.getTableRowCount("SPONSOR")), "\uD83C\uDFE2"), glassStatCard("TOTAL PRIZES", "$" + formatNumber(dao.getTotalPrizePool().toPlainString()), "\uD83D\uDCB0"));
        page.getChildren().addAll(t, sub, s1, s2, sectionTitle("GLOBAL LEADERBOARD"), glassCard(buildLeaderboardTable()));
        return page;
    }

    private VBox buildPlayerEarnings() {
        VBox p = pageContainer();
        p.getChildren().addAll(sectionTitle("PLAYER EARNINGS"), new Label("Computed from vw_player_earnings"), glassCard(buildPlayerEarningsTable()));
        p.getChildren().addAll(sectionTitle("TEAM EARNINGS"), new Label("Computed from vw_team_earnings"), glassCard(buildTeamEarningsTable()));
        return p;
    }

    private VBox buildLeaderboard() {
        VBox p = pageContainer();
        p.getChildren().addAll(sectionTitle("GLOBAL LEADERBOARD"), new Label("Computed from vw_global_leaderboard"), glassCard(buildLeaderboardTable()));
        return p;
    }

    private VBox buildRoPage(String tb, String icon, VBox tbl) {
        VBox p = pageContainer();
        Label t = new Label(icon + "  " + tb); t.setFont(Font.font("Segoe UI", FontWeight.BLACK, 24)); t.setStyle("-fx-text-fill: white;");
        p.getChildren().addAll(t, new Label("NEXUSARENA"), glassCard(tbl));
        return p;
    }
    private VBox buildCrudPage(String tb, String icon, HBox frm, VBox tbl) {
        VBox p = pageContainer();
        Label t = new Label(icon + "  " + tb.replace("_", " ")); t.setFont(Font.font("Segoe UI", FontWeight.BLACK, 24)); t.setStyle("-fx-text-fill: white;");
        p.getChildren().addAll(t, new Label("NEXUSARENA // " + tb + " Data Network"), glassCard(frm), glassCard(tbl));
        return p;
    }

    // ════════════════════════════════════════════════════════════════
    // TABLE BUILDERS (Glassmorphism support for both Read-Only & CRUD)
    // ════════════════════════════════════════════════════════════════

    private VBox buildLeaderboardTable() {
        VBox t = new VBox(2); t.getChildren().add(headerRow("#", "TEAM", "COUNTRY", "TOTAL EARNINGS", "PODIUM FINISHES"));
        int rk = 1; for (String[] r : dao.getGlobalLeaderboard()) { t.getChildren().add(dataRow(String.valueOf(rk++), r[0], r[1], "$" + formatNumber(r[2]), r[3])); } return t;
    }
    private VBox buildPlayerEarningsTable() {
        VBox t = new VBox(2); t.getChildren().add(headerRow("ID", "Username", "Real Name", "Total Earnings"));
        for (String[] r : dao.getPlayerEarnings()) t.getChildren().add(dataRow(r[0], r[1], r[2], "$" + formatNumber(r[3]))); return t;
    }
    private VBox buildTeamEarningsTable() {
        VBox t = new VBox(2); t.getChildren().add(headerRow("ID", "Team Name", "Country", "Total Prize Money"));
        for (String[] r : dao.getTeamEarnings()) t.getChildren().add(dataRow(r[0], r[1], r[2], "$" + formatNumber(r[3]))); return t;
    }

    // --- Entity Tables ---

    private VBox buildPlayerTable(boolean ro) {
        VBox t = new VBox(2); t.getChildren().add(ro ? headerRow("ID", "Username", "Real Name", "Nationality", "Email", "Rank", "DOB") : headerRow("ID", "Username", "Real Name", "Nationality", "Email", "Rank", "DOB", ""));
        for (Player p : dao.getAllPlayers()) {
            HBox r = dataRow(String.valueOf(p.getPlayerId()), p.getUsername(), p.getRealName(), nvl(p.getNationality()), nvl(p.getEmail()), nvl(p.getPlayerRank()), p.getDateOfBirth() != null ? p.getDateOfBirth().toString() : "");
            if (!ro) { Button d = delBtn(); d.setOnAction(e -> { dao.deletePlayer(p.getPlayerId()); toast.showInfo("Deleted."); navigateTo("PLAYERS"); }); r.getChildren().add(d); }
            t.getChildren().add(r);
        } return t;
    }
    private VBox buildTeamTable(boolean ro) {
        VBox t = new VBox(2); t.getChildren().add(ro ? headerRow("ID", "Name", "Founded", "Coach", "Country") : headerRow("ID", "Name", "Founded", "Coach", "Country", ""));
        for (Team tm : dao.getAllTeams()) {
            HBox r = dataRow(String.valueOf(tm.getTeamId()), tm.getTeamName(), tm.getFoundedDate() != null ? tm.getFoundedDate().toString() : "", nvl(tm.getCoachName()), nvl(tm.getCountry()));
            if (!ro) { Button d = delBtn(); d.setOnAction(e -> { dao.deleteTeam(tm.getTeamId()); toast.showInfo("Deleted."); navigateTo("TEAMS"); }); r.getChildren().add(d); }
            t.getChildren().add(r);
        } return t;
    }
    private VBox buildGameTable(boolean ro) {
        VBox t = new VBox(2); t.getChildren().add(ro ? headerRow("ID", "Name", "Publisher", "Platform", "Genre") : headerRow("ID", "Name", "Publisher", "Platform", "Genre", ""));
        for (Game g : dao.getAllGames()) {
            HBox r = dataRow(String.valueOf(g.getGameId()), g.getGameName(), g.getPublisher(), nvl(g.getPlatform()), nvl(g.getGenre()));
            if (!ro) { Button d = delBtn(); d.setOnAction(e -> { dao.deleteGame(g.getGameId()); toast.showInfo("Deleted."); navigateTo("GAMES"); }); r.getChildren().add(d); }
            t.getChildren().add(r);
        } return t;
    }
    private VBox buildTournamentTable(boolean ro) {
        VBox t = new VBox(2); t.getChildren().add(ro ? headerRow("ID", "Name", "Game", "Start", "End", "Location", "Format", "Prize") : headerRow("ID", "Name", "Game", "Start", "End", "Location", "Format", "Prize", ""));
        for (Tournament tm : dao.getAllTournaments()) {
            HBox r = dataRow(String.valueOf(tm.getTournamentId()), tm.getTournamentName(), String.valueOf(tm.getGameId()), tm.getStartDate().toString(), tm.getEndDate().toString(), tm.getLocation(), tm.getFormat(), "$" + formatNumber(tm.getTotalPrizePool().toPlainString()));
            if (!ro) { Button d = delBtn(); d.setOnAction(e -> { dao.deleteTournament(tm.getTournamentId()); toast.showInfo("Deleted."); navigateTo("TOURNAMENTS"); }); r.getChildren().add(d); }
            t.getChildren().add(r);
        } return t;
    }
    private VBox buildMatchTable(boolean ro) {
        VBox t = new VBox(2); t.getChildren().add(ro ? headerRow("ID", "Tourn", "T1", "T2", "Date", "Stage", "Winner", "Score") : headerRow("ID", "Tourn", "T1", "T2", "Date", "Stage", "Winner", "Score", ""));
        for (Match m : dao.getAllMatches()) {
            HBox r = dataRow(String.valueOf(m.getMatchId()), String.valueOf(m.getTournamentId()), String.valueOf(m.getTeam1Id()), String.valueOf(m.getTeam2Id()), m.getMatchDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), m.getStage(), m.getWinnerTeamId() != null ? String.valueOf(m.getWinnerTeamId()) : "\u2014", nvl(m.getScore()));
            if (!ro) { Button d = delBtn(); d.setOnAction(e -> { dao.deleteMatch(m.getMatchId()); toast.showInfo("Deleted."); navigateTo("MATCHES"); }); r.getChildren().add(d); }
            t.getChildren().add(r);
        } return t;
    }
    private VBox buildSponsorTable(boolean ro) {
        VBox t = new VBox(2); t.getChildren().add(ro ? headerRow("ID", "Name", "Industry", "Email") : headerRow("ID", "Name", "Industry", "Email", ""));
        for (Sponsor s : dao.getAllSponsors()) {
            HBox r = dataRow(String.valueOf(s.getSponsorId()), s.getSponsorName(), s.getIndustry(), s.getContactEmail());
            if (!ro) { Button d = delBtn(); d.setOnAction(e -> { dao.deleteSponsor(s.getSponsorId()); toast.showInfo("Deleted."); navigateTo("SPONSORS"); }); r.getChildren().add(d); }
            t.getChildren().add(r);
        } return t;
    }
    private VBox buildRosterTable(boolean ro) {
        VBox t = new VBox(2); t.getChildren().add(ro ? headerRow("ID", "Player", "Team", "Game", "Join Date", "Role") : headerRow("ID", "Player", "Team", "Game", "Join Date", "Role", ""));
        for (Roster r : dao.getAllRosters()) {
            HBox rw = dataRow(String.valueOf(r.getRosterId()), String.valueOf(r.getPlayerId()), String.valueOf(r.getTeamId()), String.valueOf(r.getGameId()), r.getJoinDate() != null ? r.getJoinDate().toString() : "", nvl(r.getRole()));
            if (!ro) { Button d = delBtn(); d.setOnAction(e -> { dao.deleteRoster(r.getRosterId()); toast.showInfo("Deleted."); navigateTo("ROSTERS"); }); rw.getChildren().add(d); }
            t.getChildren().add(rw);
        } return t;
    }
    private VBox buildPrizeTable(boolean ro) {
        VBox t = new VBox(2); t.getChildren().add(ro ? headerRow("ID", "Tournament", "Team", "Position", "Prize") : headerRow("ID", "Tournament", "Team", "Position", "Prize", ""));
        for (PrizeDistribution p : dao.getAllPrizes()) {
            HBox rw = dataRow(String.valueOf(p.getPrizeId()), String.valueOf(p.getTournamentId()), String.valueOf(p.getTeamId()), String.valueOf(p.getPosition()), "$" + p.getPrizeAmount().toPlainString());
            if (!ro) { Button d = delBtn(); d.setOnAction(e -> { dao.deletePrize(p.getPrizeId()); toast.showInfo("Deleted."); navigateTo("PRIZES"); }); rw.getChildren().add(d); }
            t.getChildren().add(rw);
        } return t;
    }
    private VBox buildRegTable(boolean ro) {
        VBox t = new VBox(2); t.getChildren().add(ro ? headerRow("ID", "Tournament", "Team", "Date", "Status") : headerRow("ID", "Tournament", "Team", "Date", "Status", ""));
        for (TournamentRegistration r : dao.getAllRegistrations()) {
            HBox rw = dataRow(String.valueOf(r.getRegistrationId()), String.valueOf(r.getTournamentId()), String.valueOf(r.getTeamId()), r.getRegistrationDate().toString(), r.getStatus());
            if (!ro) { Button d = delBtn(); d.setOnAction(e -> { dao.deleteRegistration(r.getRegistrationId()); toast.showInfo("Deleted."); navigateTo("REGISTRATIONS"); }); rw.getChildren().add(d); }
            t.getChildren().add(rw);
        } return t;
    }
    private VBox buildSponsorshipTable(boolean ro) {
        VBox t = new VBox(2); t.getChildren().add(ro ? headerRow("ID", "Sponsor", "Tournament", "Amount", "Start", "End") : headerRow("ID", "Sponsor", "Tournament", "Amount", "Start", "End", ""));
        for (Sponsorship s : dao.getAllSponsorships()) {
            HBox rw = dataRow(String.valueOf(s.getSponsorshipId()), String.valueOf(s.getSponsorId()), String.valueOf(s.getTournamentId()), "$" + s.getAmountContributed().toPlainString(), s.getContractStart().toString(), s.getContractEnd().toString());
            if (!ro) { Button d = delBtn(); d.setOnAction(e -> { dao.deleteSponsorship(s.getSponsorshipId()); toast.showInfo("Deleted."); navigateTo("SPONSORSHIPS"); }); rw.getChildren().add(d); }
            t.getChildren().add(rw);
        } return t;
    }

    // ════════════════════════════════════════════════════════════════
    // FORMS + SQL CONSOLE
    // ════════════════════════════════════════════════════════════════

    private HBox buildPlayerForm() {
        HBox f = crudForm(); TextField u = gf("Username", 110), r = gf("Real Name", 120), n = gf("Nationality", 90), e = gf("Email", 130), rk = gf("Rank", 80), d = gf("DOB", 110);
        Button add = cyanBtn("+ ADD"); add.setOnAction(ev -> { Player p = new Player(); p.setUsername(u.getText().trim()); p.setRealName(r.getText().trim()); p.setNationality(n.getText().trim()); p.setEmail(e.getText().trim()); p.setPlayerRank(rk.getText().trim()); try { p.setDateOfBirth(LocalDate.parse(d.getText().trim())); } catch (Exception ignored) {} if (p.getUsername().isEmpty() || p.getRealName().isEmpty()) { toast.showWarning("Username & Real Name required."); return; } if (dao.insertPlayer(p)) { toast.showInfo("Added."); navigateTo("PLAYERS"); } else toast.showError("Failed."); });
        f.getChildren().addAll(u, r, n, e, rk, d, add); return f;
    }
    private HBox buildTeamForm() {
        HBox f = crudForm(); TextField n = gf("Team Name", 140), fd = gf("Founded", 120), c = gf("Coach", 120), co = gf("Country", 90);
        Button add = cyanBtn("+ ADD"); add.setOnAction(e -> { Team t = new Team(); t.setTeamName(n.getText().trim()); t.setCoachName(c.getText().trim()); t.setCountry(co.getText().trim()); try { t.setFoundedDate(LocalDate.parse(fd.getText().trim())); } catch (Exception ignored) {} if (t.getTeamName().isEmpty()) { toast.showWarning("Name required."); return; } if (dao.insertTeam(t)) { toast.showInfo("Added."); navigateTo("TEAMS"); } else toast.showError("Failed."); });
        f.getChildren().addAll(n, fd, c, co, add); return f;
    }
    private HBox buildGameForm() {
        HBox f = crudForm(); TextField n = gf("Name", 140), p = gf("Publisher", 120), pl = gf("Platform", 90), g = gf("Genre", 100);
        Button add = cyanBtn("+ ADD"); add.setOnAction(e -> { Game gm = new Game(); gm.setGameName(n.getText().trim()); gm.setPublisher(p.getText().trim()); gm.setPlatform(pl.getText().trim()); gm.setGenre(g.getText().trim()); if (gm.getGameName().isEmpty()) { toast.showWarning("Name missing."); return; } if (dao.insertGame(gm)) { toast.showInfo("Added."); navigateTo("GAMES"); } else toast.showError("Failed."); });
        f.getChildren().addAll(n, p, pl, g, add); return f;
    }
    private HBox buildTournamentForm() {
        HBox f = crudForm(); TextField n = gf("Name", 140), gid = gf("Game ID", 60), s = gf("Start", 95), e = gf("End", 95), l = gf("Location", 100), fmt = gf("Format", 80), pp = gf("Prize Pool", 90);
        Button add = cyanBtn("+ ADD"); add.setOnAction(ev -> { try { Tournament t = new Tournament(); t.setTournamentName(n.getText().trim()); t.setGameId(Integer.parseInt(gid.getText().trim())); t.setStartDate(LocalDate.parse(s.getText().trim())); t.setEndDate(LocalDate.parse(e.getText().trim())); t.setLocation(l.getText().trim()); t.setFormat(fmt.getText().trim()); t.setTotalPrizePool(new BigDecimal(pp.getText().trim())); if (dao.insertTournament(t)) { toast.showInfo("Added."); navigateTo("TOURNAMENTS"); } } catch (Exception ex) { toast.showWarning("Error."); } });
        f.getChildren().addAll(n, gid, s, e, l, fmt, pp, add); return f;
    }
    private HBox buildMatchForm() {
        HBox f = crudForm(); TextField tid = gf("Tourn ID", 70), t1 = gf("T1 ID", 55), t2 = gf("T2 ID", 55), dt = gf("Date (YYYY-MM-DD HH:MM)", 160), st = gf("Stage", 80), w = gf("Winner", 60), sc = gf("Score", 55);
        Button add = cyanBtn("+ ADD"); add.setOnAction(e -> { try { Match m = new Match(); m.setTournamentId(Integer.parseInt(tid.getText().trim())); m.setTeam1Id(Integer.parseInt(t1.getText().trim())); m.setTeam2Id(Integer.parseInt(t2.getText().trim())); m.setMatchDate(LocalDateTime.parse(dt.getText().trim().replace(" ", "T"))); m.setStage(st.getText().trim()); String wt = w.getText().trim(); m.setWinnerTeamId(wt.isEmpty() ? null : Integer.parseInt(wt)); m.setScore(sc.getText().trim()); if (dao.insertMatch(m)) { toast.showInfo("Added."); navigateTo("MATCHES"); } } catch (Exception ex) { toast.showWarning("Error."); } });
        f.getChildren().addAll(tid, t1, t2, dt, st, w, sc, add); return f;
    }
    private HBox buildSponsorForm() {
        HBox f = crudForm(); TextField n = gf("Name", 150), ind = gf("Industry", 130), em = gf("Email", 180);
        Button add = cyanBtn("+ ADD"); add.setOnAction(e -> { Sponsor s = new Sponsor(); s.setSponsorName(n.getText().trim()); s.setIndustry(ind.getText().trim()); s.setContactEmail(em.getText().trim()); if (s.getSponsorName().isEmpty()) { toast.showWarning("Name missing."); return; } if (dao.insertSponsor(s)) { toast.showInfo("Added."); navigateTo("SPONSORS"); } });
        f.getChildren().addAll(n, ind, em, add); return f;
    }
    private HBox buildRosterForm() {
        HBox f = crudForm(); TextField p = gf("Player ID", 70), t = gf("Team ID", 70), g = gf("Game ID", 70), j = gf("Join Date", 100), r = gf("Role", 100);
        Button add = cyanBtn("+ ADD"); add.setOnAction(e -> { try { Roster ro = new Roster(); ro.setPlayerId(Integer.parseInt(p.getText().trim())); ro.setTeamId(Integer.parseInt(t.getText().trim())); ro.setGameId(Integer.parseInt(g.getText().trim())); try { ro.setJoinDate(LocalDate.parse(j.getText().trim())); } catch (Exception ignored) {} ro.setRole(r.getText().trim()); if (dao.insertRoster(ro)) { toast.showInfo("Added."); navigateTo("ROSTERS"); } } catch (Exception ex) { toast.showWarning("Error."); } });
        f.getChildren().addAll(p, t, g, j, r, add); return f;
    }
    private HBox buildPrizeForm() {
        HBox f = crudForm(); TextField tid = gf("Tourn ID", 80), tm = gf("Team ID", 70), pos = gf("Position", 60), amt = gf("Amount", 100);
        Button add = cyanBtn("+ ADD"); add.setOnAction(e -> { try { PrizeDistribution p = new PrizeDistribution(); p.setTournamentId(Integer.parseInt(tid.getText().trim())); p.setTeamId(Integer.parseInt(tm.getText().trim())); p.setPosition(Integer.parseInt(pos.getText().trim())); p.setPrizeAmount(new BigDecimal(amt.getText().trim())); if (dao.insertPrize(p)) { toast.showInfo("Added."); navigateTo("PRIZES"); } } catch (Exception ex) { toast.showWarning("Error."); } });
        f.getChildren().addAll(tid, tm, pos, amt, add); return f;
    }
    private HBox buildRegForm() {
        HBox f = crudForm(); TextField tid = gf("Tourn ID", 80), tm = gf("Team ID", 70), dt = gf("Date", 100); ComboBox<String> cb = new ComboBox<>(); cb.getItems().addAll("Confirmed", "Pending", "Disqualified"); cb.setValue("Pending"); cb.setStyle("-fx-background-color: " + GLASS + "; -fx-border-color: " + GLASS_BORDER + "; -fx-border-radius: 6; -fx-background-radius: 6;");
        Button add = cyanBtn("+ ADD"); add.setOnAction(e -> { try { TournamentRegistration r = new TournamentRegistration(); r.setTournamentId(Integer.parseInt(tid.getText().trim())); r.setTeamId(Integer.parseInt(tm.getText().trim())); r.setRegistrationDate(LocalDate.parse(dt.getText().trim())); r.setStatus(cb.getValue()); if (dao.insertRegistration(r)) { toast.showInfo("Added."); navigateTo("REGISTRATIONS"); } } catch (Exception ex) { toast.showWarning("Error."); } });
        f.getChildren().addAll(tid, tm, dt, cb, add); return f;
    }
    private HBox buildSponsorshipForm() {
        HBox f = crudForm(); TextField sid = gf("Sponsor ID", 80), tid = gf("Tourn ID", 80), amt = gf("Amount", 100), s = gf("Start", 95), e = gf("End", 95);
        Button add = cyanBtn("+ ADD"); add.setOnAction(ev -> { try { Sponsorship sp = new Sponsorship(); sp.setSponsorId(Integer.parseInt(sid.getText().trim())); sp.setTournamentId(Integer.parseInt(tid.getText().trim())); sp.setAmountContributed(new BigDecimal(amt.getText().trim())); sp.setContractStart(LocalDate.parse(s.getText().trim())); sp.setContractEnd(LocalDate.parse(e.getText().trim())); if (dao.insertSponsorship(sp)) { toast.showInfo("Added."); navigateTo("SPONSORSHIPS"); } } catch (Exception ex) { toast.showWarning("Error."); } });
        f.getChildren().addAll(sid, tid, amt, s, e, add); return f;
    }

    private VBox buildSqlConsole() {
        VBox p = pageContainer(); p.getChildren().addAll(sectionTitle("SQL QUERY CONSOLE"), new Label("Execute SELECT queries only."));
        TextArea in = new TextArea(); in.setPromptText("SELECT * FROM PLAYER LIMIT 10;"); in.setPrefHeight(90);
        in.setStyle("-fx-control-inner-background: " + GLASS + "; -fx-text-fill: " + CYAN + "; -fx-font-family: 'Consolas'; -fx-border-color: " + GLASS_BORDER + "; -fx-border-radius: 10; -fx-background-radius: 10; -fx-font-size: 13px;");
        Button run = cyanBtn("\u25B6 EXECUTE"); run.setPrefWidth(150); VBox res = new VBox(2);
        run.setOnAction(e -> {
            String q = in.getText().trim(); if (q.isEmpty() || !q.toUpperCase().startsWith("SELECT")) { toast.showError("Only SELECT allowed."); return; }
            try { List<String[]> r = dao.executeQuery(q); res.getChildren().clear(); if (r.isEmpty()) { toast.showInfo("No results."); return; }
                res.getChildren().add(headerRow(r.get(0))); for (int i = 1; i < r.size(); i++) res.getChildren().add(dataRow(r.get(i))); toast.showInfo("Returned " + (r.size() - 1) + " rows.");
            } catch (SQLException ex) { toast.showError("Error: " + ex.getMessage()); }
        });
        p.getChildren().addAll(glassCard(in), run, glassCard(res)); return p;
    }

    // ════════════════════════════════════════════════════════════════
    // UTILS
    // ════════════════════════════════════════════════════════════════

    private VBox pageContainer() { VBox p = new VBox(14); p.setPadding(new Insets(28, 36, 28, 36)); p.setStyle("-fx-background-color: " + BG + ";"); return p; }
    private Label sectionTitle(String text) { Label l = new Label(text); l.setFont(Font.font("Segoe UI", FontWeight.BLACK, 16)); l.setStyle("-fx-text-fill: " + CYAN + "; -fx-padding: 12 0 4 0;"); return l; }
    private HBox crudForm() { HBox f = new HBox(6); f.setAlignment(Pos.CENTER_LEFT); f.setPadding(new Insets(10)); return f; }
    private TextField gf(String p, double w) { TextField t = new TextField(); t.setPromptText(p); t.setPrefWidth(w); t.getStyleClass().add("glass-input"); return t; }
    private Button cyanBtn(String text) { Button b = new Button(text); b.setCursor(Cursor.HAND); b.setStyle("-fx-background-color: " + CYAN + "; -fx-text-fill: #080810; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 7 14; -fx-font-size: 11px;"); b.setOnMouseEntered(e -> b.setStyle("-fx-background-color: #33dfff; -fx-text-fill: #080810; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 7 14; -fx-font-size: 11px;")); b.setOnMouseExited(e -> b.setStyle("-fx-background-color: " + CYAN + "; -fx-text-fill: #080810; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 7 14; -fx-font-size: 11px;")); return b; }
    private Button delBtn() { Button b = new Button("\u00D7"); b.setCursor(Cursor.HAND); b.setStyle("-fx-background-color: transparent; -fx-text-fill: " + MUTED + "; -fx-font-size: 15px; -fx-padding: 1 6;"); b.setOnMouseEntered(e -> b.setStyle("-fx-background-color: rgba(255,45,85,0.12); -fx-text-fill: " + DELETE_COLOR + "; -fx-font-size: 15px; -fx-padding: 1 6; -fx-background-radius: 5;")); b.setOnMouseExited(e -> b.setStyle("-fx-background-color: transparent; -fx-text-fill: " + MUTED + "; -fx-font-size: 15px; -fx-padding: 1 6;")); return b; }
    private VBox glassStatCard(String label, String value, String icon) { VBox card = new VBox(4); card.setPadding(new Insets(16, 20, 16, 20)); card.setPrefWidth(170); card.setStyle("-fx-background-color: " + CARD_BG + "; -fx-background-radius: 14; -fx-border-color: " + GLASS_BORDER + "; -fx-border-radius: 14; -fx-border-width: 1;"); HBox top = new HBox(8); top.setAlignment(Pos.CENTER_LEFT); Label iL = new Label(icon); iL.setStyle("-fx-font-size: 18px;"); Label lL = new Label(label); lL.setStyle("-fx-text-fill: " + MUTED + "; -fx-font-size: 9px; -fx-font-weight: bold;"); top.getChildren().addAll(iL, lL); Label vL = new Label(value); vL.setFont(Font.font("Segoe UI", FontWeight.BLACK, 22)); vL.setStyle("-fx-text-fill: white;"); card.getChildren().addAll(top, vL); return card; }
    private VBox glassCard(Node content) { VBox card = new VBox(); card.setPadding(new Insets(16)); card.setStyle("-fx-background-color: " + CARD_BG + "; -fx-background-radius: 14; -fx-border-color: " + GLASS_BORDER + "; -fx-border-radius: 14; -fx-border-width: 1;"); card.getChildren().add(content); return card; }
    private HBox headerRow(String... cols) { HBox r = new HBox(4); r.setPadding(new Insets(8, 10, 8, 10)); r.setStyle("-fx-background-color: rgba(0,212,255,0.06); -fx-background-radius: 6;"); for (String c : cols) { Label l = new Label(c); l.setPrefWidth(105); l.setMinWidth(50); l.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10)); l.setStyle("-fx-text-fill: " + CYAN + ";"); r.getChildren().add(l); } return r; }
    private HBox dataRow(String... vals) { HBox r = new HBox(4); r.setPadding(new Insets(7, 10, 7, 10)); r.setStyle("-fx-background-color: transparent;"); r.setOnMouseEntered(e -> r.setStyle("-fx-background-color: rgba(0,212,255,0.03);")); r.setOnMouseExited(e -> r.setStyle("-fx-background-color: transparent;")); for (String v : vals) { Label l = new Label(v); l.setPrefWidth(105); l.setMinWidth(50); l.setStyle("-fx-text-fill: white; -fx-font-size: 11px;"); r.getChildren().add(l); } return r; }
    private String nvl(String s) { return s == null ? "" : s; }
    private String formatNumber(String num) { try { return String.format("%,.0f", new BigDecimal(num)); } catch (Exception e) { return num; } }
}
