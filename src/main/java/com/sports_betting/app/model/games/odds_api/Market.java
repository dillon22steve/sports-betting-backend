package com.sports_betting.app.model.games.odds_api;

// Market.java
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Market {

    @JsonProperty("key")
    private String key;

    @JsonProperty("last_update")
    private String lastUpdate;

    @JsonProperty("outcomes")
    private List<Outcome> outcomes;

    // Getters and Setters
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(String lastUpdate) { this.lastUpdate = lastUpdate; }

    public List<Outcome> getOutcomes() { return outcomes; }
    public void setOutcomes(List<Outcome> outcomes) { this.outcomes = outcomes; }
}
