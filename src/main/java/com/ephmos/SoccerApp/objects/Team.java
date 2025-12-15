package com.ephmos.SoccerApp.objects;

import java.util.GregorianCalendar;

public class Team {
    private String name;
    private GregorianCalendar creationdate;
    public Team(String name, GregorianCalendar creationdate) {
        setName(name);
        setCreationdate(creationdate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalStateException("El equipo debe tener un nombre.");
        }
        this.name = name;
    }

    public GregorianCalendar getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(GregorianCalendar creationdate) {
        if (creationdate == null) {
            throw new IllegalArgumentException("El equipo debe tener una fecha de creación.");
        }
        this.creationdate = creationdate;
    }
}
