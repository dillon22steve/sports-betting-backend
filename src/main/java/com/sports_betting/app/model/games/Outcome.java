package com.sports_betting.app.model.games;

// Outcome.java
import com.fasterxml.jackson.annotation.JsonProperty;

public class Outcome {

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private int price;

    @JsonProperty("point")
    private Double point; // Nullable — only present in spreads/totals markets

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public Double getPoint() { return point; }
    public void setPoint(Double point) { this.point = point; }
}
