package com.ephmos.SoccerApp.exceptions;

// Excepción si el jugador ya existe
public class PlayerAlreadyExistsException extends Exception {
    public PlayerAlreadyExistsException(String message) {
        super(message);
    }
}
