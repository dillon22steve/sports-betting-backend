package com.sports_betting.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sports_betting.app.model.bets.UserBet;
import com.sports_betting.app.model.bets.UserBetRequestBody;
import com.sports_betting.app.model.bets.UserBetResponseBody;
import com.sports_betting.app.service.UserBetService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@CrossOrigin(origins = "*")
public class UserBetController {

    private UserBetService userBetService;

    public UserBetController(UserBetService userBetService) {
        this.userBetService = userBetService;
    }

    @PostMapping("bets/place")
    public void placeBet(@RequestBody UserBetRequestBody requestBody) {
        System.out.println(requestBody.isSpreadBet());
        userBetService.placeBet(requestBody);
    }

    @GetMapping("bets")
    public List<UserBetResponseBody> getUserBetsById(@RequestParam String userId) {
        return userBetService.getUserBetsById(userId);
    }
    
    @DeleteMapping("bets/delete")
    public void deleteBet(@RequestParam String betId) {
        userBetService.deleteBet(betId);
    }

}
