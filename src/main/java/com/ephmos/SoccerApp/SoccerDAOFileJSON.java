package com.ephmos.SoccerApp;

import jakarta.json.bind.Jsonb;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
                return true;
            }

            final long MAX_FILE_SIZE = 100 * 1024 * 1024; // 100 MB

            return file.length() < MAX_FILE_SIZE;
        } catch (SecurityException exception) {
            throw new SecurityException("Ha ocurrido una excepción de seguridad", exception);
        }
    }

    @Override
    public void addPlayer(Player player) throws IOException {
        if (isFull()) {
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
            Type listType = new ArrayList<Player>(){}.getClass().getGenericSuperclass();
            List<Player> players = jsonb.fromJson(fileReader, listType);
            return players != null ? players : new ArrayList<>();
        } catch (IOException exception) {
            throw new IOException("Error leyendo jugadores del archivo", exception);
        }
    }

    public List<Player> filterPlayersByName(String name) throws IOException {
        return filterReadancePlayers(player -> player.getName().equals(name));
    }

    public List<Player> filterPlayersByLastname(String lastname) throws IOException {
        return filterReadancePlayers(player -> player.getLastname().equals(lastname));
    }

    public List<Player> filterPlayersByPosition(Positions position) throws IOException {
        return filterReadancePlayers(player -> player.getPosition().equals(position));
    }

    public List<Player> filterPlayersByTeam(String team) throws IOException {
        return filterReadancePlayers(player -> player.getTeam().equals(team));
    }

    public List<Player> filterPlayersByAge(int age) throws IOException {
        return filterReadancePlayers(player -> player.getAge() == age);
    }

    public List<Player> filterPlayersByGoals(int goalsNumber) throws IOException {
        return filterReadancePlayers(player -> player.getGoalsNumber() == goalsNumber);
    }

    public List<Player> filterReadancePlayers(Predicate<Player> filter) throws IOException {
        try (FileReader fileReader = new FileReader(this.file, StandardCharsets.UTF_8)) {
            Player[] filteredPlayers = jsonb.fromJson(fileReader, Player[].class);
            if (Arrays.stream(filteredPlayers).noneMatch(filter)) {
                System.out.println("No hay ningún jugador que coincida con el valor introducido igualado a ese atributo.");
                return List.of();
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
    public List<Player> findTopScorers() throws IOException {
        List<Player> players = readAllPlayers();
        Player topScorer = players.stream().max(Comparator.comparing(Player::getGoalsNumber)).orElseThrow(NoSuchElementException::new);;
        return List.of(topScorer);
    }

    @Override
    public List<Player> findTopScorer(String team) throws IOException {
        List<Player> players = readAllPlayers();
        Player topScorerInTeam = players.stream().filter(player -> player.getTeam().equals(team)).max(Comparator.comparing(Player::getGoalsNumber)).orElseThrow(NoSuchElementException::new);
        return List.of(topScorerInTeam);
    }

    @Override
    public List<Player> findByPosition(Positions position) throws IOException {
        List<Player> players = readAllPlayers();
        // return Math.toIntExact(players.stream().filter(player -> player.getPosition().equals(position)).count());
        return players;
    }

    @Override
    public double getAverageAge() throws IOException {
        List<Player> players = readAllPlayers();
        double agecounter = 0;
        for (Player player : players) {
            agecounter+=player.getAge();
        }
        return agecounter/players.size();
    }

    @Override
    public int getPlayersByPosition(Positions position) {
        return 0;
    }

    @Override
    public List<Player> sortByLastname() throws IOException {
        List<Player> players = readAllPlayers();
        players.sort(Comparator.comparing(Player::getLastname));
        return players;
    }

    @Override
    public List<Player> sortByAge() throws IOException {
        List<Player> players = readAllPlayers();
        players.sort(Comparator.comparing(Player::getAge));
        return players;
    }

    @Override
    public List<Player> sortByTeam(String team) throws IOException {
        List<Player> players = readAllPlayers();
        players.sort(Comparator.comparing(Player::getTeam));
        return players;
    }

    @Override
    public void exportPlayersToDataStorage(List<Player> players) {

    }
}