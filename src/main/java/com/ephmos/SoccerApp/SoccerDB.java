package com.ephmos.SoccerApp;

import com.ephmos.SoccerApp.dao.SoccerDAO;
import com.ephmos.SoccerApp.db.Database;
import com.ephmos.SoccerApp.exceptions.DataAccessException;
import com.ephmos.SoccerApp.objects.Player;
import com.ephmos.SoccerApp.others.Positions;

import java.sql.PreparedStatement;
import java.util.TreeSet;

public class SoccerDB implements SoccerDAO ,{
    Database database = new Database();

    // TERMINAR: SACAR LA CONEXIÓN EN LOS MÉTODOS NECESARIOS
    // Para insertar un jugador en la base de datos.
    @Override
    public void createPlayer(Player player) {
        String sql = "";
    }

    // Para eliminar un jugador de la base de datos.
    @Override
    public void deletePlayer(Player player) {
        String sql = "";
    }

    // Para actualizar un jugador en la base de datos.
    @Override
    public void updatePlayer(Player player) {
        String sql = "";
    }

    // Para comprobar si un jugador existe en la base de datos.
    @Override
    public boolean playerExists(Player player) {
        /*
        String name;
        String lastname;
        int age;
        Positions position;
        int goalsNumber;
        String team;
        */
        String sql = "SELECT name, lastname, age, position, goalsNumber, team WHERE name = ? AND lastname = ? AND age = ? AND position = ? AND goalsNumber = ? AND team = ?";
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {

        } catch (DataAccessException exception) {

        }
        return false;
    }

    // TERMINAR: Pensar en la lógica interna del método para realizar una comprobación específica.
    // Para localizar un jugador por los parámetros especificados, en caso de no especificar uno no se tendrá en cuenta.
    @Override
    public TreeSet<Player> findPlayer(String name, String lastname, String team, int age, Positions position, int goalsNumber) {
        return null;
    }

    // Leer todos los jugadores
    @Override
    public TreeSet<Player> readAllPlayers() {
        return null;
    }

    // Buscar el top goleadores
    @Override
    public TreeSet<Player> findTopScorer() {
        return null;
    }

    // Buscar el top goleadores por equipo
    @Override
    public TreeSet<Player> findTopScorer(String team) {
        return null;
    }

    // Ordenar por nombre
    @Override
    public TreeSet<Player> sortByName() {
        return null;
    }

    // Ordenar por apellido
    @Override
    public TreeSet<Player> sortByLastname() {
        return null;
    }

    // Ordenar por edad
    @Override
    public TreeSet<Player> sortByAge(boolean ascending) {
        return null;
    }

    // Ordenar por posición
    @Override
    public TreeSet<Player> sortByPosition() {
        return null;
    }

    // Ordenar por goles
    @Override
    public TreeSet<Player> sortByGoals(boolean ascending) {
        return null;
    }

    // Ordenar por equipo
    @Override
    public TreeSet<Player> sortByTeam() {
        return null;
    }

    // Obtener la media de edad
    @Override
    public double getAverageAge() {
        return 0;
    }

    // Obtener la media de goles
    @Override
    public double getAverageGoals() {
        return 0;
    }
}
