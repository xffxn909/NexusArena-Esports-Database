package com.nexus.arena.model;

public class Game {
    private int gameId;
    private String gameName;
    private String publisher;
    private String platform;
    private String genre;

    public Game() {}

    public Game(int gameId, String gameName, String publisher, String platform, String genre) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.publisher = publisher;
        this.platform = platform;
        this.genre = genre;
    }

    public int getGameId() { return gameId; }
    public void setGameId(int gameId) { this.gameId = gameId; }
    public String getGameName() { return gameName; }
    public void setGameName(String gameName) { this.gameName = gameName; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    @Override
    public String toString() { return gameName; }
}
