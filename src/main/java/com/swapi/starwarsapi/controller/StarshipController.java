package com.swapi.starwarsapi.controller;

import com.swapi.starwarsapi.exceptions.ConflictException;
import com.swapi.starwarsapi.model.Starship;
import com.swapi.starwarsapi.service.StarshipService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This is the controller for the /planets uri.
 */
@RestController
@RequestMapping("/starships")
public class StarshipController {
    @Autowired
    private StarshipService starshipService;

    /** Handler for post / request
     * @param starship The starship entity we are persisting (model.Starship)
     * @throws BadRequestException Exception thrown for custom request body validation
     * @throws ConflictException Custom exception and http response that obscures some internal server errors on duplicate or missing records
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@Valid @RequestBody Starship starship) throws BadRequestException, ConflictException {
        starshipService.save(starship);
    }

    /** Handler for get / request
     * @return Returns a list of all persisted starships
     */
    @GetMapping
    public List<Starship> findAll() {
        return starshipService.findAll();
    }

    /** Handler for get /{id} request
     * @param id The id of the starship we are searching for (integer greater than 0)
     * @return Returns the Starship if found, null if not
     */
    @GetMapping("/{id}")
    public Optional<Starship> findById(@PathVariable @NotNull @Min(1) int id) {
        return starshipService.findById(id);
    }

    /** Handler for get /find request
     * @param name The name of the starship we are searching for, found in the request's parameters (String not null, length > 0)
     * @return Returns the Starship if found, null if not
     */
    @GetMapping("/find")
    public Optional<Starship> findByName(@RequestParam @NotBlank String name) {
        return starshipService.findByName(name);
    }

    /** Handler for put /{id} request
     * @param id The id of the starship we are modifying (integer greater than 0)
     * @param starship The Starship entity with the attribute values we want to override. Found in request body (model.Starship)
     * @throws BadRequestException Exception thrown for custom request body validation
     * @throws ConflictException Custom exception and http response that obscures some internal server errors on duplicate or missing records
     */
    @PutMapping("/{id}")
    public void updateById(@PathVariable @NotNull @Min(1) int id, @Valid @RequestBody Starship starship)
            throws BadRequestException, ConflictException {
        starshipService.updateById(id, starship);
    }

    /** Handler for put / request
     * @param starship The Starship entity with the attribute values we want to override. Found in request body (model.Starship)
     * @throws BadRequestException Exception thrown for custom request body validation
     * @throws ConflictException Custom exception and http response that obscures some internal server errors on duplicate or missing records
     */
    @PutMapping
    public void update(@Valid @RequestBody Starship starship) throws BadRequestException, ConflictException {
        starshipService.updateByName(starship);
    }

    /** Handler for delete /{id} request
     * @param id The id of the starship we are deleting (integer greater than 0)
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @NotNull @Min(1) int id) {
        starshipService.deleteById(id);
    }

    /** Handler for delete /delete request
     * @param name The name of the starship we are modifying, found in the request's parameters (String not null, length > 0)
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
    public void delete(@RequestParam @NotBlank String name) {
        starshipService.deleteByName(name);
    }
}
