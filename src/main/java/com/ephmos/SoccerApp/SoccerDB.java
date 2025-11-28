package com.ephmos.SoccerApp;

import com.ephmos.SoccerApp.dao.SoccerDAO;
import com.ephmos.SoccerApp.objects.Player;
import com.ephmos.SoccerApp.others.Positions;

import java.util.TreeSet;

public class SoccerDB implements SoccerDAO {
    @Override
    public void createPlayer(Player player) {
        
    }

    @Override
    public void deletePlayer(Player player) {

    }

    @Override
    public void updatePlayer(Player player) {

    }

    @Override
    public boolean playerExists(Player player) {
        return false;
    }

    @Override
    public TreeSet<Player> findPlayer(String name, String lastname, String team, int age, Positions position, int goalsNumber) {
        return null;
    }

    @Override
    public TreeSet<Player> readAllPlayers() {
        return null;
    }

    @Override
    public TreeSet<Player> findTopScorer() {
        return null;
    }

    @Override
    public TreeSet<Player> findTopScorer(String team) {
        return null;
    }

    @Override
    public TreeSet<Player> sortByName() {
        return null;
    }

    @Override
    public TreeSet<Player> sortByLastname() {
        return null;
    }

    @Override
    public TreeSet<Player> sortByAge(boolean ascending) {
        return null;
    }

    @Override
    public TreeSet<Player> sortByPosition() {
        return null;
    }

    @Override
    public TreeSet<Player> sortByGoals(boolean ascending) {
        return null;
    }

    @Override
    public TreeSet<Player> sortByTeam() {
        return null;
    }

    @Override
    public double getAverageAge() {
        return 0;
    }

    @Override
    public double getAverageGoals() {
        return 0;
    }
}
