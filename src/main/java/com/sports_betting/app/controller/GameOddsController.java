package com.sports_betting.app.controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.ArrayList;

import com.sports_betting.app.model.games.*;
import com.sports_betting.app.service.GameOddsService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@CrossOrigin(origins = "*")
public class GameOddsController {

    private GameOddsService gameOddsService;

    public GameOddsController(GameOddsService gameOddsService) {
        this.gameOddsService = gameOddsService;
    }

    @GetMapping("/odds")
    public List<Game> getGameOdds(@RequestParam String sport) {
        boolean store = false;
        if (store) {
            return gameOddsService.storeGameOdds(sport);
        } else {
            return gameOddsService.getGameOdds(sport);
        }
    }

}
