package com.ephmos;

import com.ephmos.SoccerApp.SoccerDB;
import com.ephmos.SoccerApp.exceptions.DataAccessException;
import com.ephmos.SoccerApp.objects.Player;
import com.ephmos.SoccerApp.others.Positions;

public class Main {
    public static void main(String[] args) throws DataAccessException {
        try {
            SoccerDB soccerDB = new SoccerDB();
            Player lamine = new Player("Lamine", "Yamal", 20, Positions.FWD, 100, "FC Barcelona");
            //soccerDB.createPlayer(lamine);
            soccerDB.deletePlayer(lamine);
            //Player nuevolamine = new Player("Lamine", "Yamal", 21, Positions.FWD, 101, "FC Barcelona");
            //System.out.println(soccerDB.readAllPlayers());
            //System.out.println(soccerDB.getPlayerId("Lamine", "Yamal", 21));
            //System.out.println(soccerDB.getTeamId("FC Barcelona"));
        } catch (DataAccessException exception) {
            throw new DataAccessException("Ha ocurrido un error al intentar realizar la conexión con la base de datos.", exception);
        }
    }
}