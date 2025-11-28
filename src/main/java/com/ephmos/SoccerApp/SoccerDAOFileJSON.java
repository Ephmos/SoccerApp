package com.ephmos.SoccerApp;

import com.ephmos.SoccerApp.dao.SoccerDAO;
import com.ephmos.SoccerApp.objects.Player;
import com.ephmos.SoccerApp.others.Positions;
import jakarta.json.bind.Jsonb;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;

public record SoccerDAOFileJSON(Jsonb jsonb, String file, List<Player> players) implements SoccerDAO {
    public boolean isEmpty() throws IOException {
        try {
            File file = new File(this.file);
            if (!file.exists()) {
                return true;
            }

            // Creamos el buffer y comenzamos a leer.
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

    public boolean isFull() throws SecurityException {
        try {
            File file = new File(this.file);
            if (!file.exists()) {
                return true;
            }

            // Establecemos cuál será el máximo del fichero
            final long MAX_FILE_SIZE = 10 * 1024;

            return file.length() < MAX_FILE_SIZE;
        } catch (SecurityException exception) {
            throw new SecurityException("Ha ocurrido una excepción de seguridad", exception);
        }
    }

    @Override
    public void addPlayer(Player player) {
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
    public void deletePlayer(Player player) {
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
    public List<Player> readAllPlayers() {
        try (FileReader fileReader = new FileReader(this.file, StandardCharsets.UTF_8)) {
            /* Aquí lo que hacemos es recalcarle a Jsonb que lo que queremos deserializar es una lista de tipo Player,
            no sabría que cada elemento de la lista debe ser un elemento tipo Player de no ser por esto.*/

            Type listType = new ArrayList<Player>(){}.getClass().getGenericSuperclass();
            List<Player> players = jsonb.fromJson(fileReader, listType);
            return players != null ? players : new ArrayList<>();
        } catch (IOException exception) {
            throw new IOException("Error leyendo jugadores del archivo", exception);
        }
    }

    // Cada uno de estos métodos hace una llamada al de abajo
    public List<Player> filterPlayersByName(String name) {
        return filterReadancePlayers(player -> player.getName().equals(name));
    }

    public List<Player> filterPlayersByLastname(String lastname) {
        return filterReadancePlayers(player -> player.getLastname().equals(lastname));
    }

    public List<Player> filterPlayersByPosition(Positions position) {
        return filterReadancePlayers(player -> player.getPosition().equals(position));
    }

    public List<Player> filterPlayersByTeam(String team) {
        return filterReadancePlayers(player -> player.getTeam().equals(team));
    }

    public List<Player> filterPlayersByAge(int age) {
        return filterReadancePlayers(player -> player.getAge() == age);
    }

    public List<Player> filterPlayersByGoals(int goalsNumber) {
        return filterReadancePlayers(player -> player.getGoalsNumber() == goalsNumber);
    }

    public List<Player> filterReadancePlayers(Predicate<Player> filter) {
        try (FileReader fileReader = new FileReader(this.file)) {
            Type listType = new ArrayList<Player>(){}.getClass().getGenericSuperclass();
            List<Player> allPlayers = jsonb.fromJson(fileReader, listType);

            /* Aplico el filtro deseado para que me devuelva una secuencia de elementos, en este caso ya procesados por el filtro de cualquiera
            de los métodos de arriba, es decir, es una lista transformada. */
            List<Player> filteredPlayers = allPlayers.stream().filter(filter).toList();

            if (filteredPlayers.isEmpty()) {
                System.out.println("No hay ningún jugador que coincida con el valor introducido.");
                return List.of();
            }

            return filteredPlayers;
        } catch (IOException exception) {
            throw new IOException("Error leyendo jugadores del archivo", exception);
        }
    }

    @Override
    public void updatePlayer(Player updatePlayer) {
        List<Player> players = readAllPlayers();
        for (Player player : players) {
            // Busco jugadores que coincidan en nombre y apellido.
            if (player.getName().equals(updatePlayer.getName()) && player.getLastname().equals(updatePlayer.getLastname())) {
                player.setAge(updatePlayer.getAge());
                player.setTeam(updatePlayer.getTeam());
                player.setPosition(updatePlayer.getPosition());
                player.setGoalsNumber(updatePlayer.getGoalsNumber());
            }
        }
        try (FileWriter fileWriter = new FileWriter(this.file, StandardCharsets.UTF_8)) {
            jsonb.toJson(players, fileWriter);
        } catch (IOException exception) {
            throw new IOException("Error escribiendo jugadores al archivo", exception);
        }
    }

    @Override
    public List<Player> findTopScorers() {
        List<Player> players = readAllPlayers();
        /* Realizamos una filtración, comparando el número de goles, en este caso sólo dejaremos aquellos elementos que tengan el máximo, si no encontramos ningún elemento
           el programa lanzará una excepción */
        Player topScorer = players.stream().max(Comparator.comparing(Player::getGoalsNumber)).orElseThrow(NoSuchElementException::new);
        return List.of(topScorer);
    }

    @Override
    public List<Player> findTopScorer(String team) {
        List<Player> players = readAllPlayers();
        // Realizamos lo mismo que en el bloque anterior, pero en este caso con los jugadores de un equipo en concreto
        Player topScorerInTeam = players.stream().filter(player -> player.getTeam().equals(team)).max(Comparator.comparing(Player::getGoalsNumber)).orElseThrow(NoSuchElementException::new);
        return List.of(topScorerInTeam);
    }

    @Override
    public List<Player> findByPosition(Positions position) {
        List<Player> players = readAllPlayers();
        return players.stream().filter(player -> player.getPosition().equals(position)).toList();
    }

    @Override
    public double getAverageAge() {
        List<Player> players = readAllPlayers();
        // Sacamos la media, sumando todas las edades y dividiéndola por la cantidad de jugadores que hay en la liga.
        double agecounter = 0;
        for (Player player : players) {
            agecounter+=player.getAge();
        }
        return agecounter/players.size();
    }

    @Override
    public int getPlayersByPosition(Positions position) {
        List<Player> players = readAllPlayers();
        return Math.toIntExact(players.stream().filter(player -> player.getPosition().equals(position)).count());
    }

    @Override
    public void sortByLastname() {
        List<Player> players = readAllPlayers();
        players.sort(Comparator.comparing(Player::getLastname));
    }

    @Override
    public void sortByAge() {
        List<Player> players = readAllPlayers();
        players.sort(Comparator.comparing(Player::getAge));
    }

    @Override
    public void sortByTeam() {
        List<Player> players = readAllPlayers();
        players.sort(Comparator.comparing(Player::getTeam));
    }
}