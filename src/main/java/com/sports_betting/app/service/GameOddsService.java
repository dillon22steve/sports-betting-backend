package com.sports_betting.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;

import com.sports_betting.app.model.games.Bookmaker;
import com.sports_betting.app.model.games.Event;
import com.sports_betting.app.model.games.Game;
import com.sports_betting.app.model.games.Market;
import com.sports_betting.app.model.games.Outcome;
import com.sports_betting.app.repository.GameRepository;


@Service
public class GameOddsService {

    private WebClient client = WebClient.create();

    private GameRepository gameRepository;

    public GameOddsService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    public List<Game> storeGameOdds(String sport) {
        // Logic to fetch game odds from the database or an external API
        List<Event> events = client.get()
            .uri("https://api.the-odds-api.com/v4/sports/" + sport + "/odds/?apiKey=36116c626d8f31cec63424d2ae3b6cce&regions=us&bookmakers=draftkings&markets=h2h,spreads&oddsFormat=american")
            .retrieve()
            .bodyToFlux(Event.class)
            .collectList()
            .block();

        List<Game> games = events.stream()
            .map(this::mapEventToGame)
            .toList();
        
        return gameRepository.saveAll(games);
    }

    private Game mapEventToGame(Event event) {

        Game game = new Game();

        game.setId(event.getId());
        game.setSportKey(event.getSportKey());
        game.setCommence_time(event.getCommenceTime());
        game.setHomeTeam(event.getHomeTeam());
        game.setAwayTeam(event.getAwayTeam());

        if (event.getBookmakers() == null || event.getBookmakers().isEmpty()) {
            return game;
        }

        // Assuming you only care about DraftKings (first one)
        Bookmaker bookmaker = event.getBookmakers().get(0);
        game.setBookmaker_name(bookmaker.getTitle());

        if (bookmaker.getMarkets() == null) {
            return game;
        }

        for (Market market : bookmaker.getMarkets()) {

            switch (market.getKey()) {

                case "h2h":
                    mapMoneyline(game, market, event);
                    break;

                case "spreads":
                    mapSpreads(game, market, event);
                    break;
            }
        }

        game.setCreatedAt(java.time.LocalDateTime.now());

        return game;
    }

    private void mapMoneyline(Game game, Market market, Event event) {
        for (Outcome outcome : market.getOutcomes()) {

            if (outcome.getName().equalsIgnoreCase(event.getHomeTeam())) {
                game.setHome_team_price(outcome.getPrice());
            }

            if (outcome.getName().equalsIgnoreCase(event.getAwayTeam())) {
                game.setAway_team_price(outcome.getPrice());
            }
        }
    }

    private void mapSpreads(Game game, Market market, Event event) {
        for (Outcome outcome : market.getOutcomes()) {

            if (outcome.getName().equalsIgnoreCase(event.getHomeTeam())) {
                game.setHome_team_spread(outcome.getPoint() != null ? outcome.getPoint() : 0);
                game.setHome_team_spread_price((double) outcome.getPrice());
            }

            if (outcome.getName().equalsIgnoreCase(event.getAwayTeam())) {
                game.setAway_team_spread(outcome.getPoint() != null ? outcome.getPoint() : 0);
                game.setAway_team_spread_price((double) outcome.getPrice());
            }
        }
    }

    public List<Game> getGameOdds(String sport) {
        return gameRepository.findBySportKey(sport);
    }
    
}
