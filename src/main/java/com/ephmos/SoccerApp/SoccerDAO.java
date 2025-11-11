package com.ephmos.SoccerApp;

import java.io.IOException;
import java.util.List;

public interface SoccerDAO {
    /**
     * Comprobar si el fichero está vacío.
     *
     * @return si el fichero está vacío
     * @throws IOException error al realizar la comprobación del fichero.
     */
    boolean isEmpty() throws IOException;

    /**
     * Comprobar si el fichero está lleno.
     *
     * @return si el fichero está lleno
     * @throws IOException error al realizar la comprobación del fichero.
     */
    boolean isFull() throws IOException;

    /**
     * Para añadir un jugador al fichero.
     *
     * @param player Jugador a añadir
     * @throws IOException error al añadir el jugador al fichero.
     */
    void addPlayer(Player player) throws IOException;

    /**
     * Para eliminar un jugador del fichero.
     *
     * @param player Jugador a eliminar
     * @throws IOException error al eliminar el jugador del fichero.
     */
    void deletePlayer(Player player) throws IOException;

    /**
     * Para leer todos los jugadores del fichero.
     *
     * @return Lista con todos los jugadores
     * @throws IOException error al leer el fichero.
     */
    List<Player> readAllPlayers() throws IOException;

    /**
     * Para actualizar un jugador del fichero.
     *
     * @param player Jugador a actualizar
     * @throws IOException error al actualizar el jugador en el fichero.
     */
    void updatePlayer(Player player) throws IOException;

    /**
     * Para mostrar los máximos goleadores de la liga.
     *
     * @return Devuelve una lista con los máximos goleadores de la liga
     */
    List<Player> findTopScorers() throws IOException;

    /**
     * Para mostrar al máximo goleador de un equipo en concreto.
     *
     * @param team Equipo a buscar
     * @return Devuelva al jugador con la mayor cantidad de goles perteneciente al equipo seleccionado
     */
    List<Player> findTopScorer(String team) throws IOException;

    /**
     * Para mostrar todos los jugadores en una posicion determinada.
     *
     * @param position Posición a buscar
     * @return Devuelve una lista de jugadores en esa posición
     */
    List<Player> findByPosition(Positions position) throws IOException;

    /**
     * Para obtener la edad promedio de todos los jugadores.
     *
     * @return Devuelve la edad promedio de todos los jugadores
     */
    double getAverageAge() throws IOException;

    /**
     * Para contar el número de jugadores en una posición determinada.
     *
     * @param position Posición a buscar
     * @return Devuelve una lista de jugadores en una posicion determinada
     */
    int getPlayersByPosition(Positions position) throws IOException;

    /**
     * Para ordenar los jugadores por apellidos.
     *
     * @return Lista de jugadores ordenada por apellidos
     */
    List<Player> sortByLastname() throws IOException;

    /**
     * Para ordenar los jugadores por edad.
     *
     * @return Lista de jugadores ordenada por edad
     */
    List<Player> sortByAge() throws IOException;

    /**
     * Para obtener todos los jugadores de un equipo
     *
     * @param team Equipo
     * @return Lista de jugadores de un equipo
     */
    List<Player> sortByTeam(String team) throws IOException;

    /**
     * Para exportar la información de los jugadores a los ficheros de almacenamiento.
     *
     * @param players Lista de jugadores a exportar.
     */
    void exportPlayersToDataStorage(List<Player> players);
}