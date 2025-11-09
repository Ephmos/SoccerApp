package com.ephmos;

import com.ephmos.SoccerApp.Player;
import com.ephmos.SoccerApp.Positions;
import com.ephmos.SoccerApp.SoccerDAOFileJSON;
import com.ephmos.SoccerApp.SoccerDAOFileXML;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String file = "C:\\Users\\Kevlar\\IdeaProjects\\SoccerApp\\src\\main\\resources\\Jugadores.json";
        List<Player> players = new ArrayList<>();
        SoccerDAOFileJSON obj = new SoccerDAOFileJSON(file, players);
        try {
            //System.out.println(obj.readAllPlayers());
            System.out.println(obj.readPlayers("Antonio"));
        } catch (Exception exception) {
            throw new Exception(exception);
        }
        /*
        SoccerDAOFileXML prueba1 = new SoccerDAOFileXML("C:\\Users\\Klugy\\Desktop\\2ºDAM\\GITHUB\\SoccerApp\\src\\main\\resources\\Jugadores.xml");
        System.out.println("Esta llena?");
        System.out.println(prueba1.isFull());
        System.out.println("Esta vacio?");
        System.out.println(prueba1.isEmpty());
        //CARGAMOS LA LISTA
        prueba1.readAllPlayers();
        //COMPROBAMOS QUE SE HAN CARGADO
        for(Player p : prueba1.getListaJugadores()){
            System.out.println(p);
        }
        //Hay 48 jugadores
        System.out.println("Numero de Jugadores: "+prueba1.getListaJugadores().size());
        prueba1.addPlayer(new Player("Alejandro", "Balde", 21, Positions.DEF, 0, "FC Barcelona"));
        System.out.println("");
        prueba1.addPlayer(new Player("Ferland", "Mendy", 30, Positions.DEF, 1, "Real Madrid"));
        System.out.println("");
        prueba1.deletePlayer(new Player("Alejandro", "Balde", 21, Positions.DEF, 0, "FC Barcelona"));
        System.out.println("");
        try {
            prueba1.updatePlayer(new Player("Ferland", "Mendy", 30, Positions.DEF, 1, "FC Barcelona"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("");
        System.out.println("Top scorers");
        prueba1.findTopScorers().forEach(System.out::println);
        System.out.println("");
        System.out.println("Top scorers from barsa");
        prueba1.findTopScorer("FC Barcelona").forEach(System.out::println);
        System.out.println("");
        System.out.println("Top scorers from atleti");
        prueba1.findTopScorer("Atlético de Madrid").forEach(System.out::println);
        System.out.println("");
        System.out.println("Edad promedio "+prueba1.getAverageAge());
        System.out.println("");
        prueba1.getPlayersByPosition(Positions.MF).forEach(System.out::println);*/
    }
}