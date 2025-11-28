package com.ephmos.SoccerApp;

import com.ephmos.SoccerApp.dao.SoccerDAO;
import com.ephmos.SoccerApp.objects.Player;
import com.ephmos.SoccerApp.others.Positions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class SoccerDAOFileXML extends DefaultHandler implements SoccerDAO {
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


    // GETTERS Y SETTERS LISTA JUGADORES
    public List<Player> getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(List<Player> listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    //constructor
    public SoccerDAOFileXML(String file) {
        this.file = file;
    }

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

    public boolean isFull() {
        //creamos la variable y la inicializamos a false
        boolean isFull = false;
        try{

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
        }
        catch (SecurityException exception) {
            throw new SecurityException("Ha ocurrido una excepción de seguridad", exception);
        }
        //retorna el valor
        return !isFull;
    }

    @Override
    public void addPlayer(Player name) {
        //comprobamos que el fichero no este lleno
        File file = new File(this.file);
        if(this.isFull()){
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
    public List<Player> readAllPlayers() {
        // Creamos un SAXParserFactory que se utilizarán para procesar el XML
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware(false);//Specifies that the parser produced by this code will provide support for XML namespaces
        try {
            //Creamos un SAXParser, Este parser es el encargado de leer el archivo XML evento por evento
            //(inicio/fin de elementos, caracteres...)
            SAXParser saxParser = saxParserFactory.newSAXParser();
            //creamos el tipo File para usarlo en el parser
            File file= new File(this.file);
            // Usamos 'this' porque nuestra clase ya extiende DefaultHandler
            saxParser.parse(file, this);
            // Retornamos la lista que se ha cargado durante el parseo
            return listaJugadores;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            //return new ArrayList<>(); // Retornamos lista vacía en caso de error
            throw new IOException("error de lectura o escritura",e); //lanzamos error de lectura o escritura
        }
    }

    @Override
    public void startDocument() {
        //cuando comience el documento creamos la lista
        listaJugadores = new ArrayList<Player>();
    }

    @Override
    public void endDocument() {
        //añadimos un mensaje de "Fin de documento"
        //lo he comentado porque sino cada vez que lee el documento lo imprime
        //System.out.println("Fin de documento");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        //este metodo se usa cada vez que se comienza un elemento
        //cuando se crea un "player inicia un objeto de tipo Player para asignarle sus atributos"
        if (qName.equalsIgnoreCase("player")) {
            jugador = new Player();
        }
        //los metodos de abajo se utilizan para saber cuando estamos en un campo determinado
        else if(qName.equalsIgnoreCase("name")) {
            enName = true;
        }
        else if (qName.equalsIgnoreCase("lastname")) {
            enLastname = true;
        }
        else if (qName.equalsIgnoreCase("age")) {
            enAge = true;
        }
        else if (qName.equalsIgnoreCase("position")) {
            enPosition = true;
        }
        else if (qName.equalsIgnoreCase("goalsNumber")) {
            enGoalsNumber = true;
        }
        else if (qName.equalsIgnoreCase("team")) {
            enTeam = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        //cuando se acabe el elemento  "player" queremos añadir el jugador que hemos generado en memoria
        if (qName.equalsIgnoreCase("player")) {
            listaJugadores.add(jugador);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // Esta funcion se invoca cuando el parser encuentra CONTENIDO DE TEXTO dentro de etiquetas
        // Parámetros:
        // - ch: array de caracteres que contiene el contenido
        // - start: posición de inicio del contenido en el array
        // - length: cantidad de caracteres a procesar

        // Convierte el array de caracteres(ch) a un String desde la posición 'start' con 'length' caracteres
        // elimina espacios en blanco al inicio y final con trim()
        String contenido = new String(ch, start, length).trim();

        // Saltamos los elementos vacíos (espacios, saltos de línea, etc.)
        if (contenido.isEmpty()) {
            return;
        }
        //añadimos los atributos al objeto y ponemos false para saber donde estamos
        try {
            if (enName) {
                jugador.setName(contenido);
                enName = false;
            } else if (enLastname) {
                jugador.setLastname(contenido);
                enLastname = false;
            } else if (enAge) {
                jugador.setAge(Integer.parseInt(contenido)); // solo aquí número
                enAge = false;
            } else if (enPosition) {
                // Normaliza por si viniera en minúsculas en otros XML
                jugador.setPosition(Positions.valueOf(contenido.toUpperCase()));
                enPosition = false;
            } else if (enGoalsNumber) {
                jugador.setGoalsNumber(Integer.parseInt(contenido)); // solo aquí número
                enGoalsNumber = false;
            } else if (enTeam) {
                jugador.setTeam(contenido);
                enTeam = false;
            }
        }//excepcion por si falla algun campo saber porque
        catch (IllegalArgumentException ex) {
            //para saber el campo en el que falla
            String campo = enAge ? "age" : enGoalsNumber ? "goalsNumber" : enPosition ? "position" :
                    enName ? "name" : enLastname ? "lastname" : enTeam ? "team" : "desconocido";
            //sacamos la excepcion
            throw new SAXException("Valor inválido en el campo: "+ campo +" contenido: " + contenido, ex);
        }
    }

    public List<Player> readPlayers(String name) {
        //cargamos los jugadores en la lista
        listaJugadores=readAllPlayers();
        //creamos una nueva lista para los jugadores creados
        List Jugador_buscado = new ArrayList();
        //recorremos la lista buscando coincidencias por nombre
        for (Player p: listaJugadores) {
            //si se da la condicion añadimos el jugador a la lista
            if (p.getName().equals(name)) {
                Jugador_buscado.add(p);
            }
        }
        //retornamos la lista
        return Jugador_buscado;
    }

    @Override
    public void updatePlayer(Player name) {
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
    public List<Player> findTopScorers() throws IOException {
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
    public List<Player> findTopScorer(String team) throws IOException {
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
    public List<Player> findByPosition(Positions position) throws IOException {
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
    public double getAverageAge() throws IOException {
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
    public int getPlayersByPosition(Positions position) throws IOException {
        //variable para el numero de jugadores
        int numeroJugadores=0;
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
        //asignamos el valor
        numeroJugadores=playersByPosition.size();
        //retornamos el numero de jugadores
        return numeroJugadores;
    }

    @Override
    public void sortByLastname() {
        //cargamos la lista
        listaJugadores =readAllPlayers();
        //usamos el comparator para ordenar
        listaJugadores.sort(Comparator.comparing(Player::getLastname));
        //retornamos la lista
        exportPlayersToDataStorage(listaJugadores);
    }

    @Override
    public void sortByAge() throws IOException {
        listaJugadores =readAllPlayers();
        listaJugadores.sort(Comparator.comparing(Player::getAge));
        exportPlayersToDataStorage(listaJugadores);
    }

    @Override
    public void sortByTeam() throws IOException {
        listaJugadores =readAllPlayers();
        listaJugadores.sort(Comparator.comparing(Player::getTeam));
        exportPlayersToDataStorage(listaJugadores);
    }

    @Override
    public void exportPlayersToDataStorage(List<Player> players) {
        try {
            // Crear la fábrica y el constructor DOM para construir el documento XML
            //DOM significa Document Object Model
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Crear un nuevo documento XML vacío en memoria
            Document document = builder.newDocument();
            // Crear el elemento raíz <players> contendrá todos los players
            Element root = document.createElement("players");
            //Colocar el elemento raíz dentro del documento
            document.appendChild(root);

            // Para cada jugador, crear la estructura XML <player> con sus hijos
            //recorremos la lista pasada por parametro
            for(Player player : players) {
                //Creamos el elemento que agrupa los campos del jugador
                Element playerElement = document.createElement("player");
                //señalamos que es un hijo de "players"
                root.appendChild(playerElement);

                //creamos el campo "name" del jugador
                Element name = document.createElement("name");
                //escribimos el texto
                name.appendChild(document.createTextNode(player.getName()));
                //señalamos que es un hijo de "player"
                playerElement.appendChild(name);


                //HACEMOS LOS MISMO PARA TODOS LOS CAMPOS


                Element lastname = document.createElement("lastname");
                lastname.appendChild(document.createTextNode(player.getLastname()));
                playerElement.appendChild(lastname);

                Element age = document.createElement("age");
                age.appendChild(document.createTextNode(String.valueOf(player.getAge())));
                playerElement.appendChild(age);

                Element position = document.createElement("position");
                position.appendChild(document.createTextNode(player.getPosition().toString()));
                playerElement.appendChild(position);

                Element goalsNumber = document.createElement("goalsNumber");
                goalsNumber.appendChild(document.createTextNode(String.valueOf(player.getGoalsNumber())));
                playerElement.appendChild(goalsNumber);

                Element team = document.createElement("team");
                team.appendChild(document.createTextNode(player.getTeam()));
                playerElement.appendChild(team);
            }

            // Crear el transformador que convertirá el documento DOM a archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            //la salida al fichero
            Transformer transformer = transformerFactory.newTransformer();
            // Configurar propiedades para formato legible e indentado
            //codificacion UTF-8
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            //que indentr
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // Nivel de indentación
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            // Crear fuente DOM y resultado de archivo
            DOMSource source = new DOMSource(document);
            //Destino de la transformación
            StreamResult result = new StreamResult(this.file);
            // Escribir el contenido al archivo XML (sobrescribiendo el archivo existente)
            transformer.transform(source, result);
            //errores
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
            System.err.println("Error escribiendo el archivo XML.");
        }
    }
}
