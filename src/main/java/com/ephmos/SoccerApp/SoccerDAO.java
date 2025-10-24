package com.ephmos.SoccerApp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface SoccerDAO {
    /**
     * Comprobar si el fichero se encuentra vacío.
     *
     * @return true si el fichero está vacío
     */
    Boolean isEmpty() throws FileNotFoundException, IOException;

    /**
     * Comprobar si el fichero está lleno.
     *
     * @return true si el fichero está lleno
     */
    Boolean isFull() throws FileNotFoundException, IOException;

    /**
     * Para añadir un jugador al fichero.
     *
     * @param name Jugador a añadir
     */
    void addPlayer(Player name);

    /**
     * Para eliminar un jugador del fichero.
     *
     * @param name Jugador a eliminar
     */
    void deletePlayer(Player name);

    /**
     * Para leer todos los jugadores del fichero.
     *
     * @return Lista con todos los jugadores
     */
    List<Player> readPlayers();

    /**
     * Para leer un jugador del fichero.
     *
     * @param name Nombre del jugador a leer
     * @return Jugador leído
     */
    List<Player> readPlayer(String name);

    /**
     * Para actualizar un jugador del fichero.
     *
     * @param player Jugador a actualizar
     */
    void updatePlayer(Player player);

    /**
     * Para añadir un equipo al fichero.
     *
     * @param team Equipo a añadir
     */
    void addTeam(Team team);

    /**
     * Para eliminar un equipo del fichero.
     *
     * @param team Equipo a eliminar
     */
    void deleteTeam(Team team);

    /**
     * Para leer todos los equipos del fichero.
     *
     * @return Lista con todos los equipos
     */
    List<Team> readTeams();

    /**
     * Para leer un equipo del fichero.
     *
     * @param name Equipo a leer
     * @return Equipo leído
     */
    List<Team> readTeam(String name);

    /**
     * Para actualizar un equipo del fichero.
     *
     * @param team Equipo a actualizar
     */
    void updateTeam(Team team);

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
    Player findTopScorer(Team team);

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
     * @return Devuelve la edad promedio de los jugadores en esa posición
     */
    double getAverageAge(Positions position);

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
     * Para exportar la información de un jugador a un fichero CSV.
     * @param players Lista de jugadores a exportar.
     */
    void exportPlayersToCSV(List<Player> players);

    /**
     * Para exportar la información de un equipo a un fichero CSV.
     * @param team Lista de equipos a exportar.
     */
    void exportTeamsToCSV(List<Team> team);
}
