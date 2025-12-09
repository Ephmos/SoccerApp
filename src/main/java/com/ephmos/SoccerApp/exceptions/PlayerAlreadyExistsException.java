package com.ephmos.SoccerApp.exceptions;

// Excepción si el jugador ya existe
public class PlayerAlreadyExistsException extends DataAccessException {
    public PlayerAlreadyExistsException(String message) {
        super(message);
    }
}
