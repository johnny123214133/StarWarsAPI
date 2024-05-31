package com.swapi.starwarsapi;

import com.swapi.starwarsapi.model.Planet;
import com.swapi.starwarsapi.model.StarWarsCharacter;
import com.swapi.starwarsapi.model.Starship;
import com.swapi.starwarsapi.repository.PlanetRepository;
import com.swapi.starwarsapi.repository.StarWarsCharacterRepository;
import com.swapi.starwarsapi.repository.StarshipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class StarWarsApiApplication {

    private static final Logger log = LoggerFactory.getLogger(StarWarsApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StarWarsApiApplication.class, args);
    }

    // The demo below enabled my own quick testing in Postman. Use it if you'd like
/*
    @Bean
    public CommandLineRunner demo(PlanetRepository planetRepository, StarWarsCharacterRepository starWarsCharacterRepository, StarshipRepository starshipRepository) {
        return (args) -> {
            Planet planet1 = new Planet("Tatooine", "Desert", 1000000);
            Planet planet2 = new Planet("Corellia", "Temperate", 3000000);
            Starship ship1 = new Starship("Millennium Falcon", "YT-1300F light freighter", 10000);
            Starship ship2 = new Starship("Stolen Shuttle", "Lambda-class T-4a shuttle", 1000000.54);
            Integer[] ships = {1};
            StarWarsCharacter character1 = new StarWarsCharacter("Luke Skywalker", 1, Arrays.asList(ships));
            StarWarsCharacter character2 = new StarWarsCharacter("Han Solo", 2, Arrays.asList(ships));

            planetRepository.save(planet1);
            planetRepository.save(planet2);

            starshipRepository.save(ship1);
            starshipRepository.save(ship2);

            starWarsCharacterRepository.save(character1);
            starWarsCharacterRepository.save(character2);

            log.info("findAll(), expect 2 planets");
            log.info("-------------------------------");
            for (Planet planet : planetRepository.findAll()) {
                log.info(planet.toString());
            }
            log.info("\n");

            log.info("findByName(name), expect 1 planet");
            log.info("-------------------------------");
            log.info(planetRepository.findByName("Tatooine").toString());
            log.info("\n");

            log.info("findAll(), expect 2 characters");
            log.info("-------------------------------");
            for (StarWarsCharacter character : starWarsCharacterRepository.findAll()) {
                log.info(character.toString());
            }
            log.info("\n");

            log.info("findAll(), expect 2 starships");
            log.info("-------------------------------");
            for (Starship starship : starshipRepository.findAll()) {
                log.info(starship.toString());
            }
            log.info("\n");
        };

    }
*/
}
