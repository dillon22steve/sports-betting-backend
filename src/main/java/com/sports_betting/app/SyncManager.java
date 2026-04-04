package com.sports_betting.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.sports_betting.app.model.bets.UserBet;
import com.sports_betting.app.model.games.Game;
import com.sports_betting.app.model.metadata.SystemMetadata;
import com.sports_betting.app.repository.MetadataRepository;
import com.sports_betting.app.service.GameOddsService;
import com.sports_betting.app.service.UserBetService;

import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SyncManager implements ApplicationRunner {

    @Autowired
    private MetadataRepository repository;

    @Autowired
    private GameOddsService gameOddsService;

    @Autowired
    private UserBetService userBetService;

    private static final String GAME_ODDS_SYNC_KEY = "GAME_ODDS_SYNC";
    private static final String GAME_RESULTS_SYNC_KEY = "GAME_RESULTS_SYNC";
    
    private static final String[] SPORTS = {"americanfootball_nfl", "americanfootball_ncaaf", "basketball_nba", "basketball_ncaab", "baseball_mlb", "icehockey_nhl"};

    @Override
    public void run(ApplicationArguments args) {
        // 1. Fetch the last sync time from the DB
        LocalDateTime lastGameOddsSync = repository.findById(GAME_ODDS_SYNC_KEY)
                .map(SystemMetadata::getConfigValue)
                .orElse(null);

        // 2. Check if we need to sync (if null or older than 24 hours)
        if (lastGameOddsSync == null || lastGameOddsSync.isBefore(LocalDateTime.now().minusHours(24))) {
            System.out.println("Starting scheduled sports data sync...");
            
            try {
                
                for (String sport : SPORTS) {
                    System.out.println("Syncing data for sport: " + sport);
                    gameOddsService.storeGameOdds(sport);
                }
                
                // 4. Update the timestamp so we don't run it again for 24 hours
                repository.updateTimestamp(GAME_ODDS_SYNC_KEY, LocalDateTime.now());
                System.out.println("Sync completed and timestamp updated.");
                
            } catch (Exception e) {
                System.err.println("Sync failed: " + e.getMessage());
                // We don't update the timestamp here so it tries again on next restart
            }
        } else {
            System.out.println("Data is up to date. Last sync was: " + lastGameOddsSync);
        }



        LocalDateTime lastGameResultsSync = repository.findById(GAME_RESULTS_SYNC_KEY)
                .map(SystemMetadata::getConfigValue)
                .orElse(null);

        if (lastGameResultsSync == null || lastGameResultsSync.isBefore(LocalDateTime.now().minusHours(6))) {
            Flux.fromArray(SPORTS)
                .flatMap(sport -> gameOddsService.updateGameByGameResults(sport)) // Fires requests in parallel
                .doOnNext(updatedGames -> userBetService.updateBetsByGameResults(updatedGames))
                .subscribe(
                    result -> {
                        if (result != null && !result.isEmpty()) {
                            System.out.println("Updated game results and user bets for sport: " + result.get(0).getSportKey());
                        } else {
                            System.out.println("Updated game results and user bets for a sport with no games.");
                        }
                    },
                    error -> {
                        System.err.println("Failed to sync: " + error.getMessage());
                    },
                    () -> {
                        repository.updateTimestamp(GAME_RESULTS_SYNC_KEY, LocalDateTime.now());
                        System.out.println("All sports synced successfully!");
                    }
                );
        } else {
            System.out.println("Game results are up to date. Last sync was: " + lastGameResultsSync);
        }
    }
}
