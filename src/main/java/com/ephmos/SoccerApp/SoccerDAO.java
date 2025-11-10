package com.ephmos.SoccerApp;

import javax.swing.text.Position;
import java.io.IOException;
import java.util.List;

public interface SoccerDAO {
    /**
     * Comprobar si el fichero se encuentra vacío.
     *
     * @return true si el fichero está vacío
     */
    boolean isEmpty() throws IOException;

    /**
     * Comprobar si el fichero está lleno.
     *
     * @return true si el fichero está lleno
     */
    boolean isFull() throws IOException;

    /**
     * Para añadir un jugador al fichero.
     *
     * @param player Jugador a añadir
     */
    void addPlayer(Player player) throws Exception;

    /**
     * Para eliminar un jugador del fichero.
     *
     * @param player Jugador a eliminar
     */
    void deletePlayer(Player player) throws Exception;

    /**
     * Para leer todos los jugadores del fichero.
     *
     * @return Lista con todos los jugadores
     */
    List<Player> readAllPlayers() throws Exception;

    List<Player> filterPlayersByName(String name) throws Exception;
    List<Player> filterPlayersByLastname(String lastname) throws Exception;
    List<Player> filterPlayersByPosition(Positions position) throws Exception;
    List<Player> filterPlayersByTeam(String team) throws Exception;
    List<Player> filterPlayersByAge(int age) throws Exception;
    List<Player> filterPlayersByGoals(int goals) throws Exception;

    /**
     * Para actualizar un jugador del fichero.
     *
     * @param player Jugador a actualizar
     */
    void updatePlayer(Player player) throws IOException;

    /**
     * Para mostrar los máximos goleadores de la liga.
     *
     * @return Devuelve una lista con los máximos goleadores
     */
    List<Player> findTopScorers();

    /**
     * Para mostrar el máximo goleador de un equipo en concreto.
     *
     * @param team Equipo a buscar
     * @return Devuelva el máximo goleador del equipo
     */
    List<Player> findTopScorer(String team);

    /**
     * Para mostrar todos los jugadores en una posicion determinada.
     *
     * @param position Posición a buscar
     * @return Devuelve una lista con los jugadores en esa posición
     */
    List<Player> findByPosition(Positions position);

    /**
     * Para obtener la edad promedio de todos los jugadores.
     *
     * @return Devuelve la edad promedio de todos los jugadores
     */
    double getAverageAge();

    /**
     * Para contar el número de jugadores en una posición determinada.
     *
     * @param position Posición a buscar
     * @return Devuelve una lista de jugadores en una posicion determinada
     */
    List<Player> getPlayersByPosition(Positions position);

    /**
     * Para ordenar los jugadores por apellidos.
     *
     * @return Lista de jugadores ordenada por apellidos
     */
    List<Player> sortBySurname();

    /**
     * Para ordenar los jugadores por edad.
     *
     * @return Lista de jugadores ordenada por edad
     */
    List<Player> sortByAge();

    /**
     * Para obtener todos los jugadores de un equipo
     *
     * @return Lista de jugadores de un equipo
     * @param team Equipo
     */
    List<Player> sortByTeam(String team);

    /**
     * Para exportar la información de los jugadores a los ficheros de almacenamiento.
     * @param players Lista de jugadores a exportar.
     */
    void exportPlayersToDataStorage(List<Player> players);
}