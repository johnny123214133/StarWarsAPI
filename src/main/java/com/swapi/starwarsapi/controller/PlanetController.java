package com.swapi.starwarsapi.controller;

import com.swapi.starwarsapi.exceptions.ConflictException;
import com.swapi.starwarsapi.model.Planet;
import com.swapi.starwarsapi.service.PlanetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This is the controller for the /planets uri.
 */
@RestController
@RequestMapping("/planets")
public class PlanetController {
    @Autowired
    private PlanetService planetService;

    /** Handler for post / request
     * @param planet The planet entity we are persisting (model.Planet)
     * @throws ConflictException Custom exception and http response that obscures some internal server errors on duplicate or missing records
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@Valid @RequestBody Planet planet) throws ConflictException {
        planetService.save(planet);
    }

    /** Handler for get / request
     * @return Returns a list of all persisted planets
     */
    @GetMapping
    public List<Planet> findAll() {
        return planetService.findAll();
    }

    /** Handler for get /{id} request
     * @param id The id of the planet we are searching for (integer greater than 0)
     * @return Returns the Planet if found, null if not
     */
    @GetMapping("/{id}")
    public Optional<Planet> findById(@PathVariable @NotNull @Min(1) int id) {
        return planetService.findById(id);
    }

    /** Handler for get /find request
     * @param name The name of the planet we are searching for, found in the request's parameters (String not null, length > 0)
     * @return Returns the Planet if found, null if not
     */
    @GetMapping("/find")
    public Optional<Planet> findByName(@RequestParam @NotBlank String name) {
        return planetService.findByName(name);
    }

    /** Handler for put /{id} request
     * @param id The id of the planet we are modifying (integer greater than 0)
     * @param planet The Planet entity with the attribute values we want to override. Found in request body (model.Planet)
     * @throws ConflictException Custom exception and http response that obscures some internal server errors on duplicate or missing records
     */
    @PutMapping("/{id}")
    public void update(@PathVariable @NotNull @Min(1) int id, @Valid @RequestBody Planet planet) throws ConflictException {
        planetService.updateById(id, planet);
    }

    /** Handler for put / request
     * @param planet The Planet entity with the attribute values we want to override. Found in request body (model.Planet)
     * @throws ConflictException Custom exception and http response that obscures some internal server errors on duplicate or missing records
     */
    @PutMapping
    public void update(@Valid @RequestBody Planet planet) throws ConflictException {
        planetService.updateByName(planet);
    }

    /** Handler for delete /{id} request
     * @param id The id of the planet we are deleting (integer greater than 0)
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @NotNull @Min(1) int id) {
        planetService.deleteById(id);
    }

    /** Handler for delete /delete request
     * @param name The name of the planet we are modifying, found in the request's parameters (String not null, length > 0)
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
    public void delete(@RequestParam @NotBlank String name) {
        planetService.deleteByName(name);
    }
}
