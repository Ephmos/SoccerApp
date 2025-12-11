package com.ephmos.SoccerApp.exceptions;

public class TeamAlreadyExistsException extends DataAccessException {
    public TeamAlreadyExistsException(String message) {
        super(message);
    }
}
