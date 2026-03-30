package com.sports_betting.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sports_betting.app.model.bets.UserBet;


@Repository
public interface UserBetRepository extends JpaRepository<UserBet, String> {

    List<UserBet> findByUserId(String userId);
    
}
