package com.ephmos;

import com.ephmos.SoccerApp.Player;
import com.ephmos.SoccerApp.Positions;
import com.ephmos.SoccerApp.SoccerDAOFileJSON;
import com.ephmos.SoccerApp.SoccerDAOFileXML;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
       /*String file = "C:\\Users\\Kevlar\\Desktop\\SoccerApp\\src\\main\\resources\\Jugadores.json";
        Jsonb jsonb = JsonbBuilder.create();
        List<Player> players = new ArrayList<>();
        SoccerDAOFileJSON obj = new SoccerDAOFileJSON(jsonb, file, players);
        try {
            //obj.addPlayer(new Player("Roberto", "Carlos", 35, Positions.DEF, 30, "FC Barcelona"));
            //System.out.println(obj.readAllPlayers());
            //System.out.println(obj.filterPlayersByAge(33));
            //System.out.println(obj.getAverageAge());
            //System.out.println(obj.findTopScorers());
            System.out.println(obj.findTopScorer("FC Barcelona"));
        } catch (Exception exception) {
            throw new Exception(exception);
        }*/

        SoccerDAOFileXML prueba1 = new SoccerDAOFileXML("C:\\Users\\Klugy\\Desktop\\2ºDAM\\GITHUB\\SoccerApp\\src\\main\\resources\\Jugadores.xml");
        /*System.out.println("Esta llena?");
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
        prueba1.findByPosition(Positions.MF).forEach(System.out::println);
        System.out.println("buscar jugador por nombre");*/
        //prueba1.readPlayers("Iñaki").forEach(System.out::println);
        //prueba1.sortByAge();
        //prueba1.getListaJugadores().forEach(System.out::println);
        //prueba1.sortByTeam();
        //prueba1.getListaJugadores().forEach(System.out::println);
        //prueba1.sortByLastname();
        //prueba1.getListaJugadores().forEach(System.out::println);


        //LLAMADAS AL SISTEMA
        //generarArchivo("C:\\Users\\Klugy\\Desktop\\2ºDAM\\GITHUB\\SoccerApp\\src\\main\\resources","prueba1.txt");
        //renombrarArchivo("C:\\Users\\Klugy\\Desktop\\2ºDAM\\GITHUB\\SoccerApp\\src\\main\\resources\\JavierJaen.txt","prueba1.txt");
    }
    //metodo que genera un archivo introduciendo una ruta y un nombre de directorio
    public static void generarArchivo(String ruta, String nombreArchivo) throws IOException {
        try {
            //representamos el fichero
            File file=new File(ruta,nombreArchivo);
            //creamos el fichero
            //si se crea lo muestra por consola
            if(file.createNewFile()){
                System.out.println("El archivo "+nombreArchivo+" se ha generado exitosamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("no se pudo generar el archivo"+e);

        }
    }
    public static void renombrarArchivo(String ruta, String nombreArchivo) throws IOException {
        try{
            //representamos el fcihero
            File file= new File(ruta);
            //sacamos la ruta
            String padre= file.getParent();
            //representamos el nuevo archivo
            File Filereplace = new File(padre,nombreArchivo);
            //hacemos un rename
            file.renameTo(Filereplace);
        } catch (Exception e) {
        throw new IOException("no se pudo renombrar el archivo"+e);
        }
    }

}