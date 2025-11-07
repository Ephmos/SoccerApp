package com.ephmos;

import com.ephmos.SoccerApp.Player;
import com.ephmos.SoccerApp.SoccerDAOFileJSON;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        String file = "C:\\Users\\Kevlar\\IdeaProjects\\SoccerApp\\src\\main\\resources\\Jugadores.json";
        List<Player> players = new ArrayList<>();
        SoccerDAOFileJSON obj = new SoccerDAOFileJSON(file, players);
        try {
            //System.out.println(obj.readPlayers());
            //System.out.println(obj.readPlayer("Robertito"));
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }
}