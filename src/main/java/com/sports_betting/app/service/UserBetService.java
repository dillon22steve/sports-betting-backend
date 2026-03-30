package com.sports_betting.app.service;

import java.util.ArrayList;
import java.util.List;

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
}
