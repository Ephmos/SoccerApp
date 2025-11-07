package com.ephmos.SoccerApp;

import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SoccerDAOFileXML extends DefaultHandler implements SoccerDAO  {
    //VALORES QUE NECESITAREMOS
    private long maxBytes= 52428800;
    private String file;
    private List <Player> listaJugadores = null;
    private Player jugador = null;
    boolean enName = false;
    boolean enLastname= false;
    boolean enAge=false;
    boolean enPosition=false;
    boolean enGoalsNumber=false;
    boolean enTeam= false;
    //GETERS Y SETERS DE LAS LISTAS
    public List<Player> getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(List<Player> listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    public SoccerDAOFileXML(String file) {
        this.file = file;
    }

    @Override
    public boolean isEmpty() throws IOException {
        //creamos variable que nos dirá si esta o no Vacio
        boolean isEmpty=false;
        try {
            //creamos el archivo
            File file = new File(this.file);
            //comprobamos si existe(comprueba que no existe)
            if (!file.exists()){
                isEmpty = true;
            }
            //si existe comprobamos que no este vacio(se podria hacer comprobando el tamaño tambien)
            else{
                //abrimos un buffered reader que lee la linea
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    //si la primera linea esta vacia isEmpty pasaria a true
                    isEmpty= br.readLine() == null;
                }
            }
        } catch (IOException e) {
            //si se da un problema de lectura o escritura lo lanzamos
           throw new IOException(e);
        }
        //retorna el resultado de la variable
        return isEmpty;
    }

    @Override
    public boolean isFull() {
        //creamos la variable y la inicializamos a false
        boolean isFull = false;
        File file = new File(this.file);
        //si no exite no puede estar vacia
        if (!file.exists()){
            isFull = false;
        }
        //si el tamaño del archivo es igual o superior a maxBytes la variable pasa a true
        //file.length() retorna el tamaño en bytes
        if(file.length() >= maxBytes){
            isFull = true;
        }
        //retorna el valor
        return isFull;
    }

    @Override
    public void addPlayer(Player name) {
        //comprobamos que el fichero no este lleno
        File file = new File(this.file);
        if(!this.isFull()){
            //cargamos a la lista los jugadores con el metodo readPlayers
            listaJugadores=readAllPlayers();
            //añadimos al final el jugaodor que queremos añadir
            listaJugadores.add(name);
            //los cargamos al archivo
            exportPlayersToDataStorage(listaJugadores);
        }
    }

    @Override
    public void deletePlayer(Player name) {
        //cargamos a la lista los jugadores con el metodo readPlayers
        listaJugadores=readAllPlayers();
        //eliminamos jugador, es necesario crear un iterador para eliminarlo(lo vimos el año pasado en clase)
        for (Iterator<Player> it = listaJugadores.iterator(); it.hasNext(); ) {
            Player p = it.next();
            //si cuadra el nombre, apellido y edad (mala suerte seria que exitiesen dos jugadores iguales)
            if (p.getName().equals(name.getName()) && p.getName().equals(name.getName()) && p.getAge()==name.getAge()) {
                it.remove();
            }
        }
        //los cargamos al archivo
        exportPlayersToDataStorage(listaJugadores);
    }

    @Override
    public List<Player> readAllPlayers()  {

        //descomentar lo de abajo
       // List <Player> listaJugadores = null
        // Player player1 = null;
        // PARA HACER PRUEBAS->
        return listaJugadores;
    }
    /*public void startDocument() throws SAXException {
        listaJugadores = new ArrayList <Player> ();
        System.out.println("Comienzo del documento XML");
    }
    @Override
    public void endDocument() throws SAXException {
        System.out.println("Final del documento XML");
    }
    @Override
    /*public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        System.out.println("\tPrincipio Elemento:" + qName);
    }
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("player")) {
            jugador = new Player();
        }
        else if(qName.equalsIgnoreCase("name")) {
            enName=true;
        }
        else if (qName.equalsIgnoreCase("lastname")) {
            enLastname = true;
        }
        else if (qName.equalsIgnoreCase("age")) {
            enAge = true;
        }
        else if (qName.equalsIgnoreCase("position")) {
            enPosition = true;
        } else if (qName.equalsIgnoreCase("goalsnumber")) {
            enGoalsNumber = true;
        }
        else if (qName.equalsIgnoreCase("team")) {
            enTeam = true;
        }
    }
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equalsIgnoreCase("jugador")) {
            listaJugadores.add(jugador);
            System.out.println("\tFin Elemento:" + qName);
        }
    }*/

    @Override
    public List<Player> readPlayers(String name) throws IOException {
        return List.of();
    }

    @Override
    public void updatePlayer(Player name) throws IOException {
        //cargamos a la lista los jugadores con el metodo readPlayers
        listaJugadores=readAllPlayers();
        //actualizamos un jugador
        for (Player p:listaJugadores) {
            if (p.getName().equals(name.getName()) && p.getLastname().equals(name.getLastname())) {
                p.setAge(name.getAge());
                p.setPosition(name.getPosition());
                p.setGoalsNumber(name.getGoalsNumber());
                p.setTeam(name.getTeam());
            }
        }
        //los cargamos al archivo
        exportPlayersToDataStorage(listaJugadores);
    }

    @Override
    public List<Player> findTopScorers() {
        // variable para guardar el maximo goleador
        int numeroGoles=0;
        //lista para añadir las coincidencias con el maximo goleador
        List<Player> maximosGoleadores= new ArrayList<>();
        //cargamos a la lista los jugadores con el metodo readPlayers
        listaJugadores=readAllPlayers();
        //recorremos la lista para obtener el dato mas alto de goles
         for (Player p:listaJugadores) {
            if(p.getGoalsNumber()>numeroGoles){
                numeroGoles=p.getGoalsNumber();
            }
         }
         // buscamos en la lista los jugadores que tengan ese numero de goles
         for (Player p:listaJugadores) {
             if(p.getGoalsNumber()==numeroGoles){
                 maximosGoleadores.add(p);
             }
         }
        return maximosGoleadores;
    }

    @Override
    public List<Player> findTopScorer(String team) {
        // variable para guardar el maximo goleador
        int numeroGoles=0;
        //lista para añadir las coincidencias con el maximo goleador
        List<Player> maximosGoleadores= new ArrayList<>();
        //cargamos los jugadores
        listaJugadores=readAllPlayers();
        //recorremos la lista para obtener el dato mas alto de goles de el equipo
        for (Player p:listaJugadores) {
            if(p.getTeam().equals(team) && p.getGoalsNumber()>numeroGoles) {
                numeroGoles = p.getGoalsNumber();
            }
        }
        // buscamos en la lista los jugadores que tengan ese numero de goles
        for (Player p:listaJugadores) {
            if(p.getGoalsNumber()==numeroGoles && p.getTeam().equals(team)){
                maximosGoleadores.add(p);
            }
        }
        return maximosGoleadores;
    }

    @Override
    public List<Player> findByPosition(Positions position) {
        return List.of();
    }

    @Override
    public double getAverageAge() {
        //generamos una variable que almacenará las edades de los jugadores y
        // posteriormente almacenará la edad promedio
        double edadpromedio=0;
        //cargamos la lista
        listaJugadores= readAllPlayers();
        //recorremos la lista para almacenar las edades
        for (Player p:listaJugadores) {
            edadpromedio+=p.getAge();
        }
        //hacemos la division por el numero de jugadores para sacar la media
        edadpromedio=edadpromedio/listaJugadores.size();
        //retornamos la media
        return edadpromedio;
    }

    @Override
    public List<Player> getPlayersByPosition(Positions position) {
        //cargamos la lista
        listaJugadores= readAllPlayers();
        //generamos otra lista en la que almacenaremos los jugadores de la posicion determinada
        List<Player> playersByPosition= new ArrayList<>();
        //recorremos la lista para almacenar los jugadores que sean de una determinada posicion
        for (Player p:listaJugadores) {
            if (p.getPosition().equals(position)) {
                playersByPosition.add(p);
            }
        }
        //retornamos la lista de los jugadores por posicion
        return playersByPosition;
    }

    @Override
    public List<Player> sortBySurname() {
        return List.of();
    }

    @Override
    public List<Player> sortByAge() {
        return List.of();
    }

    @Override
    public List<Player> sortByTeam(String team) {
        return List.of();
    }

    @Override
    public void exportPlayersToDataStorage(List<Player> players) {
        //para probar simplemente va a leer
        for (Player player : players) {
            System.out.println(player.toString());
        }
    }
    public static void main(String[] args) {
        //MAIN DE PRUEBAS
        ArrayList<Player> players = new ArrayList<>();

        // FC Barcelona (5)
        players.add(new Player("Marc-André", "ter Stegen", 33, Positions.GK, 0, "FC Barcelona"));
        players.add(new Player("Ronald", "Araujo", 26, Positions.DEF, 1, "FC Barcelona"));
        players.add(new Player("Pedri", "González", 22, Positions.MF, 3, "FC Barcelona"));
        players.add(new Player("Lamine", "Yamal", 17, Positions.FWD, 7, "FC Barcelona"));
        players.add(new Player("Robert", "Lewandowski", 36, Positions.FWD, 19, "FC Barcelona"));

        // Real Madrid (5)
        players.add(new Player("Thibaut", "Courtois", 33, Positions.GK, 0, "Real Madrid"));
        players.add(new Player("Antonio", "Rüdiger", 32, Positions.DEF, 2, "Real Madrid"));
        players.add(new Player("Jude", "Bellingham", 21, Positions.MF, 19, "Real Madrid"));
        players.add(new Player("Vinícius", "Júnior", 25, Positions.FWD, 15, "Real Madrid"));
        players.add(new Player("Kylian", "Mbappé", 26, Positions.FWD, 21, "Real Madrid"));

        // Atlético de Madrid (5)
        players.add(new Player("Jan", "Oblak", 32, Positions.GK, 0, "Atlético de Madrid"));
        players.add(new Player("José María", "Giménez", 30, Positions.DEF, 1, "Atlético de Madrid"));
        players.add(new Player("Marcos", "Llorente", 30, Positions.MF, 6, "Atlético de Madrid"));
        players.add(new Player("Antoine", "Griezmann", 34, Positions.FWD, 16, "Atlético de Madrid"));
        players.add(new Player("Álvaro", "Morata", 32, Positions.FWD, 15, "Atlético de Madrid"));

        // Muestra por consola
        players.forEach(System.out::println);
        System.out.println("Total jugadores: " + players.size());
        //CREACION DE OBJETO
        // File file1 = new File("Jugadores.xml");
        //File file2 = new File("C:\\Users\\Klugy\\Desktop\\2ºDAM\\GITHUB\\SoccerApp\\src\\main\\resources\\Jugadores.xml");
        //System.out.println(file2.exists());
        SoccerDAOFileXML prueba1 = new SoccerDAOFileXML("C:\\Users\\Klugy\\Desktop\\2ºDAM\\GITHUB\\SoccerApp\\src\\main\\resources\\Jugadores.xml");
        //COMPROBAMOS SI EL FICHERO ESTA LLENO O VACIO
        System.out.println("Esta llena?");
        System.out.println(prueba1.isFull());
        try {
            System.out.println("Esta vacia?");
            System.out.println(prueba1.isEmpty());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //CARGAMOS LA LISTA
        prueba1.setListaJugadores(players);
        //COMPROBAMOS QUE SE HAN CARGADO
        for (Player p :prueba1.getListaJugadores()) {
            System.out.println(p);
        }
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
        prueba1.getPlayersByPosition(Positions.MF).forEach(System.out::println);


    }



}
