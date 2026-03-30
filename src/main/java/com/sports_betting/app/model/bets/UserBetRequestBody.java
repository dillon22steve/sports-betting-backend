package com.sports_betting.app.model.bets;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserBetRequestBody {

    private String userId;

    private String gameId;

    private String teamBetOn;

    private Double amountBet;

    private Double amountToWin;

    @JsonProperty("isSpreadBet")
    private boolean isSpreadBet;
    

    public UserBetRequestBody() {
    }

    public UserBetRequestBody(String userId, String gameId, String teamBetOn, Double amountBet, boolean isSpreadBet) {
        this.userId = userId;
        this.gameId = gameId;
        this.teamBetOn = teamBetOn;
        this.amountBet = amountBet;
        this.isSpreadBet = isSpreadBet;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Double getAmountBet() {
        return amountBet;
    }

    public void setAmountBet(Double amountBet) {
        this.amountBet = amountBet;
    }

    public boolean isSpreadBet() {
        return isSpreadBet;
    }

    public void setSpreadBet(boolean spreadBet) {
        isSpreadBet = spreadBet;
    }

    public String getTeamBetOn() {
        return teamBetOn;
    }

    public void setTeamBetOn(String teamBetOn) {
        this.teamBetOn = teamBetOn;
    }

    public Double getAmountToWin() {
        return amountToWin;
    }

    public void setAmountToWin(Double amountToWin) {
        this.amountToWin = amountToWin;
    }
    
}
