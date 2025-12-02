package com.ephmos.SoccerApp.dao;

import com.ephmos.SoccerApp.objects.Player;
import com.ephmos.SoccerApp.others.Positions;

import java.util.TreeSet;

public interface SoccerDAO {
    //PlayerAlreadyExistsException
    //DataAccessException
    void createPlayer(Player player);
    //PlayerNotFoundException
    //DataAccessException
    void deletePlayer(Player player);
    //PlayerNotFoundException
    //DataAccessException
    void updatePlayer(Player player);
    //DataAccessException
    boolean playerExists(Player player);
    // Para localizar un jugador por los parámetros especificados, en caso de no especificar uno no se tendrá en cuenta.
    //DataAccessException
    TreeSet<Player> findPlayer(String name, String lastname, String team, int age, Positions position, int goalsNumber);
    //DataAccessException
    TreeSet<Player> readAllPlayers();
    //DataAccessException
    TreeSet<Player> findTopScorer();
    //DataAccessException
    TreeSet<Player> findTopScorer(String team);
    //DataAccessException
    TreeSet<Player> sortByName();
    //DataAccessException
    TreeSet<Player> sortByLastname();
    //DataAccessException
    TreeSet<Player> sortByAge(boolean ascending);
    //DataAccessException
    TreeSet<Player> sortByPosition();
    //DataAccessException
    TreeSet<Player> sortByGoals(boolean ascending);
    //DataAccessException
    TreeSet<Player> sortByTeam();
    //DataAccessException
    double getAverageAge();
    //DataAccessException
    double getAverageGoals();
}