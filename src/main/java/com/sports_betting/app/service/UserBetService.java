package com.sports_betting.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sports_betting.app.model.bets.UserBet;
import com.sports_betting.app.model.bets.UserBetRequestBody;
import com.sports_betting.app.model.bets.UserBetResponseBody;
import com.sports_betting.app.model.games.Game;
import com.sports_betting.app.model.user.User;
import com.sports_betting.app.repository.GameRepository;
import com.sports_betting.app.repository.UserBetRepository;
import com.sports_betting.app.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@Service
public class UserBetService {

    UserBetRepository betRepository;
    UserRepository userRepository;
    GameRepository gameRepository;

    public UserBetService(UserBetRepository betRepository, UserRepository userRepository, GameRepository gameRepository) {
        this.betRepository = betRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }


    @Transactional
    public UserBet placeBet(UserBetRequestBody requestBody) {


        User user = userRepository.findById(requestBody.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Game game = gameRepository.findById(requestBody.getGameId())
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));

        System.out.println(requestBody.isSpreadBet());

        UserBet bet = new UserBet(
            requestBody.getUserId(), 
            requestBody.getGameId(), 
            requestBody.getTeamBetOn(), 
            requestBody.getAmountBet(),
            requestBody.getAmountToWin(), 
            requestBody.isSpreadBet());

        if (user.getBalance() - bet.getAmountBet() > 0) {
            user.setBalance(user.getBalance() - bet.getAmountBet());
            userRepository.save(user);

            bet.setUser(user);
            return betRepository.save(bet);
        }

        throw new RuntimeException("Insufficient Funds");
    }


    public List<UserBetResponseBody> getUserBetsById(String userId) {
        List<UserBetResponseBody> responseBodies = new ArrayList<>();
        List<UserBet> userBets = betRepository.findByUserId(userId);
        for (UserBet bet : userBets) {
            try {
                Game game = gameRepository.findById(bet.getGameId())
                        .orElseThrow(() -> new EntityNotFoundException("Game not found"));
                UserBetResponseBody responseBody = new UserBetResponseBody(
                        bet.getId(),
                        game.getHomeTeam(),
                        game.getAwayTeam(),
                        bet.getTeamBetOn(),
                        game.getCommence_time().toString(),
                        bet.getTeamBetOn().equals(game.getHomeTeam()) ? game.getHome_team_spread() : game.getAway_team_spread(),
                        bet.getTeamBetOn().equals(game.getHomeTeam()) ? game.getHome_team_price() : game.getAway_team_price(),
                        bet.getAmountBet(),
                        bet.getAmountWonOrLost(),
                        bet.isCompleted(),
                        bet.isWin()
                );
                responseBodies.add(responseBody);
            } catch (EntityNotFoundException e) {
                // Skip bets where game is not found
                System.out.println("Skipping bet " + bet.getId() + " because game " + bet.getGameId() + " not found");
            }
        }
        return responseBodies;
    }


    public void deleteBet(String betId) {
        UserBet bet = betRepository.findById(betId)
                .orElseThrow(() -> new EntityNotFoundException("Bet not found"));

        if (!bet.isCompleted()) {
            User user = bet.getUser();
            user.setBalance(user.getBalance() + bet.getAmountBet());
            userRepository.save(user);
        }

        betRepository.delete(bet);
    }


    public void updateBetsByGameResults(List<Game> updatedGames) {
        List<UserBet> allBets = betRepository.findAll();
        Map<String, Game> gameMap = updatedGames.stream()
            .collect(Collectors.toMap(Game::getId, game -> game));
        
        for (UserBet bet : allBets) {
            if (bet.isCompleted()) {
                continue;
            }

            Game game = gameMap.get(bet.getGameId());
            
            if (game != null && game.isComplete()) {
                boolean won = determineWin(bet, game);
                bet.setWin(won);
                
                if (won) {
                    Double price = bet.isSpreadBet() ? 
                        (bet.getTeamBetOn().equalsIgnoreCase(game.getHomeTeam()) ? game.getHome_team_spread_price() : game.getAway_team_spread_price()) :
                        (bet.getTeamBetOn().equalsIgnoreCase(game.getHomeTeam()) ? game.getHome_team_price() : game.getAway_team_price());
                    double winnings = (bet.getAmountBet() * Math.abs(price)) / 100.0;
                    bet.setAmountWonOrLost(bet.getAmountBet() + winnings);  // Return stake + profit
                } else {
                    bet.setAmountWonOrLost(0.0);  // No change for losses (stake already subtracted)
                }

                bet.setCompleted(true);
                betRepository.save(bet);
                userRepository.findById(bet.getUser().getId()).ifPresent(user -> {
                    user.setBalance(user.getBalance() + bet.getAmountWonOrLost());
                    userRepository.save(user);
                });
            }
        }
    }

    private boolean determineWin(UserBet bet, Game game) {
        if (!bet.isSpreadBet()) {
            return evaluateH2H(bet, game);
        } else {
            return evaluateSpread(bet, game);
        }
    }

    private boolean evaluateH2H(UserBet bet, Game game) {
        if (game.getHomeTeam().equalsIgnoreCase(bet.getTeamBetOn())) {
            return game.getHome_team_score() > game.getAway_team_score();
        } else {
            return game.getAway_team_score() > game.getHome_team_score();
        }
    }

    private boolean evaluateSpread(UserBet bet, Game game) {
        if (game.getHomeTeam().equalsIgnoreCase(bet.getTeamBetOn())) {
            return (game.getHome_team_score() + game.getHome_team_spread()) > game.getAway_team_score();
        } else {
            return (game.getAway_team_score() + game.getAway_team_spread()) > game.getHome_team_score();
        }
    }

}
