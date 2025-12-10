package com.ephmos.SoccerApp.dao;

import com.ephmos.SoccerApp.exceptions.DataAccessException;
import com.ephmos.SoccerApp.exceptions.PlayerAlreadyExistsException;
import com.ephmos.SoccerApp.exceptions.PlayerNotFoundException;
import com.ephmos.SoccerApp.objects.Player;
import com.ephmos.SoccerApp.others.Positions;

import java.util.List;
import java.util.TreeSet;

public interface SoccerDAO {
    /**
     * Inserción de jugador.
     * @param player El jugador que se desea insertar.
     * @throws PlayerAlreadyExistsException El jugador que se intenta insertar ya existe.
     * @throws DataAccessException No es posible conectarse a la fuente de datos.
     */
    void createPlayer(Player player) throws PlayerAlreadyExistsException, DataAccessException;
    /**
     *
     * @param player El jugador que se desea eliminar.
     * @throws PlayerNotFoundException El jugador que se intenta eliminar no existe.
     * @throws DataAccessException No es posible conectarse a la fuente de datos.
     */
    void deletePlayer(Player player) throws PlayerNotFoundException, DataAccessException;
    long getPlayerId(String name, String lastname, int age) throws DataAccessException;
    void updatePlayer(Player player, int newGoalsNumber, String newTeam) throws PlayerNotFoundException, DataAccessException;
    /**
     * Comprobar la existencia de un jugador.
     * @param player El jugador que se desea localizar.
     * @return Si el jugador existe.
     * @throws DataAccessException No es posible conectarse a la fuente de datos.
     */
    boolean playerExists(Player player) throws DataAccessException;
    long getTeamId(String name) throws DataAccessException;
    // Para localizar un jugador por los parámetros especificados, en caso de no especificar uno no se tendrá en cuenta.
    //DataAccessException
    TreeSet<Player> findPlayer(String name, String lastname, String team, int age, Positions position, int goalsNumber);
    //DataAccessException
    List<Player> readAllPlayers() throws DataAccessException;
    //DataAccessException
    TreeSet<Player> findTopScorer();
    //DataAccessException
    TreeSet<Player> findTopScorer(String team);
    //DataAccessException
    TreeSet<Player> sortByName();
    //DataAccessException
    TreeSet<Player> sortByLastname();
    //DataAccessException
    TreeSet<Player> sortByAge(boolean ascending);
    //DataAccessException
    TreeSet<Player> sortByPosition();
    //DataAccessException
    TreeSet<Player> sortByGoals(boolean ascending);
    //DataAccessException
    TreeSet<Player> sortByTeam();
    //DataAccessException
    double getAverageAge();
    //DataAccessException
    double getAverageGoals();
}