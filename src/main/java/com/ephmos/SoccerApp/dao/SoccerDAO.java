package com.ephmos.SoccerApp.dao;

import com.ephmos.SoccerApp.objects.Player;
import com.ephmos.SoccerApp.others.Positions;

import java.util.TreeSet;

public interface SoccerDAO {
    void createPlayer(Player player);
    void removePlayer(Player player);
    void updatePlayer(Player player);
    boolean playerExists(Player player);
    TreeSet<Player> readAllPlayers();
    TreeSet<Player> findTopScorers();
    TreeSet<Player> findTopScorer(String team);
    TreeSet<Player> findByPosition(Positions position);
    double getAverageAge();
    int getPlayersByPosition(Positions position);
    void sortByLastname();
    void sortByAge();
    void sortByTeam();
}