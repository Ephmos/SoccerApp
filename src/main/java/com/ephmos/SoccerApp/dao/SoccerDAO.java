package com.ephmos.SoccerApp.dao;

import com.ephmos.SoccerApp.objects.Player;
import com.ephmos.SoccerApp.others.Positions;

import java.util.TreeSet;

public interface SoccerDAO {
    void createPlayer(Player player);
    void deletePlayer(Player player);
    void updatePlayer(Player player);
    boolean playerExists(Player player);
    // Para localizar un jugador por los parámetros especificados, en caso de no especificar uno no se tendrá en cuenta.
    TreeSet<Player> findPlayer(String name, String lastname, String team, int age, Positions position, int goalsNumber);
    TreeSet<Player> readAllPlayers();
    TreeSet<Player> findTopScorer();
    TreeSet<Player> findTopScorer(String team);
    TreeSet<Player> sortByName();
    TreeSet<Player> sortByLastname();
    TreeSet<Player> sortByAge(boolean ascending);
    TreeSet<Player> sortByPosition();
    TreeSet<Player> sortByGoals(boolean ascending);
    TreeSet<Player> sortByTeam();
    double getAverageAge();
    double getAverageGoals();
}