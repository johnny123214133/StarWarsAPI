package com.swapi.starwarsapi.service;

import com.swapi.starwarsapi.exceptions.ConflictException;
import com.swapi.starwarsapi.model.Starship;
import com.swapi.starwarsapi.repository.StarshipRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for Starship
 */
@Service
public class StarshipService {
    @Autowired
    private StarshipRepository starshipRepository;

    /** Service for creating a new Starship
     * @param starship Starship we want to persist
     * @throws BadRequestException Exception thrown for custom validation
     * @throws ConflictException Custom exception and http response that obscures some internal server errors on duplicate or missing records
     */
    public void save(Starship starship) throws BadRequestException, ConflictException {
        // I want to intercept records with duplicate names.
        // My 409 error is more appropriate than the auto-generated 500 error.
        this.validateName(starship.getName());

        // The annotation-driven validation only handles simple cases and data types.
        // They don't handle the abstraction over the costInCredits attribute
        this.validateCostInCredits(starship.getCostInCredits());
        starshipRepository.save(starship);
    }

    /** Service for selecting all persisted Starship entities
     * @return List of Starship entities we want to get
     */
    public List<Starship> findAll() {
        return starshipRepository.findAll();
    }

    /** Service for finding Starship by id
     * @param id id of Starship we want to find
     * @return Starship if exists, null if not
     */
    public Optional<Starship> findById(Integer id) {
        return starshipRepository.findById(id);
    }

    /** Service for finding Starship by name
     * @param name name of Starship we want to find
     * @return Starship if exists, null if not
     */
    public Optional<Starship> findByName(String name) {
        return starshipRepository.findByName(name);
    }

    /** Service for updating Starship by id
     * @param id id of Starship we want to modify
     * @param starship Starship that contains the data we want to modify
     * @throws BadRequestException Exception thrown for custom validation
     * @throws ConflictException Custom exception and http response that obscures some internal server errors on duplicate or missing records
     */
    public void updateById(Integer id, Starship starship) throws BadRequestException, ConflictException {
        // My 409 error is more appropriate than the auto-generated 500 error.
        this.validateStarshipExists(id);

        // The annotation-driven validation only handles simple cases and data types.
        // They don't handle the abstraction over the costInCredits attribute
        this.validateCostInCredits(starship.getCostInCredits());
        starshipRepository.updateById(id, starship.getName(), starship.getModel(), starship.getCostInCredits());
    }

    /** Service for updating Starship by name
     * @param starship Starship that contains the data we want to modify
     * @throws BadRequestException Exception thrown for custom validation
     * @throws ConflictException Custom exception and http response that obscures some internal server errors on duplicate or missing records
     */
    public void updateByName(Starship starship) throws BadRequestException, ConflictException {
        // My 409 error is more appropriate than the auto-generated 500 error.
        this.validateStarshipExists(starship.getName());

        // The annotation-driven validation only handles simple cases and data types.
        // They don't handle the abstraction over the costInCredits attribute
        this.validateCostInCredits(starship.getCostInCredits());
        starshipRepository.updateByName(starship.getName(), starship.getModel(), starship.getCostInCredits());
    }

    /** Service for deleting Starship by id
     * @param id id of Starship wa want to delete
     */
    public void deleteById(Integer id) {
        starshipRepository.deleteById(id);
    }

    /** Service for deleting Starship by name
     * @param name name of Starship wa want to delete
     */
    public void deleteByName(String name) {
        starshipRepository.deleteByName(name);
    }

    // Basic error handling is already taken care of by annotations in the model
    // More advanced error handling is done here

    /** Helper function to weed out duplicates
     * @param name name of Starship we want to validate
     * @throws ConflictException Custom exception and http response to catch duplicate records
     */
    private void validateName(String name) throws ConflictException {
        // Because I am treating names as unique, I need to make sure multiple records with the same name aren't created
        if (this.findByName(name).isPresent()) {
            throw new ConflictException("starship with name " + name + " already exists");
        }
    }

    /** Helper function to weed out missing Starships
     * @param name name of Starship we want to validate
     * @throws ConflictException Custom exception and http response to catch missing records
     */
    private void validateStarshipExists(String name) throws ConflictException {
        // I need to make sure the starship exists when I try updating it
        if (this.findByName(name).isEmpty()) {
            throw new ConflictException("starship with name " + name + " does not exist");
        }
    }

    /** Helper function to weed out missing Starships
     * @param id id of Starship we want to validate
     * @throws ConflictException Custom exception and http response to catch missing records
     */
    private void validateStarshipExists(int id) throws ConflictException {
        // I need to make sure the starship exists when I try updating it
        if (this.findById(id).isEmpty()) {
            throw new ConflictException("starship with id " + id + " does not exist");
        }
    }

    /** Helper function to verify format and value of costInCredits
     * @param costInCredits cost we want to validate
     * @throws BadRequestException Exception thrown for custom validation
     */
    private void validateCostInCredits(double costInCredits) throws BadRequestException {
        // Double class inherently has exactly 0 or 1 decimal points.
        // Postgres money type max value = Long.MAX_VALUE / 100. Verify the input double is less than that
        // I also want to catch and reject the case where a negative cost is given
        // Then, checking for positivity is simple enough, and
        // casting to string and doing a quick calculation against string length will verify the rest
        if (costInCredits > ((double) Long.MAX_VALUE) / 100) {
            throw new BadRequestException("costInCredits exceeds the maximum allowed credits");
        } else if (costInCredits < 0) {
            throw new BadRequestException("costInCredits should be greater than or equal to zero");
        }

        String cost = Double.toString(costInCredits);
        if (!cost.contains(".")) { return; }
        else if (cost.length() - cost.indexOf(".") <= 3) { return; }
        else {
            throw new BadRequestException("costInCredits is not in a valid monetary format");
        }
    }
}
