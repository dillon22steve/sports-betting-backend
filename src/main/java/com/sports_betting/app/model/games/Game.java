package com.sports_betting.app.model.games;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;

@Entity
public class Game {

    @Id
    private String id;

    private String sportKey;

    private String commence_time;

    private String homeTeam;

    private String awayTeam;

    private String bookmaker_name;

    private int home_team_price;

    private int away_team_price;

    private Double home_team_spread;

    private Double home_team_spread_price;

    private Double  away_team_spread;

    private Double away_team_spread_price;

    private boolean home_team_won;

    private int home_team_score;

    private int away_team_score;

    private boolean isComplete;

    private LocalDateTime createdAt;


    public Game() {

    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSportKey() {
        return sportKey;
    }

    public void setSportKey(String sport_key) {
        this.sportKey = sport_key;
    }

    public String getCommence_time() {
        return commence_time;
    }

    public void setCommence_time(String commence_time) {
        this.commence_time = commence_time;
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

    public String getBookmaker_name() {
        return bookmaker_name;
    }

    public void setBookmaker_name(String bookmaker_name) {
        this.bookmaker_name = bookmaker_name;
    }

    public int getHome_team_price() {
        return home_team_price;
    }

    public void setHome_team_price(int home_team_price) {
        this.home_team_price = home_team_price;
    }

    public int getAway_team_price() {
        return away_team_price;
    }

    public void setAway_team_price(int away_team_price) {
        this.away_team_price = away_team_price;
    }

    public Double getHome_team_spread() {
        return home_team_spread;
    }

    public void setHome_team_spread(Double home_team_spread) {
        this.home_team_spread = home_team_spread;
    }

    public Double getHome_team_spread_price() {
        return home_team_spread_price;
    }

    public void setHome_team_spread_price(Double home_team_spread_price) {
        this.home_team_spread_price = home_team_spread_price;
    }

    public Double getAway_team_spread() {
        return away_team_spread;
    }

    public void setAway_team_spread(Double  away_team_spread) {
        this.away_team_spread = away_team_spread;
    }

    public Double getAway_team_spread_price() {
        return away_team_spread_price;
    }

    public void setAway_team_spread_price(Double away_team_spread_price) {
        this.away_team_spread_price = away_team_spread_price;
    }

    public boolean isHome_team_won() {
        return home_team_won;
    }

    public void setHome_team_won(boolean home_team_won) {
        this.home_team_won = home_team_won;
    }

    public int getHome_team_score() {
        return home_team_score;
    }

    public void setHome_team_score(int home_team_score) {
        this.home_team_score = home_team_score;
    }

    public int getAway_team_score() {
        return away_team_score;
    }

    public void setAway_team_score(int away_team_score) {
        this.away_team_score = away_team_score;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }
}
