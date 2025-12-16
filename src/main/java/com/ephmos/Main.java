package com.ephmos;

import com.ephmos.SoccerApp.SoccerDB;
import com.ephmos.SoccerApp.exceptions.DataAccessException;
import com.ephmos.SoccerApp.objects.Player;
import com.ephmos.SoccerApp.objects.Team;
import com.ephmos.SoccerApp.others.Positions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws DataAccessException {
        SoccerDB soccerDB = new SoccerDB();
        Team bcn = new Team("FC Barcelona", new GregorianCalendar(1899, GregorianCalendar.NOVEMBER, 29));
        Team rm = new Team("Real Madrid", new GregorianCalendar(1902, GregorianCalendar.MARCH, 6));
        Player jugador1 = new Player("Oscar", "Perez", 22, Positions.FWD, 80, bcn.getName());
        Player jugador2 = new Player("Álvaro", "Gomez", 25, Positions.MF, 75, bcn.getName());
        Player jugador3 = new Player("Pol", "Lopez", 27, Positions.DEF, 78, bcn.getName());
        Player jugador4 = new Player("Pedro", "Sanchez", 24, Positions.GK, 82, bcn.getName());
        Player jugador5 = new Player("Mario", "Diaz", 21, Positions.FWD, 77, bcn.getName());
        Player jugador6 = new Player("Luis", "Ruiz", 26, Positions.MF, 79, bcn.getName());
        Player jugador7 = new Player("Fernando", "Hernandez", 23, Positions.DEF, 76, bcn.getName());
        Player jugador8 = new Player("Lautaro", "Martinez", 28, Positions.FWD, 81, bcn.getName());
        Player jugador9 = new Player("Osvaldo", "Castro", 22, Positions.MF, 74, bcn.getName());
        Player jugador10 = new Player("Hugo", "Navarro", 29, Positions.DEF, 83, bcn.getName());
        Player jugador11 = new Player("Luis", "Suarez", 24, Positions.FWD, 79, rm.getName());
        Player jugador12 = new Player("Iñigo", "Vazquez", 27, Positions.MF, 81, rm.getName());
        Player jugador13 = new Player("Jean", "Ortega", 26, Positions.DEF, 77, rm.getName());
        Player jugador14 = new Player("Samuel", "Reyes", 23, Positions.GK, 80, rm.getName());
        Player jugador15 = new Player("Pablo", "Nunez", 25, Positions.FWD, 82, rm.getName());
        Player jugador16 = new Player("Leo", "Cano", 21, Positions.MF, 73, rm.getName());
        Player jugador17 = new Player("Pablo", "Iglesias", 28, Positions.DEF, 84, rm.getName());
        Player jugador18 = new Player("Álvaro", "Moreno", 22, Positions.FWD, 76, rm.getName());
        Player jugador19 = new Player("Sergio", "Ramos", 30, Positions.MF, 85, rm.getName());
        Player jugador20 = new Player("Raúl", "Gil", 24, Positions.DEF, 78, rm.getName());
        Player jugador21 = new Player("Salvador", "Pascual", 23, Positions.FWD, 79, bcn.getName());
        Player jugador22 = new Player("Samuel", "Dominguez", 26, Positions.MF, 80, bcn.getName());
        Player jugador23 = new Player("Mario", "Prieto", 27, Positions.DEF, 82, bcn.getName());
        Player jugador24 = new Player("Leonardo", "Campos", 22, Positions.GK, 77, bcn.getName());
        Player jugador25 = new Player("Alejandro", "Lorenzo", 25, Positions.FWD, 81, bcn.getName());
        /*try {
            soccerDB.createTeam(bcn);
            soccerDB.createTeam(rm);
            soccerDB.createPlayer(jugador1);
            soccerDB.createPlayer(jugador2);
            soccerDB.createPlayer(jugador3);
            soccerDB.createPlayer(jugador4);
            soccerDB.createPlayer(jugador5);
            soccerDB.createPlayer(jugador6);
            soccerDB.createPlayer(jugador7);
            soccerDB.createPlayer(jugador8);
            soccerDB.createPlayer(jugador9);
            soccerDB.createPlayer(jugador10);
            soccerDB.createPlayer(jugador11);
            soccerDB.createPlayer(jugador12);
            soccerDB.createPlayer(jugador13);
            soccerDB.createPlayer(jugador14);
            soccerDB.createPlayer(jugador15);
            soccerDB.createPlayer(jugador16);
            soccerDB.createPlayer(jugador17);
            soccerDB.createPlayer(jugador18);
            soccerDB.createPlayer(jugador19);
            soccerDB.createPlayer(jugador20);
            soccerDB.createPlayer(jugador21);
            soccerDB.createPlayer(jugador22);
            soccerDB.createPlayer(jugador23);
            soccerDB.createPlayer(jugador24);
            soccerDB.createPlayer(jugador25);

            soccerDB.updatePlayer(jugador1, bcn, 0);
            //System.out.println(soccerDB.findPlayer(jugador1.getName(), null, null, 0, null, 0));
            soccerDB.deletePlayer(jugador1);
            System.out.println("La media de edad es: " + Math.floor(soccerDB.getAverageAge()));
            System.out.println("La media de goles es: " + Math.floor(soccerDB.getAverageGoals()));
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            throw new DataAccessException("Ha ocurrido un error durante la ejecución del programa.");
        }*/
        System.out.println("id Álvaro Gomez"+soccerDB.getPlayerId("Álvaro","Gomez",25));
        System.out.println(soccerDB.playerExists(jugador2));
        System.out.println(soccerDB.teamExists(bcn));
        System.out.println(soccerDB.getTeamId("FC Barcelona"));
        ArrayList<Player> jugadores = new ArrayList<>();
        System.out.println(soccerDB.readAllPlayers());
        System.out.println("RETORNO DE POL");
        System.out.println(soccerDB.findPlayer("Pol","Lopez",bcn.getName(),27,Positions.DEF, 78));
        System.out.println("TOP SCORER: ");
        System.out.println(soccerDB.findTopScorer());
        System.out.println(soccerDB.findTopScorer(bcn.getName()));
        System.out.println("JUGADORES ORDENADOS  \n");
        //System.out.println(soccerDB.sortByName());
        //System.out.println(soccerDB.sortByAge(true));
        //System.out.println(soccerDB.sortByLastname());
        //System.out.println(soccerDB.sortByTeam());

    }
}