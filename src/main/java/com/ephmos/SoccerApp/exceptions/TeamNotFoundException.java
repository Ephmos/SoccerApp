package com.ephmos.SoccerApp.exceptions;

public class TeamNotFoundException extends DataAccessException {
    public TeamNotFoundException(String message) {
        super(message);
    }
}
