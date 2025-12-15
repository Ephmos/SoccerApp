package com.ephmos.SoccerApp;

import com.ephmos.SoccerApp.dao.SoccerDAO;
import com.ephmos.SoccerApp.db.Database;
import com.ephmos.SoccerApp.exceptions.*;
import com.ephmos.SoccerApp.objects.Player;
import com.ephmos.SoccerApp.objects.Team;
import com.ephmos.SoccerApp.others.Positions;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeSet;

public class SoccerDB implements SoccerDAO {
    Database database;
    public SoccerDB() throws DataAccessException {
        database = new Database();
    }

    @Override
    public void createPlayer(Player player) throws PlayerAlreadyExistsException, DataAccessException {
        String sql = "INSERT INTO soccerdb.players(name, lastname, age, position, goalsNumber, teamid) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, player.getName());
            statement.setString(2, player.getLastname());
            statement.setInt(3, player.getAge());
            statement.setObject(4, player.getPosition().toString(), Types.OTHER);
            statement.setInt(5, player.getGoalsNumber());
            statement.setLong(6, getTeamId(player.getTeam()));
            if (playerExists(player)) {
                throw new PlayerAlreadyExistsException("El jugador que se ha intentado insertar ya existe en la base de datos.");
            } else {
                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al crear al jugador.");
        }
    }

    @Override
    public void createTeam(Team team) throws TeamAlreadyExistsException, DataAccessException {
        String sql = "INSERT INTO soccerdb.teams(name, creationdate) VALUES (?, ?)";
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, team.getName());
            statement.setDate(2, new Date(team.getCreationdate().getTimeInMillis()));
            if (teamExists(team)) {
                throw new TeamAlreadyExistsException("El equipo que se ha intentado insertar ya existe en la base de datos.");
            } else {
                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al crear el equipo.");
        }
    }

    @Override
    public void deleteTeam(Team team) throws TeamNotFoundException, DataAccessException {
        String sql = "DELETE FROM soccerdb.teams WHERE name = ?";
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, team.getName());
            statement.executeUpdate();
            if (!teamExists(team)) {
                throw new TeamNotFoundException("El equipo que se ha intentado eliminar no existe en la base de datos.");
            } else {
                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al eliminar el equipo.");
        }
    }

    @Override
    public void deletePlayer(Player player) throws DataAccessException {
        String sql = "DELETE FROM soccerdb.players WHERE id = ?";
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setLong(1, getPlayerId(player.getName(), player.getLastname(), player.getAge()));
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al eliminar al jugador.");
        }
    }

    @Override
    public long getPlayerId(String name, String lastname, int age) throws PlayerNotFoundException, DataAccessException {
        String sql = "SELECT id FROM soccerdb.players WHERE name = ? AND lastname = ? AND age = ?";
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastname);
            statement.setInt(3, age);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            } else {
                throw new PlayerNotFoundException("No se encontró ningún ID asociado a los datos de jugador introducidos.");
            }
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al leer el id del jugador", exception);
        }
    }

    @Override
    public void updatePlayer(Player player, Team team, int newGoalsNumber) throws PlayerNotFoundException, DataAccessException {
        if (!playerExists(player)) {
            throw new PlayerNotFoundException("No se ha encontrado el jugador para actualizar.");
        }

        long playerId = getPlayerId(player.getName(), player.getLastname(), player.getAge());

        if ((team == null || !teamExists(team)) && newGoalsNumber == 0) {
            return;
        }

        try {
            if (newGoalsNumber != 0) {
                String sql = "UPDATE soccerdb.players SET goalsNumber = ? WHERE id = ?";
                try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
                    statement.setInt(1, newGoalsNumber);
                    statement.setLong(2, playerId);
                    statement.executeUpdate();
                }
            }

            // Actualizar equipo si es válido
            if (team != null && teamExists(team)) {
                String sql = "UPDATE soccerdb.players SET teamid = ? WHERE id = ?";
                try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
                    statement.setLong(1, getTeamId(team.getName()));
                    statement.setLong(2, playerId);
                    statement.executeUpdate();
                }
            } else if (team != null && !teamExists(team)) {
                throw new TeamNotFoundException("No se ha encontrado el equipo para asignar al jugador.");
            }
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al actualizar al jugador.");
        }
    }

    @Override
    public boolean playerExists(Player player) throws DataAccessException {
        String sql = "SELECT name, lastname, age, position, goalsNumber, teamid FROM soccerdb.players WHERE name = ? AND lastname = ? AND age = ? AND position = ? AND goalsNumber = ? AND teamid = ?";
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, player.getName());
            statement.setString(2, player.getLastname());
            statement.setInt(3, player.getAge());
            statement.setObject(4, player.getPosition().toString(), Types.OTHER);
            statement.setInt(5, player.getGoalsNumber());
            statement.setLong(6, getTeamId(player.getTeam()));
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al leer al jugador.", exception);
        }
    }

    @Override
    public boolean teamExists(Team team) throws DataAccessException {
        String sql = "SELECT name, creationdate FROM soccerdb.teams WHERE name = ? AND creationdate = ?";
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, team.getName());
            statement.setDate(2, new Date(team.getCreationdate().getTimeInMillis()));
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al leer el equipo.", exception);
        }
    }

    @Override
    public long getTeamId(String name) throws TeamNotFoundException, DataAccessException {
        String sql = "SELECT id FROM soccerdb.teams WHERE name = ?";
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            } else {
                throw new TeamNotFoundException("No se encontro el id del team.");
            }
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al leer el id del equipo", exception);
        }
    }


    // TERMINAR: Pensar en la lógica interna del método para realizar una comprobación específica.
    // Para localizar un jugador por los parámetros especificados, en caso de no especificar uno no se tendrá en cuenta.
    @Override
    public TreeSet<Player> findPlayer(String name, String lastname, String team, int age, Positions position, int goalsNumber) throws DataAccessException {
        String sql = "SELECT name, lastname, age, position, goalsNumber, team FROM soccerdb.players WHERE 1=1";

        if (name != null && !name.trim().isEmpty()) sql += " AND name = '" + name + "'";
        if (lastname != null && !lastname.trim().isEmpty()) sql += " AND lastname = '" + lastname + "'";
        if (team != null && !team.trim().isEmpty()) sql += " AND team = '" + team + "'";
        if (age > 0) sql += " AND age = " + age;
        if (position != null) sql += " AND position = '" + position.toString() + "'";
        if (goalsNumber >= 0) sql += " AND goalsNumber = " + goalsNumber;

        TreeSet<Player> players = new TreeSet<>();
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String pName = resultSet.getString("name");
                String pLastname = resultSet.getString("lastname");
                int pAge = resultSet.getInt("age");
                Positions pPosition = Positions.valueOf(resultSet.getString("position"));
                int pGoals = resultSet.getInt("goalsNumber");
                String pTeam = resultSet.getString("team");
                players.add(new Player(pName, pLastname, pAge, pPosition, pGoals, pTeam));
            }
            return players;
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al buscar jugador.");
        }
    }

    // Leer todos los jugadores
    @Override
    public ArrayList<Player> readAllPlayers() throws DataAccessException {
        String sql = "SELECT name, lastname, age, position, goalsNumber, teamid FROM soccerdb.players";
        ArrayList<Player> players = new ArrayList<>();
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                int age = resultSet.getInt("age");
                Positions position = Positions.valueOf(resultSet.getString("position"));
                int goalsNumber = resultSet.getInt("goalsNumber");
                String teamId = resultSet.getString("teamid");
                players.add(new Player(name, lastname, age, position, goalsNumber, teamId));
            }
            return players;
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al intentar leer a los jugadores.");
        }
    }

    // Buscar el top goleadores
    @Override
    public TreeSet<Player> findTopScorer() throws DataAccessException {
        //consulta
        String sql = "SELECT name, lastname, age, position, goalsNumber, teamid FROM soccerdb.players ORDER BY goalsNumber DESC";

        TreeSet players = new TreeSet();
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            //obetenemos los datos del jugador menos el nombre de equipo
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                int age = resultSet.getInt("age");
                Positions position = Positions.valueOf(resultSet.getString("position"));
                int goalsNumber = resultSet.getInt("goalsNumber");
                long teamId = resultSet.getLong("teamid");

                // Obtener nombre del equipo por id
                String teamName = null;
                String sqlTeam = "SELECT name FROM soccerdb.teams WHERE id = ?";
                try (PreparedStatement teamStatement = database.getConnection().prepareStatement(sqlTeam)) {
                    teamStatement.setLong(1, teamId);
                    ResultSet teamResult = teamStatement.executeQuery();
                    if (teamResult.next()) {
                        teamName = teamResult.getString("name");
                    }
                }

                players.add(new Player(name, lastname, age, position, goalsNumber, teamName));
            }
            return players;
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al buscar el máximo goleador.");
        }
    }

    // Buscar el top goleadores por equipo
    @Override
    public TreeSet<Player> findTopScorer(String team) throws DataAccessException {
        //llamamos al metodo de getTeamId
        long teamId = getTeamId(team);
        String sql = "SELECT name, lastname, age, position, goalsNumber FROM soccerdb.players WHERE teamid = ? ORDER BY goalsNumber DESC";
        TreeSet players = new TreeSet();

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            statement.setLong(1, teamId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                int age = resultSet.getInt("age");
                Positions position = Positions.valueOf(resultSet.getString("position"));
                int goalsNumber = resultSet.getInt("goalsNumber");

                players.add(new Player(name, lastname, age, position, goalsNumber, team));
            }
            return players;
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al buscar el top goleador por equipo.");
        }
    }

    // Ordenar por nombre
    @Override
    public TreeSet<Player> sortByName() throws DataAccessException {

        String sql = "SELECT name, lastname, age, position, goalsNumber, teamid FROM soccerdb.players ORDER BY name, lastname";
        TreeSet players = new TreeSet();

        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                int age = resultSet.getInt("age");
                Positions position = Positions.valueOf(resultSet.getString("position"));
                int goalsNumber = resultSet.getInt("goalsNumber");
                long teamId = resultSet.getLong("teamid");

                String teamName = null;
                String sqlTeam = "SELECT name FROM soccerdb.teams WHERE id = ?";
                try (PreparedStatement teamStatement = database.getConnection().prepareStatement(sqlTeam)) {
                    teamStatement.setLong(1, teamId);
                    ResultSet teamResult = teamStatement.executeQuery();
                    if (teamResult.next()) {
                        teamName = teamResult.getString("name");
                    }
                }

                players.add(new Player(name, lastname, age, position, goalsNumber, teamName));
            }
            return players;
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al ordenar por nombre.");
        }
    }

    // Ordenar por apellido
    @Override
    public TreeSet<Player> sortByLastname() throws DataAccessException {
        String sql = "SELECT name, lastname, age, position, goalsNumber, teamid FROM soccerdb.players ORDER BY lastname, name";
        TreeSet players = new TreeSet();
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                int age = resultSet.getInt("age");
                Positions position = Positions.valueOf(resultSet.getString("position"));
                int goalsNumber = resultSet.getInt("goalsNumber");
                long teamId = resultSet.getLong("teamid");

                String teamName = null;
                String sqlTeam = "SELECT name FROM soccerdb.teams WHERE id = ?";
                try (PreparedStatement teamStatement = database.getConnection().prepareStatement(sqlTeam)) {
                    teamStatement.setLong(1, teamId);
                    ResultSet teamResult = teamStatement.executeQuery();
                    if (teamResult.next()) {
                        teamName = teamResult.getString("name");
                    }
                }

                players.add(new Player(name, lastname, age, position, goalsNumber, teamName));
            }
            return players;
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al ordenar por apellido.");
        }
    }

    // Ordenar por edad
    @Override
    public TreeSet<Player> sortByAge(boolean ascending) throws DataAccessException {
        String sql = "SELECT name, lastname, age, position, goalsNumber, teamid FROM soccerdb.players ORDER BY age, lastname";
        TreeSet players = new TreeSet();
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                int age = resultSet.getInt("age");
                Positions position = Positions.valueOf(resultSet.getString("position"));
                int goalsNumber = resultSet.getInt("goalsNumber");
                long teamId = resultSet.getLong("teamid");

                String teamName = null;
                String sqlTeam = "SELECT name FROM soccerdb.teams WHERE id = ?";
                try (PreparedStatement teamStatement = database.getConnection().prepareStatement(sqlTeam)) {
                    teamStatement.setLong(1, teamId);
                    ResultSet teamResult = teamStatement.executeQuery();
                    if (teamResult.next()) {
                        teamName = teamResult.getString("name");
                    }
                }

                players.add(new Player(name, lastname, age, position, goalsNumber, teamName));
            }
            return players;
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al ordenar por edad.");
        }
    }

    // Ordenar por posición
    @Override
    public TreeSet<Player> sortByPosition() throws DataAccessException {
        String sql = "SELECT name, lastname, age, position, goalsNumber, teamid FROM soccerdb.players ORDER BY position, lastname";
        TreeSet players = new TreeSet();
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                int age = resultSet.getInt("age");
                Positions position = Positions.valueOf(resultSet.getString("position"));
                int goalsNumber = resultSet.getInt("goalsNumber");
                long teamId = resultSet.getLong("teamid");

                String teamName = null;
                String sqlTeam = "SELECT name FROM soccerdb.teams WHERE id = ?";
                try (PreparedStatement teamStatement = database.getConnection().prepareStatement(sqlTeam)) {
                    teamStatement.setLong(1, teamId);
                    ResultSet teamResult = teamStatement.executeQuery();
                    if (teamResult.next()) {
                        teamName = teamResult.getString("name");
                    }
                }

                players.add(new Player(name, lastname, age, position, goalsNumber, teamName));
            }
            return players;
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al ordenar por posición.");
        }
    }

    // Ordenar por goles
    @Override
    public TreeSet<Player> sortByGoals(boolean ascending) throws DataAccessException {
        String sql = "SELECT name, lastname, age, position, goalsNumber, teamid FROM soccerdb.players ORDER BY goalsnumber, lastname";
        TreeSet players = new TreeSet();
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                int age = resultSet.getInt("age");
                Positions position = Positions.valueOf(resultSet.getString("position"));
                int goalsNumber = resultSet.getInt("goalsNumber");
                long teamId = resultSet.getLong("teamid");

                String teamName = null;
                String sqlTeam = "SELECT name FROM soccerdb.teams WHERE id = ?";
                try (PreparedStatement teamStatement = database.getConnection().prepareStatement(sqlTeam)) {
                    teamStatement.setLong(1, teamId);
                    ResultSet teamResult = teamStatement.executeQuery();
                    if (teamResult.next()) {
                        teamName = teamResult.getString("name");
                    }
                }

                players.add(new Player(name, lastname, age, position, goalsNumber, teamName));
            }
            return players;
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al ordenar por nombre.");
        }
    }

    // Ordenar por equipo
    @Override
    public TreeSet<Player> sortByTeam() throws DataAccessException {
        String sql = "SELECT name, lastname, age, position, goalsNumber, teamid FROM soccerdb.players ORDER BY teamid, lastname, name";
        TreeSet players = new TreeSet();
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                int age = resultSet.getInt("age");
                Positions position = Positions.valueOf(resultSet.getString("position"));
                int goalsNumber = resultSet.getInt("goalsNumber");
                long teamId = resultSet.getLong("teamid");

                String teamName = null;
                String sqlTeam = "SELECT name FROM soccerdb.teams WHERE id = ?";
                try (PreparedStatement teamStatement = database.getConnection().prepareStatement(sqlTeam)) {
                    teamStatement.setLong(1, teamId);
                    ResultSet teamResult = teamStatement.executeQuery();
                    if (teamResult.next()) {
                        teamName = teamResult.getString("name");
                    }
                }

                players.add(new Player(name, lastname, age, position, goalsNumber, teamName));
            }
            return players;
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al ordenar por nombre.");
        }
    }

    @Override
    public double getAverageAge() throws DataAccessException {
        String sql = "SELECT AVG(age) FROM soccerdb.players";
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
        } catch (SQLException exception) {
            throw new DataAccessException("Error al obtener los datos para el cálculo de la media de edad");
        }
        return 0;
    }

    // Obtener la media de goles
    @Override
    public double getAverageGoals() throws DataAccessException {
        String sql = "SELECT AVG(goalsNumber) FROM soccerdb.players";
        try (PreparedStatement statement = database.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
        } catch (SQLException exception) {
            throw new DataAccessException("Error al obtener los datos para el cálculo de la media de goles");
        }
        return 0;
    }
}
