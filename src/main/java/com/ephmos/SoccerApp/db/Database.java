package com.ephmos.SoccerApp.db;

import com.ephmos.SoccerApp.exceptions.DataAccessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    Connection connection;
    public Database() throws DataAccessException {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/aprendizaje");
        } catch (SQLException exception) {
            throw new DataAccessException("Ha ocurrido un error al intentar realizar la conexión con la base de datos.",exception);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
