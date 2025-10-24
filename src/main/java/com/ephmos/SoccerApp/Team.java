package com.ephmos.SoccerApp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Team {
    String name;
    String stadium;
    int creationYear;
    ArrayList<Player> players;

    public Team(String name, String stadium, int creationYear, ArrayList<Player> players) {
        this.name = name;
        this.stadium = stadium;
        this.creationYear = creationYear;
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("nombre no puede ser null o vacío");
        }
        this.name = name.trim();
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        if (stadium == null || stadium.trim().isEmpty()) {
            throw new IllegalArgumentException("Estadio no puede ser null o vacío");
        }
        this.stadium = stadium.trim();
    }

    public int getCreationYear() {
        return creationYear;
    }

    public void setCreationYear(int creationYear) {
        int currentYear = LocalDate.now().getYear();
        //1824 fue la fecha de creación del primer club (segun google)
        if (creationYear < 1824 || creationYear > currentYear) {
            throw new IllegalArgumentException("annoCreacion debe estar entre 1850 y " + currentYear);
        }
        this.creationYear = creationYear;
    }

    public ArrayList<Player> getJugadores() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {

        if (players == null) {
            throw new IllegalArgumentException("La lista no puede ser nula");
        }
        if (players.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede estar vacío");
        }
        if (players.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("elementos nulos");
        }
        this.players = players;
    }

    @Override
    public String toString() {
        return "Equipo{"
                + "nombre='" + name + '\''
                + ", Estadio='" + stadium + '\''
                + ", annoCreacion=" + creationYear
                + ", jugadores=" + players
                + '}';
    }
}
