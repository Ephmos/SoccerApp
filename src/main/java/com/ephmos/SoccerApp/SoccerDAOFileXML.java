package com.ephmos.SoccerApp;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class SoccerDAOFileXML extends DefaultHandler implements SoccerDAO  {
    private List <Player> listaJugadores = null;
    private Player jugador = null;
    boolean enName = false;
    boolean enLastname= false;
    boolean enAge=false;
    boolean enPosition=false;
    boolean enGoalsNumber=false;
    boolean enTeam= false;



    @Override
    public Boolean isEmpty() {
        return null;
    }

    @Override
    public Boolean isFull() {
        return null;
    }

    @Override
    public void addPlayer(Player name) {

    }

    @Override
    public void deletePlayer(Player name) {

    }

    @Override
    public List<Player> readPlayers()  {
        List <Player> listaJugadores = null;
        Player player1 = null;

        return List.of();
    }
    public void startDocument()  throws SAXException {
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
    }*/
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("player")) {
            jugador = new Player();
        }
        if(qName.equalsIgnoreCase("name")) {
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
            System.out.println("\tFin Elemento:" + qName)
        }
    }
    ;
    @Override
    public List<Player> readPlayer(String name) throws IOException {
        return List.of();
    }

    @Override
    public void updatePlayer(Player player) throws IOException {

    }

    @Override
    public List<Player> findTopScorers() {
        return List.of();
    }

    @Override
    public Player findTopScorer(String team) {
        return null;
    }

    @Override
    public List<Player> findByPosition(Positions position) {
        return List.of();
    }

    @Override
    public double getAverageAge() {
        return 0;
    }

    @Override
    public double getAverageAge(Positions position) {
        return 0;
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

    }
    public static void main(String[] args) {

    }

}
