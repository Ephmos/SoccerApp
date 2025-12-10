-- 1) Crear el schema soccerdb
DROP SCHEMA IF EXISTS soccerdb CASCADE;
CREATE SCHEMA soccerdb;

-- 2) Crear el tipo ENUM dentro del schema soccerdb
CREATE TYPE soccerdb.positions_enum AS ENUM('GK', 'DEF', 'MF', 'FWD');

-- 3) Crear la tabla de equipos dentro de soccerdb
CREATE TABLE soccerdb.teams (
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    creationDate DATE NOT NULL
);

-- 4) Crear la tabla de jugadores dentro de soccerdb
CREATE TABLE soccerdb.players (
                                  id          SERIAL PRIMARY KEY,
                                  name        VARCHAR(100)           NOT NULL,
                                  lastname    VARCHAR(100)           NOT NULL,
                                  age         INTEGER                NOT NULL,
                                  position    soccerdb.positions_enum NOT NULL,
                                  goalsNumber INTEGER                NOT NULL,
                                  teamid      SERIAL REFERENCES soccerdb.teams(id)
);
