package com.swapi.starwarsapi.service;

import com.swapi.starwarsapi.exceptions.ConflictException;
import com.swapi.starwarsapi.model.Planet;
import com.swapi.starwarsapi.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for Planet
 */
@Service
public class PlanetService {
    @Autowired
    private PlanetRepository planetRepository;

    /** Service for creating a new Planet
     * @param planet Planet we want to persist
     * @throws ConflictException Custom exception and http response that obfuscates some internal server errors on duplicate or missing records
     */
    public void save(Planet planet) throws ConflictException {
        // I want to intercept records with duplicate names.
        // My 409 error is more appropriate than the auto-generated 500 error.
        this.validateName(planet.getName());
        planetRepository.save(planet);
    }

    /** Service for selecting all persisted Planet entities
     * @return List of Planet entities we want to get
     */
    public List<Planet> findAll() {
        return planetRepository.findAll();
    }

    /** Service for finding Planet by id
     * @param id id of Planet we want to find
     * @return Planet if exists, null if not
     */
    public Optional<Planet> findById(Integer id) {
        return planetRepository.findById(id);
    }

    /** Service for finding Planet by name
     * @param name name of Planet we want to find
     * @return Planet if exists, null if not
     */
    public Optional<Planet> findByName(String name) {
        return planetRepository.findByName(name);
    }

    /** Service for updating Planet by id
     * @param id id of Planet we want to modify
     * @param planet Planet that contains the data we want to modify
     * @throws ConflictException Custom exception and http response that obfuscates some internal server errors on duplicate or missing records
     */
    public void updateById(Integer id, Planet planet) throws ConflictException {
        // My 409 error is more appropriate than the auto-generated 500 error.
        this.validatePlanetExists(id);
        planetRepository.updateById(id, planet.getName(), planet.getClimate(), planet.getPopulation());
    }

    /** Service for updating Planet by name
     * @param planet Planet that contains the data we want to modify
     * @throws ConflictException Custom exception and http response that obfuscates some internal server errors on duplicate or missing records
     */
    public void updateByName(Planet planet) throws ConflictException {
        // My 409 error is more appropriate than the auto-generated 500 error.
        this.validatePlanetExists(planet.getName());
        planetRepository.updateByName(planet.getName(), planet.getClimate(), planet.getPopulation());
    }

    /** Service for deleting Planet by id
     * @param id id of Planet wa want to delete
     */
    public void deleteById(Integer id) {
        planetRepository.deleteById(id);
    }

    /** Service for deleting Planet by name
     * @param name name of Planet wa want to delete
     */
    public void deleteByName(String name) {
        planetRepository.deleteByName(name);
    }

    // Basic error handling is already taken care of by annotations in the model
    // More advanced error handling is done here

    /** Helper function to weed out duplicates
     * @param name name of Planet we want to validate
     * @throws ConflictException Custom exception and http response to catch duplicate records
     */
    private void validateName(String name) throws ConflictException {
        // Because I am treating names as unique, I need to make sure multiple records with the same name aren't created
        if (this.findByName(name).isPresent()) {
            throw new ConflictException("planet with name " + name + " already exists");
        }
    }

    /** Helper function to weed out missing Planets
     * @param name name of Planet we want to validate
     * @throws ConflictException Custom exception and http response to catch missing records
     */
    private void validatePlanetExists(String name) throws ConflictException {
        // Because I am treating names as unique, I need to make sure multiple records with the same name aren't created
        if (this.findByName(name).isEmpty()) {
            throw new ConflictException("planet with name " + name + " does not exist");
        }
    }

    /** Helper function to weed out missing Planets
     * @param id id of Planet we want to validate
     * @throws ConflictException Custom exception and http response to catch missing records
     */
    private void validatePlanetExists(int id) throws ConflictException {
        // Because I am treating names as unique, I need to make sure multiple records with the same name aren't created
        if (this.findById(id).isEmpty()) {
            throw new ConflictException("planet with id " + id + " does not exist");
        }
    }
}
