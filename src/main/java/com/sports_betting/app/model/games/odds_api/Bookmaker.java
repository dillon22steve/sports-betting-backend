package com.sports_betting.app.model.games.odds_api;

// Bookmaker.java
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Bookmaker {

    @JsonProperty("key")
    private String key;

    @JsonProperty("title")
    private String title;

    @JsonProperty("last_update")
    private String lastUpdate;

    @JsonProperty("markets")
    private List<Market> markets;

    // Getters and Setters
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(String lastUpdate) { this.lastUpdate = lastUpdate; }

    public List<Market> getMarkets() { return markets; }
    public void setMarkets(List<Market> markets) { this.markets = markets; }
}
