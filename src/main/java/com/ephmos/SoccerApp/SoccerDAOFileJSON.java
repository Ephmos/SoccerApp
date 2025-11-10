package com.ephmos.SoccerApp;

import jakarta.json.bind.Jsonb;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public record SoccerDAOFileJSON(Jsonb jsonb, String file, List<Player> players) implements SoccerDAO {
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
            throw new IOException("Ha ocurrido una excepción de entrada y salida", exception);
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
            throw new SecurityException("Ha ocurrido una excepción de seguridad", exception);
        }
    }

    @Override
    public void addPlayer(Player player) throws Exception {
        if (!isFull()) {
            List<Player> players = readAllPlayers();
            if (!players.contains(player)) {
                players.add(player);
                try (FileWriter fileWriter = new FileWriter(this.file, StandardCharsets.UTF_8)) {
                    jsonb.toJson(players, fileWriter);
                } catch (IOException exception) {
                    throw new IOException("Error escribiendo jugadores al archivo", exception);
                }
            } else {
                System.out.println("El jugador que has intentado introducir ya existe.");
            }
        }
    }

    @Override
    public void deletePlayer(Player player) throws IOException {
        if (!isEmpty()) {
            List<Player> players = readAllPlayers();
            if (players.contains(player)) {
                players.remove(player);
                try (FileWriter fileWriter = new FileWriter(this.file, StandardCharsets.UTF_8)) {
                    jsonb.toJson(players, fileWriter);
                } catch (IOException exception) {
                    throw new IOException("Error escribiendo jugadores al archivo", exception);
                }
            } else {
                System.out.println("El jugador que has intentado eliminar no existe.");
            }
        }
    }

    @Override
    public List<Player> readAllPlayers() throws IOException {
        try (FileReader fileReader = new FileReader(this.file, StandardCharsets.UTF_8)) {
            Type listType = new ArrayList<Player>() {
            }.getClass().getGenericSuperclass();
            List<Player> players = jsonb.fromJson(fileReader, listType);
            return players != null ? players : new ArrayList<>();
        } catch (IOException exception) {
            throw new IOException("Error leyendo jugadores del archivo", exception);
        }
    }

    public List<Player> filterPlayersByName(String name) throws Exception {
        return filterReadancePlayers(player -> player.getName().equals(name));
    }

    public List<Player> filterPlayersByLastname(String lastname) throws Exception {
        return filterReadancePlayers(player -> player.getLastname().equals(lastname));
    }

    public List<Player> filterPlayersByPosition(Positions position) throws Exception {
        return filterReadancePlayers(player -> player.getPosition().equals(position));
    }

    public List<Player> filterPlayersByTeam(String team) throws Exception {
        return filterReadancePlayers(player -> player.getTeam().equals(team));
    }

    public List<Player> filterPlayersByAge(int age) throws Exception {
        return filterReadancePlayers(player -> player.getAge() == age);
    }

    public List<Player> filterPlayersByGoals(int goalsNumber) throws Exception {
        return filterReadancePlayers(player -> player.getGoalsNumber() == goalsNumber);
    }

    public List<Player> filterReadancePlayers(Predicate<Player> filter) throws IOException {
        try (FileReader fileReader = new FileReader(this.file, StandardCharsets.UTF_8)) {
            Player[] filteredPlayers = jsonb.fromJson(fileReader, Player[].class);
            if (Arrays.stream(filteredPlayers).noneMatch(filter)) {
                return null;
            } else {
                this.players.addAll(Arrays.stream(filteredPlayers).filter(filter).toList());
                return players;
            }
        } catch (IOException exception) {
            throw new IOException("Error leyendo jugadores del archivo", exception);
        }
    }

    @Override
    public void updatePlayer(Player player) {

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