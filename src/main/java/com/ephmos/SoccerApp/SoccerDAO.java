package com.ephmos.SoccerApp;

import java.util.List;

public interface SoccerDAO {
    /**
     * Comprobar si el fichero se encuentra vacío.
     *
     * @return true si el fichero está vacío
     */
    public Boolean isEmpty();

    /**
     * Comprobar si el fichero está lleno.
     *
     * @return true si el fichero está lleno
     */
    public Boolean isFull();

    /**
     * Para añadir un jugador al fichero.
     *
     * @param name Jugador a añadir
     */
    public void addPlayer(Player name);

    /**
     * Para eliminar un jugador del fichero.
     *
     * @param name Jugador a eliminar
     */
    public void deletePlayer(Player name);

    /**
     * Para leer todos los jugadores del fichero.
     *
     * @return Lista con todos los jugadores
     */
    public List<Player> readPlayers();

    /**
     * Para leer un jugador del fichero.
     *
     * @param name Nombre del jugador a leer
     * @return Jugador leído
     */
    public List<Player> readPlayer(String name);

    /**
     * Para actualizar un jugador del fichero.
     *
     * @param player Jugador a actualizar
     */
    public void updatePlayer(Player player);

    /**
     * Para añadir un equipo al fichero.
     *
     * @param team Equipo a añadir
     */
    public void addTeam(Team team);

    /**
     * Para eliminar un equipo del fichero.
     *
     * @param team Equipo a eliminar
     */
    public void deleteTeam(Team team);

    /**
     * Para leer todos los equipos del fichero.
     *
     * @return Lista con todos los equipos
     */
    public List<Team> readTeams();

    /**
     * Para leer un equipo del fichero.
     *
     * @param name Equipo a leer
     * @return Equipo leído
     */
    public List<Team> readTeam(String name);

    /**
     * Para actualizar un equipo del fichero.
     *
     * @param team Equipo a actualizar
     */
    public void updateTeam(Team team);

    /**
     * Para mostrar los máximos goleadores de la liga.
     *
     * @return Devuelve una lista con los máximos goleadores
     */
    public List<Player> findTopScorers();

    /**
     * Para mostrar el máximo goleador de un equipo en concreto.
     *
     * @param team Equipo a buscar
     * @return Devuelva el máximo goleador del equipo
     */
    public Player findTopScorer(Team team);

    /**
     * Para mostrar todos los jugadores en una posicion determinada.
     *
     * @param position Posición a buscar
     * @return Devuelve una lista con los jugadores en esa posición
     */
    public List<Player> findByPosition(Positions position);

    /**
     * Para obtener la edad promedio de todos los jugadores.
     *
     * @return Devuelve la edad promedio de todos los jugadores
     */
    public double getAverageAge();

    /**
     * Para contar el número de jugadores en una posición determinada.
     *
     * @param position Posición a buscar
     * @return Devuelve la edad promedio de los jugadores en esa posición
     */
    public double getAverageAge(Positions position);

    /**
     * Para ordenar los jugadores por apellidos.
     *
     * @return Lista de jugadores ordenada por apellidos
     */
    public List<Player> sortBySurname();

    /**
     * Para ordenar los jugadores por edad.
     *
     * @return Lista de jugadores ordenada por edad
     */
    public List<Player> sortByAge();

    /**
     * Para exportar la información de un jugador a un fichero CSV.
     * @param players Lista de jugadores a exportar.
     */
    public void exportPlayersToCSV(List<Player> players);

    /**
     * Para exportar la información de un equipo a un fichero CSV.
     * @param team Lista de equipos a exportar.
     */
    public void exportTeamsToCSV(List<Team> team);
}
