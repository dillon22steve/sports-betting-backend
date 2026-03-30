package com.sports_betting.app.model.bets;

public class UserBetResponseBody {

    private String id;
    private String homeTeam;
    private String awayTeam;
    private String teamBetOn;
    private String commenceTime;
    private Double spread;
    private int point;
    private Double amountBet;
    private Double amountWonOrLost;
    private boolean completed;
    private boolean isWon;



    public UserBetResponseBody() {

    }

    

    public UserBetResponseBody(String id, String homeTeam, String awayTeam, String teamBetOn, String commenceTime, Double spread, int point,
            Double amountBet, Double amountWonOrLost, boolean completed, boolean isWon) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.teamBetOn = teamBetOn;
        this.commenceTime = commenceTime;
        this.spread = spread;
        this.point = point;
        this.amountBet = amountBet;
        this.amountWonOrLost = amountWonOrLost;
        this.completed = completed;
        this.isWon = isWon;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    

    public String getCommenceTime() {
        return commenceTime;
    }

    public void setCommenceTime(String commenceTime) {
        this.commenceTime = commenceTime;
    }

    public Double getSpread() {
        return spread;
    }

    public void setSpread(Double spread) {
        this.spread = spread;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Double getAmountBet() {
        return amountBet;
    }

    public void setAmountBet(Double amountBet) {
        this.amountBet = amountBet;
    }

    public Double getAmountWonOrLost() {
        return amountWonOrLost;
    }

    public void setAmountWonOrLost(Double amountWonOrLost) {
        this.amountWonOrLost = amountWonOrLost;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean isWon) {
        this.isWon = isWon;
    }

    public String getTeamBetOn() {
        return teamBetOn;
    }

    public void setTeamBetOn(String teamBetOn) {
        this.teamBetOn = teamBetOn;
    }
    
}
