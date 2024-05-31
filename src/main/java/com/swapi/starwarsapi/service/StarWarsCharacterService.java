package com.swapi.starwarsapi.service;

import com.swapi.starwarsapi.model.StarWarsCharacter;
import com.swapi.starwarsapi.repository.StarWarsCharacterRepository;
import com.swapi.starwarsapi.exceptions.ConflictException;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for StarWarsCharacter
 */
@Service
public class StarWarsCharacterService {
    private static final Logger log = LoggerFactory.getLogger(StarWarsCharacterService.class);
    @Autowired
    private StarWarsCharacterRepository starWarsCharacterRepository;
    @Autowired
    private StarshipService starshipService;
    @Autowired
    private PlanetService planetService;

    /** Service for creating a new StarWarsCharacter
     * @param character StarWarsCharacter we want to persist
     * @throws BadRequestException Exception thrown for custom validation
     * @throws ConflictException Custom exception and http response that obfuscates some internal server errors on duplicate or missing records
     */
    public void save(StarWarsCharacter character) throws BadRequestException, ConflictException {
        // My 409 error is more appropriate than the auto-generated 500 errors for character name and homePlanetId
        // I then verify the starship array is properly formatted and entities for each key exist
        this.validateName(character.getName());
        this.validateHomePlanetId(character.getHomePlanetId());
        this.validateStarships(character.getStarships());
        starWarsCharacterRepository.save(character);
    }

    /** Service for selecting all persisted StarWarsCharacter entities
     * @return List of StarWarsCharacter entities we want to get
     */
    public List<StarWarsCharacter> findAll() {
        return starWarsCharacterRepository.findAll();
    }

    /** Service for finding StarWarsCharacter by id
     * @param id id of StarWarsCharacter we want to find
     * @return StarWarsCharacter if exists, null if not
     */
    public Optional<StarWarsCharacter> findById(Integer id) {
        return starWarsCharacterRepository.findById(id);
    }

    /** Service for finding StarWarsCharacter by name
     * @param name name of StarWarsCharacter we want to find
     * @return StarWarsCharacter if exists, null if not
     */
    public Optional<StarWarsCharacter> findByName(String name) {
        return starWarsCharacterRepository.findByName(name);
    }

    /** Service for updating StarWarsCharacter by id
     * @param id id of StarWarsCharacter we want to modify
     * @param character StarWarsCharacter that contains the data we want to modify
     * @throws BadRequestException Exception thrown for custom validation
     * @throws ConflictException Custom exception and http response that obfuscates some internal server errors on duplicate or missing records
     */
    public void updateById(Integer id, StarWarsCharacter character) throws BadRequestException, ConflictException {
        // Verify that the character to update exists
        this.validateCharacterExists(id);

        // Verify that the starships array is in the expected format, then update the record
        // The annotation-driven validation only handles simple cases and data types
        this.validateStarships(character.getStarships());
        starWarsCharacterRepository.updateById(
            id,
            character.getName(),
            character.getHomePlanetId(),
            character.getStarships()
        );
    }

    /** Service for updating StarWarsCharacter by name
     * @param character StarWarsCharacter that contains the data we want to modify
     * @throws BadRequestException Exception thrown for custom validation
     * @throws ConflictException Custom exception and http response that obfuscates some internal server errors on duplicate or missing records
     */
    public void updateByName(StarWarsCharacter character) throws BadRequestException, ConflictException {
        // Verify that the character to update exists
        this.validateCharacterExists(character.getName());

        // Verify that the starships array is in the expected format, then update the record
        // The annotation-driven validation only handles simple cases and data types
        this.validateStarships(character.getStarships());
        starWarsCharacterRepository.updateByName(
            character.getName(),
            character.getHomePlanetId(),
            character.getStarships()
        );
    }

    /** Service for deleting StarWarsCharacter by id
     * @param id id of StarWarsCharacter wa want to delete
     */
    public void deleteById(Integer id) {
        starWarsCharacterRepository.deleteById(id);
    }

    /** Service for deleting StarWarsCharacter by name
     * @param name name of StarWarsCharacter wa want to delete
     */
    public void deleteByName(String name) {
        starWarsCharacterRepository.deleteByName(name);
    }

    // Basic error handling is already taken care of by annotations in the model
    // More advanced error handling is done here

    /** Helper function to weed out duplicates
     * @param name name of StarWarsCharacter we want to validate
     * @throws ConflictException Custom exception and http response to catch duplicate records
     */
    private void validateName(String name) throws ConflictException {
        // Because I am treating names as unique, I need to make sure multiple records with the same name aren't created
        if (this.findByName(name).isPresent()) {
            throw new ConflictException("character with name " + name + " already exists");
        }
    }

    /** Helper function to weed out missing StarWarsCharacters
     * @param name name of StarWarsCharacter we want to validate
     * @throws ConflictException Custom exception and http response to catch missing records
     */
    private void validateCharacterExists(String name) throws ConflictException {
        // Because I am treating names as unique, I need to make sure multiple records with the same name aren't created
        if (this.findByName(name).isEmpty()) {
            throw new ConflictException("character with name " + name + " does not exist");
        }
    }

    /** Helper function to weed out missing StarWarsCharacters
     * @param id id of StarWarsCharacter we want to validate
     * @throws ConflictException Custom exception and http response to catch missing records
     */
    private void validateCharacterExists(int id) throws ConflictException {
        // Because I am treating names as unique, I need to make sure multiple records with the same name aren't created
        if (this.findById(id).isEmpty()) {
            throw new ConflictException("character with id " + id + " does not exist");
        }
    }

    /** Helper function to verify format and value of homePlanetId
     * @param homePlanetId homePlanetId we want to verify value and type for
     * @throws BadRequestException Exception thrown for bad Json
     * @throws ConflictException Exception thrown for custom validation
     */
    private void validateHomePlanetId(int homePlanetId) throws BadRequestException, ConflictException {
        // I think catching and handling the exception for a missing planet here is better than relying on
        // the built-in internal server error whose error message exposes database details...

        // make sure the homePlanetId is in a valid range of values
        // then verify a planet exists with that id
        if (homePlanetId <= 0) {
            throw new BadRequestException("homePlanetId must be a positive integer");
        } else if (planetService.findById(homePlanetId).isEmpty()) {
            throw new ConflictException("planet with id " + homePlanetId + " does not exist");
        }
    }

    /** Helper function to verify format and value of starships
     * @param starships starships whose structure and values we want to validate
     * @throws BadRequestException Exception thrown for custom format validation
     * @throws ConflictException Exception thrown for catching missing starships
     */
    private void validateStarships(List<Integer> starships) throws BadRequestException, ConflictException {
        // starship references are stored as a json array of starship IDs
        // incoming arrays should be checked for valid id format and for the existence of their correlated entities
        if (starships == null) {
            throw new BadRequestException("starships cannot be null");
        }
        for (int starshipId : starships) {
            if (starshipId < 1) {
                throw new BadRequestException("starships must contain positive integers");
            }
            else if (starshipService.findById(starshipId).isEmpty()) {
                throw new ConflictException("starship with id " + starshipId + "does not exist");
            }
        }
    }
}
