package com.ephmos.SoccerApp.exceptions;

import java.sql.SQLException;

// Excepción para errores de acceso (BBDD o I/O)
public class DataAccessException extends Exception {
    private String sqlState;
    private int errorCode;
    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, SQLException cause) {
        super(message, cause);
        if (cause != null) {
            this.sqlState = cause.getSQLState();
            this.errorCode = cause.getErrorCode();
        }
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getSqlState() {
        return sqlState;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
