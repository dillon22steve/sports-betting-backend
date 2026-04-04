package com.sports_betting.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;

import com.sports_betting.app.model.games.Game;
import com.sports_betting.app.model.games.odds_api.Bookmaker;
import com.sports_betting.app.model.games.odds_api.Event;
import com.sports_betting.app.model.games.odds_api.Market;
import com.sports_betting.app.model.games.odds_api.Outcome;
import com.sports_betting.app.model.games.results_api.GameResult;
import com.sports_betting.app.model.games.results_api.Score;
import com.sports_betting.app.repository.GameRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class GameOddsService {

    private WebClient client = WebClient.create();

    private GameRepository gameRepository;

    private final String API_BASE_URL = "https://api.the-odds-api.com/v4/sports/";

    private final String API_KEY = "36116c626d8f31cec63424d2ae3b6cce";

    public GameOddsService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    public List<Game> storeGameOdds(String sport) {
        // Logic to fetch game odds from the database or an external API
        List<Event> events = client.get()
            .uri(API_BASE_URL + sport + "/odds/?apiKey=" + API_KEY + "&regions=us&bookmakers=draftkings&markets=h2h,spreads&oddsFormat=american")
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
        game.setComplete(false);

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

    public Mono<List<Game>> updateGameByGameResults(String sport) {
        System.out.println("Updating game results for sport: " + sport);
    
    // 1. Fetch your existing local games (Assuming this is a standard List)
        List<Game> gamesToUpdate = getGameOdds(sport);

        // 2. Fetch the API results and turn them into a Map reactively
        return client.get()
            .uri(API_BASE_URL + sport + "/scores/?daysFrom=3&apiKey=" + API_KEY)
            .retrieve()
            .bodyToFlux(GameResult.class)
            // Convert the Flux into a Mono<Map<String, GameResult>>
            .collectMap(GameResult::getId, Function.identity())
            .map(resultMap -> {
                // 3. Now that we have the map, update our list
                gamesToUpdate.stream()
                    .filter(game -> resultMap.containsKey(game.getId()))
                    .forEach(game -> {
                        GameResult result = resultMap.get(game.getId());
                        System.out.println("Updating game: " + game.getId() + " with results from API");
                        if (result.getScores() != null && result.getScores().length >= 2) {
                            for (Score s : result.getScores()) {
                                if (s.getName() != null && s.getName().equalsIgnoreCase(game.getHomeTeam())) {
                                    System.out.println("Setting home team score for game " + game.getId() + ": " + s.getScore());
                                    game.setHome_team_score(s.getScore());
                                } else if (s.getName() != null && s.getName().equalsIgnoreCase(game.getAwayTeam())) {
                                    System.out.println("Setting away team score for game " + game.getId() + ": " + s.getScore());
                                    game.setAway_team_score(s.getScore());
                                }
                            }
                            game.setComplete(true);
                            gameRepository.save(game);
                        }
                    });
                return gamesToUpdate;
            });
    }
    
}
