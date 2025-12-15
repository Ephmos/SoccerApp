package com.ephmos.SoccerApp.exceptions;


// Excepción para errores de acceso (BBDD o I/O)
public class DataAccessException extends Exception {
    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
 