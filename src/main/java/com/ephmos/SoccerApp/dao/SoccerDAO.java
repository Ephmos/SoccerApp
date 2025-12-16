package com.ephmos.SoccerApp.dao;

import com.ephmos.SoccerApp.exceptions.*;
import com.ephmos.SoccerApp.objects.Player;
import com.ephmos.SoccerApp.objects.Team;
import com.ephmos.SoccerApp.others.Positions;

import java.util.ArrayList;
import java.util.List;

public interface SoccerDAO {
    /**
     * Inserción de jugador.
     * @param player El jugador que se desea insertar.
     * @throws PlayerAlreadyExistsException El jugador que se intenta insertar ya existe.
     * @throws DataAccessException No es posible conectarse a la fuente de datos.
     */
    void createPlayer(Player player) throws PlayerAlreadyExistsException, DataAccessException;
    void createTeam(Team team) throws TeamAlreadyExistsException, DataAccessException;
    void deleteTeam(Team team) throws TeamNotFoundException, DataAccessException;
    /**
     *
     * @param player El jugador que se desea eliminar.
     * @throws PlayerNotFoundException El jugador que se intenta eliminar no existe.
     * @throws DataAccessException No es posible conectarse a la fuente de datos.
     */
    void deletePlayer(Player player) throws PlayerNotFoundException, DataAccessException;
    long getPlayerId(String name, String lastname, int age) throws DataAccessException;
    void updatePlayer(Player player, Team team, int newGoalsNumber) throws PlayerNotFoundException, DataAccessException;
    /**
     * Comprobar la existencia de un jugador.
     * @param player El jugador que se desea localizar.
     * @return Si el jugador existe.
     * @throws DataAccessException No es posible conectarse a la fuente de datos.
     */
    boolean playerExists(Player player) throws DataAccessException;
    boolean teamExists(Team team) throws DataAccessException;
    long getTeamId(String name) throws DataAccessException;
    // Para localizar un jugador por los parámetros especificados, en caso de no especificar uno no se tendrá en cuenta.
    //DataAccessException
    ArrayList<Player> findPlayer(String name, String lastname, String team, int age, Positions position, int goalsNumber) throws DataAccessException;
    //DataAccessException
    List<Player> readAllPlayers() throws DataAccessException;
    //DataAccessException
    ArrayList<Player> findTopScorer() throws DataAccessException;
    //DataAccessException
    ArrayList<Player> findTopScorer(String team) throws DataAccessException;
    //DataAccessException
    ArrayList<Player> sortByName() throws DataAccessException;
    //DataAccessException
    ArrayList<Player> sortByLastname() throws DataAccessException;
    //DataAccessException
    ArrayList<Player> sortByAge(boolean ascending) throws DataAccessException;
    //DataAccessException
    ArrayList<Player> sortByPosition() throws DataAccessException;
    //DataAccessException
    ArrayList<Player> sortByGoals(boolean ascending) throws DataAccessException;
    //DataAccessException
    ArrayList<Player> sortByTeam() throws DataAccessException;
    //DataAccessException
    double getAverageAge() throws DataAccessException;
    //DataAccessException
    double getAverageGoals() throws DataAccessException;
}