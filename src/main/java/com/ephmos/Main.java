package com.ephmos;

import com.ephmos.SoccerApp.Player;
import com.ephmos.SoccerApp.Positions;
import com.ephmos.SoccerApp.SoccerDAOFileJSON;
import com.ephmos.SoccerApp.SoccerDAOFileXML;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String file = "./Jugadores.json";
        Jsonb jsonb = JsonbBuilder.create();
        List<Player> players = new ArrayList<>();
        SoccerDAOFileJSON obj = new SoccerDAOFileJSON(jsonb, file, players);
        try {
            // El archivo parte de inicio como vacío.
            System.out.println("¿Está vacío?: " + obj.isEmpty());
            // Realiza una escritura de corchetes debido a un error con el JSON que causa que no sea posible escribir si no los tiene de estado inicial.
            if (obj.isEmpty()) {
                try (FileWriter fileWriter = new FileWriter("./Jugadores.json", StandardCharsets.UTF_8)) {
                    fileWriter.write("[]");
                } catch (IOException exception) {
                    throw new IOException("Error escribiendo jugadores al archivo", exception);
                }
            }

            obj.addPlayer(new Player("Cristiano", "Ronaldo", 32, Positions.FWD, 10, "Real Madrid"));
            obj.addPlayer(new Player("Lionel", "Messi", 29, Positions.FWD, 10, "FC Barcelona"));
            System.out.println(obj.readAllPlayers());

            // Intento añadir nuevamente a un jugador que ya existe en la lista
            System.out.println("Jugador duplicado: ");
            obj.addPlayer(new Player("Lionel", "Messi", 29, Positions.FWD, 10, "FC Barcelona"));

            // Intento eliminar a un jugador que no existe en la lista
            System.out.println("Jugador eliminado dos veces: ");
            obj.deletePlayer(new Player("Lionel", "Messi", 29, Positions.FWD, 10, "FC Barcelona"));
            obj.deletePlayer(new Player("Lionel", "Messi", 29, Positions.FWD, 10, "FC Barcelona"));
            obj.deletePlayer(new Player("Cristiano", "Ronaldo", 32, Positions.FWD, 10, "Real Madrid"));

            // Cargo una lista de jugadores más sustancial para poder comenzar con las operaciones de filtrado.
            obj.addPlayer(new Player("Thibaut", "Courtois", 32, Positions.GK, 1, "Real Madrid"));
            obj.addPlayer(new Player("Dani", "Carvajal", 32, Positions.DEF, 2, "Real Madrid"));
            obj.addPlayer(new Player("Éder", "Militão", 26, Positions.DEF, 3, "Real Madrid"));
            obj.addPlayer(new Player("Antonio", "Rüdiger", 31, Positions.DEF, 6, "Real Madrid"));
            obj.addPlayer(new Player("Ferland", "Mendy", 29, Positions.DEF, 5, "Real Madrid"));
            obj.addPlayer(new Player("Toni", "Kroos", 34, Positions.MF, 8, "Real Madrid"));
            obj.addPlayer(new Player("Luka", "Modrić", 39, Positions.MF, 19, "Real Madrid"));
            obj.addPlayer(new Player("Federico", "Valverde", 26, Positions.MF, 15, "Real Madrid"));
            obj.addPlayer(new Player("Vinícius", "Júnior", 24, Positions.FWD, 20, "Real Madrid"));
            obj.addPlayer(new Player("Karim", "Benzema", 36, Positions.FWD, 9, "Real Madrid"));
            obj.addPlayer(new Player("Rodrygo", "Goes", 23, Positions.FWD, 11, "Real Madrid"));
            obj.addPlayer(new Player("Marc-André", "ter Stegen", 32, Positions.GK, 1, "FC Barcelona"));
            obj.addPlayer(new Player("Alejandro", "Balde", 21, Positions.DEF, 3, "FC Barcelona"));
            obj.addPlayer(new Player("Gerard", "Piqué", 36, Positions.DEF, 3, "FC Barcelona"));
            obj.addPlayer(new Player("Andreas", "Christensen", 28, Positions.DEF, 15, "FC Barcelona"));
            obj.addPlayer(new Player("Sergiño", "Dest", 23, Positions.DEF, 2, "FC Barcelona"));
            obj.addPlayer(new Player("Sergio", "Busquets", 35, Positions.MF, 5, "FC Barcelona"));
            obj.addPlayer(new Player("Pedri", "González", 21, Positions.MF, 26, "FC Barcelona"));
            obj.addPlayer(new Player("Gavi", "Páez", 19, Positions.MF, 6, "FC Barcelona"));
            obj.addPlayer(new Player("Ousmane", "Dembélé", 26, Positions.FWD, 11, "FC Barcelona"));
            obj.addPlayer(new Player("Lionel", "Messi", 36, Positions.FWD, 10, "FC Barcelona"));
            obj.addPlayer(new Player("Robert", "Lewandowski", 35, Positions.FWD, 9, "FC Barcelona"));

            // Saco una lista con todos los jugadores
            System.out.println(obj.readAllPlayers());

            // Hago una modificación en un jugador, y compruebo que los cambios se hayan aplicado.
            obj.updatePlayer(new Player("Lionel", "Messi", 37, Positions.MF, 15, "FC Barcelona"));
            System.out.println(obj.filterPlayersByName("Lionel"));

            // Hago diferences operaciones de búsqueda por filtrado, para comprobar que funcione correctamente.
            System.out.println("Jugadores que tengan 37 años de edad: " + obj.filterPlayersByAge(37));
            System.out.println("Jugadores que tengan de apellido Lewandowski: " + obj.filterPlayersByLastname("Lewandowski"));
            System.out.println("Jugadores que jueguen en el Real Madrid: " + obj.filterPlayersByTeam("Real Madrid"));
            System.out.println("Jugadores que jueguen como portero: " + obj.filterPlayersByPosition(Positions.GK));
            System.out.println("Jugadores que tengan 6 goles anotados esta temporada:" + obj.filterPlayersByGoals(6));

            // Busco los máximos goleadores de la liga.
            System.out.println("Máximos goleadores de la Liga:");
            System.out.println(obj.findTopScorers());

            // Busco el máximo goleador de un equipo.
            System.out.println("Máximo goleador del FC Barcelona: ");
            System.out.println(obj.findTopScorer("FC Barcelona"));

            // Busco jugadores que jueguen en una posición determinada.
            System.out.println(obj.findByPosition(Positions.MF));

            // Busco el promedio de edad de los jugadores de la liga.
            System.out.println("La edad media de los jugadores de la liga es de " + Math.round(obj.getAverageAge()) + " años de edad");

            obj.sortByAge();
        } catch (IOException exception) {
            throw new IOException(exception);
        }


        SoccerDAOFileXML prueba1 = new SoccerDAOFileXML("Jugadores.xml");
        try {
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
            prueba1.addPlayer(new Player("Ferland", "Mendy", 30, Positions.DEF, 1, "Real Madrid"));
            prueba1.deletePlayer(new Player("Alejandro", "Balde", 21, Positions.DEF, 0, "FC Barcelona"));
            prueba1.updatePlayer(new Player("Ferland", "Mendy", 30, Positions.DEF, 1, "FC Barcelona"));
            System.out.println("Top scorers");
            prueba1.findTopScorers().forEach(System.out::println);
            System.out.println("Top scorers from barsa");
            prueba1.findTopScorer("FC Barcelona").forEach(System.out::println);
            System.out.println("Top scorers from atleti");
            prueba1.findTopScorer("Atlético de Madrid").forEach(System.out::println);
            System.out.println("Edad promedio "+prueba1.getAverageAge());
            prueba1.findByPosition(Positions.MF).forEach(System.out::println);
            System.out.println("buscar jugador por nombre");

            prueba1.readPlayers("Iñaki").forEach(System.out::println);
            prueba1.sortByAge();
            prueba1.sortByTeam();
            prueba1.sortByLastname();
        } catch (IOException exception) {
            throw new IOException(exception);
        }

        //LLAMADAS AL SISTEMA
        generarArchivo("./","prueba1.txt");
        renombrarArchivo("./prueba1.txt","messi.txt");
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
            throw new IOException("no se pudo generar el archivo"+e);
        }
    }
    public static void renombrarArchivo(String ruta, String nombreArchivo) throws IOException {
        try{
            //representamos el fichero
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