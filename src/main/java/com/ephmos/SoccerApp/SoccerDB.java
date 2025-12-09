package com.ephmos.SoccerApp;

import com.ephmos.SoccerApp.dao.SoccerDAO;
import com.ephmos.SoccerApp.db.Database;
import com.ephmos.SoccerApp.exceptions.DataAccessException;
import com.ephmos.SoccerApp.exceptions.PlayerAlreadyExistsException;
import com.ephmos.SoccerApp.exceptions.PlayerNotFoundException;
import com.ephmos.SoccerApp.objects.Player;
import com.ephmos.SoccerApp.others.Positions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.TreeSet;

public class SoccerDB implements SoccerDAO {
    Database database;
    public SoccerDB() throws DataAccessException {
        database = new Database();
    }

    @Override
    public void createPlayer(Player player) throws DataAccessException {
        String sql = "INSERT INTO soccerdb.players(name, lastname, age, position, goalsNumber, team) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, player.getName());
            statement.setString(2, player.getLastname());
            statement.setInt(3, player.getAge());
            statement.setObject(4, player.getPosition().toString(), Types.OTHER);
            statement.setInt(5, player.getGoalsNumber());
            statement.setString(6, player.getTeam());
            if (playerExists(player)) {
                throw new PlayerAlreadyExistsException("El jugador que se ha intentado insertar ya existe en la base de datos.");
            }
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al crear al jugador.");
        }
    }

    @Override
    public void deletePlayer(Player player) throws DataAccessException {
        String sql = "DELETE FROM soccerdb.players WHERE name = ? AND lastname = ? AND age = ? AND position = ? AND goalsNumber = ? AND team = ?";
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, player.getName());
            statement.setString(2, player.getLastname());
            statement.setInt(3, player.getAge());
            statement.setObject(4, player.getPosition().toString(), Types.OTHER);
            statement.setInt(5, player.getGoalsNumber());
            statement.setString(6, player.getTeam());
            if (!playerExists(player)) {
                throw new PlayerNotFoundException("El jugador que se ha intentado eliminar no existe en la base de datos.");
            }
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al eliminar al jugador.");
        }
    }

    @Override
    public void updatePlayer(Player player, int newGoalsNumber, String newTeam) throws PlayerNotFoundException, DataAccessException {
        String sql = "UPDATE soccerdb.players SET goalsNumber = ? WHERE name = ? AND lastname = ? AND age = ? AND position = ?";
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            if (!playerExists(player)) {
                throw new PlayerNotFoundException("El jugador que se ha intentado actualizar no existe en la base de datos.");
            }
            statement.setInt(1, newGoalsNumber);
            statement.setString(2, newTeam);
            statement.setString(3, player.getName());
            statement.setString(4, player.getLastname());
            statement.setInt(5, player.getAge());
            statement.setObject(6, player.getPosition().toString(), Types.OTHER);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al actualizar al jugador.");
        }
    }

    @Override
    public boolean playerExists(Player player) throws DataAccessException {
        String sql = "SELECT name, lastname, age, position, goalsNumber, team FROM soccerdb.players WHERE name = ? AND lastname = ? AND age = ? AND position = ? AND goalsNumber = ? AND team = ?";
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, player.getName());
            statement.setString(2, player.getLastname());
            statement.setInt(3, player.getAge());
            statement.setObject(4, player.getPosition().toString(), Types.OTHER);
            statement.setInt(5, player.getGoalsNumber());
            statement.setString(6, player.getTeam());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al leer al jugador.", exception);
        }
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
