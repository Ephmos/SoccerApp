package com.ephmos.SoccerApp;

public class Player {
    String name;
    String lastname;
    int age;
    Positions position;
    int goalsNumber;
    public Player(String name, String lastname, int age, Positions position, int goalsNumber) {
        this.name = name;
        this.lastname = lastname;
        this.age = age;
        this.position = position;
        this.goalsNumber = goalsNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("el nombre esta vacio");
        }
        this.name = name.trim();
    }

    public String getLastname1() {
        return lastname;
    }

    public void setLastname(String lastname) {
        if (lastname == null || lastname.trim().isEmpty()) {
            throw new IllegalArgumentException("el primer apellido esta vacio");
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
            throw new IllegalArgumentException("Debe tener una posicion");
        }
        this.position = position;
    }

    public int getGoalsNumber() {
        return goalsNumber;
    }

    public void setGoalsNumber(int goalsNumber) {
        if (goalsNumber < 0) {
            throw new IllegalArgumentException("el numero de goles no puede ser negativo");
        }
        this.goalsNumber = goalsNumber;
    }

    @Override
    public String toString() {
        return "Jugador{"
                + "name='" + name + '\''
                + ", lastname1='" + lastname + '\''
                + ", age=" + age
                + ", position=" + position
                + ", goalsNumber=" + goalsNumber
                + '}';
    }
}
