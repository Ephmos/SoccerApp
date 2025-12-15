package com.ephmos.SoccerApp.objects;

import com.ephmos.SoccerApp.others.Positions;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Player implements Externalizable {
    String name;
    String lastname;
    int age;
    Positions position;
    int goalsNumber;
    String team;
    public Player(String name, String lastname, int age, Positions position, int goalsNumber, String team)  {
        setName(name);
        setLastname(lastname);
        setAge(age);
        setPosition(position);
        setGoalsNumber(goalsNumber);
        setTeam(team);
    }

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre está vacío");
        }
        this.name = name.trim();
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        if (lastname == null || lastname.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido está vacío");
        }
        this.lastname = lastname.trim();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0 || age > 100) {
            throw new IllegalArgumentException("La edad debe estar entre 0 y 100");
        }
        this.age = age;
    }

    public Positions getPosition() {
        return position;
    }

    public void setPosition(Positions position) {
        if (position == null) {
            throw new IllegalArgumentException("Debe tener una posición");
        }
        this.position = position;
    }

    public int getGoalsNumber() {
        return goalsNumber;
    }

    public void setGoalsNumber(int goalsNumber) {
        if (goalsNumber < 0) {
            throw new IllegalArgumentException("El número de goles no puede ser negativo");
        }
        this.goalsNumber = goalsNumber;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return String.format(
                """
                        {%n\
                          name: %s %s%n\
                          age: %d%n\
                          position: %s%n\
                          team: %s%n\
                          goals: %d%n\
                        }""",
                name, lastname, age, position, team, goalsNumber
        );
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return name.equals(player.name) &&
                lastname.equals(player.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastname);
    }*/
}
