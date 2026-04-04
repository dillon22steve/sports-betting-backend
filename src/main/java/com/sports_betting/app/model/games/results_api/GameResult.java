package com.sports_betting.app.model.games.results_api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameResult {

    @JsonProperty("id")
    private String id;

    @JsonProperty("sport_key")
    private String sportKey;

    @JsonProperty("sport_title")
    private String sportTitle;

    @JsonProperty("commence_time")
    private String commenceTime;

    @JsonProperty("home_team")
    private String homeTeam;

    @JsonProperty("away_team")
    private String awayTeam;

    @JsonProperty("scores")
    private Score[] scores;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSportKey() {
        return sportKey;
    }

    public void setSportKey(String sportKey) {
        this.sportKey = sportKey;
    }

    public String getSportTitle() {
        return sportTitle;
    }

    public void setSportTitle(String sportTitle) {
        this.sportTitle = sportTitle;
    }

    public String getCommenceTime() {
        return commenceTime;
    }

    public void setCommenceTime(String commenceTime) {
        this.commenceTime = commenceTime;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Score[] getScores() {
        return scores;
    }

    public void setScores(Score[] scores) {
        this.scores = scores;
    }
    
}
