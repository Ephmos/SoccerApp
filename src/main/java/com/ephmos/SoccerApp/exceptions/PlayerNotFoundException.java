package com.ephmos.SoccerApp.exceptions;

// Excepción si el jugador no se encuentra
public class PlayerNotFoundException extends Exception {
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
