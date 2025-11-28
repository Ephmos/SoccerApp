-- 1) Crear el schema Players
DROP SCHEMA IF EXISTS "Players" CASCADE;
CREATE SCHEMA "Players";

-- 2) Crear el tipo ENUM dentro del schema Players
CREATE TYPE "Players".positions_enum AS ENUM ('GK', 'DEF', 'MF', 'FWD');

-- 3) Crear la tabla Player dentro del schema Players
CREATE TABLE "Players"."Player" (
                                    id          SERIAL PRIMARY KEY,
                                    name        VARCHAR(100)           NOT NULL,
                                    lastname    VARCHAR(100)           NOT NULL,
                                    age         INTEGER                NOT NULL,
                                    position    "Players".positions_enum NOT NULL,
                                    goalsNumber INTEGER                NOT NULL,
                                    team        VARCHAR(100)           NOT NULL
);
