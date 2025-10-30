package com.ephmos.SoccerApp;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class SoccerDAOFileJSON implements SoccerDAO{
    @Override
    public Boolean isEmpty() {
        return null;
    }

    @Override
    public Boolean isFull() {
        return null;
    }

    @Override
    public void addPlayer(Player name) {

    }

    @Override
    public void deletePlayer(Player name) {

    }

    @Override
    public List<Player> readPlayers() {
        Jsonb jsonb = JsonbBuilder.create();
        /*try (FileReader fileReader = new FileReader("jugadores.json")) {

        }*/
        return List.of();
    }

    @Override
    public List<Player> readPlayer(String name) throws IOException {
        return List.of();
    }

    @Override
    public void updatePlayer(Player player) throws IOException {

    }

    @Override
    public List<Player> findTopScorers() {
        return List.of();
    }

    @Override
    public Player findTopScorer(String team) {
        return null;
    }

    @Override
    public List<Player> findByPosition(Positions position) {
        return List.of();
    }

    @Override
    public double getAverageAge() {
        return 0;
    }

    @Override
    public double getAverageAge(Positions position) {
        return 0;
    }

    @Override
    public List<Player> sortBySurname() {
        return List.of();
    }

    @Override
    public List<Player> sortByAge() {
        return List.of();
    }

    @Override
    public List<Player> sortByTeam(String team) {
        return List.of();
    }

    @Override
    public void exportPlayersToDataStorage(List<Player> players) {

    }
}
