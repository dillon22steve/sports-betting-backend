package com.sports_betting.app.model.bets;

import org.hibernate.annotations.GenericGenerator;

import com.sports_betting.app.model.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class UserBet {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String gameId;

    private String teamBetOn;

    private Double amountBet;

    private Double amountToWin;

    private boolean isSpreadBet;

    private boolean isCompleted;

    private boolean isWin;

    private Double amountWonOrLost;



    public UserBet() {
    }

    public UserBet(String userId, String gameId, String teamBetOn, Double amountBet, Double amountToWin, boolean isSpreadBet) {
        this.user = new User();
        this.user.setId(userId);
        this.gameId = gameId;
        this.teamBetOn = teamBetOn;
        this.amountBet = amountBet;
        this.amountToWin = amountToWin;
        this.isSpreadBet = isSpreadBet;
        this.isCompleted = false;
        this.isWin = false;
        this.amountWonOrLost = 0.0;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void setSpreadBet(boolean isSpreadBet) {
        this.isSpreadBet = isSpreadBet;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean isWin) {
        this.isWin = isWin;
    }

    public Double getAmountWonOrLost() {
        return amountWonOrLost;
    }

    public void setAmountWonOrLost(Double amountWonOrLost) {
        this.amountWonOrLost = amountWonOrLost;
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
