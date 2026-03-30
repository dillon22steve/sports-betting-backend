package com.sports_betting.app.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sports_betting.app.model.games.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {

    List<Game> findBySportKey(String sport_key);
    
}
