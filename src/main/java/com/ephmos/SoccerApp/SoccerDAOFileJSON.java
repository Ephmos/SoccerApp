package com.ephmos.SoccerApp;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class SoccerDAOFileJSON implements SoccerDAO {
    private final String file;
    private final List<Player> players;
    public SoccerDAOFileJSON(String file, List<Player> players) {
        this.file = file;
        this.players = players;
    }

    @Override
    public boolean isEmpty() throws IOException {
        try {
            File file = new File(this.file);
            if (!file.exists()) {
                return true;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(this.file))) {
                if (br.readLine() == null) {
                    return true;
                }
            }

            return false;
        } catch (IOException exception) {
            throw new IOException("I/O Exception has been occurred", exception);
        }
    }

    @Override
    public boolean isFull() throws SecurityException {
        try {
            File file = new File(this.file);
            if (!file.exists()) {
                return false;
            }

            final long MAX_FILE_SIZE = 100 * 1024 * 1024; // 100 MB

            return file.length() >= MAX_FILE_SIZE;
        } catch (SecurityException exception) {
            throw new SecurityException("SecurityException has been occurred", exception);
        }
    }

    @Override
    public void addPlayer(Player name) {

    }

    @Override
    public void deletePlayer(Player name) {

    }

    @Override
    public List<Player> readPlayers() throws Exception {
        try (Jsonb jsonb = JsonbBuilder.create(); FileReader fileReader = new FileReader(this.file)) {
            Player[] players = jsonb.fromJson(fileReader, Player[].class);
            this.players.addAll(Arrays.asList(players));
        } catch (IOException exception) {
            throw new IOException("Error leyendo jugadores del archivo", exception);
        } catch (Exception exception) {
            throw new Exception("Ha ocurrido un error al intentar recuperar los recursos asignados a uno de los elementos", exception);
        }

        return this.players;
    }

    @Override
    public Player readPlayer(String name) throws Exception {
        try (Jsonb json = JsonbBuilder.create(); FileReader fileReader = new FileReader(this.file)) {
            Player[] players = json.fromJson(fileReader, Player[].class);
            for (Player player : players) {
                if (player.getName().equals(name)) {
                    return player;
                }
            }
        }
        return null;
    }

    @Override
    public void updatePlayer(Player player) throws IOException {

    }

    @Override
    public List<Player> findTopScorers() {
        return List.of();
    }

    @Override
    public List<Player> findTopScorer(String team) {
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
    public List<Player> getPlayersByPosition(Positions position) {
        return List.of();
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
