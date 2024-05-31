package com.swapi.starwarsapi.controller;

import com.swapi.starwarsapi.exceptions.ConflictException;
import com.swapi.starwarsapi.model.StarWarsCharacter;
import com.swapi.starwarsapi.service.StarWarsCharacterService;
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
 * This is the controller for the /characters uri.
 */
@RestController
@RequestMapping("/characters")
public class StarWarsCharacterController {
    @Autowired
    private StarWarsCharacterService starWarsCharacterService;

    /** Handler for post / request
     * @param character The character entity we are persisting (model.StarWarsCharacter)
     * @throws BadRequestException Exception thrown for custom request body validation
     * @throws ConflictException Custom exception and http response that obfuscates some internal server errors on duplicate or missing records
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@Valid  @RequestBody StarWarsCharacter character) throws BadRequestException, ConflictException {
        starWarsCharacterService.save(character);
    }

    /** Handler for get / request
     * @return Returns a list of all persisted characters
     */
    @GetMapping
    public List<StarWarsCharacter> findAll() {
        return starWarsCharacterService.findAll();
    }

    /** Handler for get /{id} request
     * @param id The id of the character we are searching for (integer greater than 0)
     * @return Returns the StarWarsCharacter if found, null if not
     */
    @GetMapping("/{id}")
    public Optional<StarWarsCharacter> findById(@PathVariable @NotNull @Min(1) Integer id) {
        return starWarsCharacterService.findById(id);
    }

    /** Handler for get /find request
     * @param name The name of the character we are searching for, found in the request's parameters (String not null, length > 0)
     * @return Returns the StarWarsCharacter if found, null if not
     */
    @GetMapping("/find")
    public Optional<StarWarsCharacter> findByName(@RequestParam @NotBlank String name) {
        return starWarsCharacterService.findByName(name);
    }

    /** Handler for put / request
     * @param character The StarWarsCharacter entity with the attribute values we want to override. Found in request body (model.StarWarsCharacter)
     * @throws BadRequestException Exception thrown for custom request body validation
     * @throws ConflictException Custom exception and http response that obfuscates some internal server errors on duplicate or missing records
     */
    @PutMapping
    public void updateByName(@Valid @RequestBody StarWarsCharacter character) throws BadRequestException, ConflictException {
        starWarsCharacterService.updateByName(character);
    }

    /** Handler for put /{id} request
     * @param id The id of the character we are modifying (integer greater than 0)
     * @param character The StarWarsCharacter entity with the attribute values we want to override. Found in request body (model.StarWarsCharacter)
     * @throws BadRequestException Exception thrown for custom request body validation
     * @throws ConflictException Custom exception and http response that obfuscates some internal server errors on duplicate or missing records
     */
    @PutMapping("/{id}")
    public void updateById(@PathVariable @NotNull @Min(1) int id,
                           @Valid @RequestBody StarWarsCharacter character) throws BadRequestException, ConflictException {
        starWarsCharacterService.updateById(id, character);
    }

    /** Handler for delete /delete request
     * @param name The name of the character we are modifying, found in the request's parameters (String not null, length > 0)
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
    public void delete(@RequestParam @NotBlank String name) {
        starWarsCharacterService.deleteByName(name);
    }

    /** Handler for delete /{id} request
     * @param id The id of the character we are deleting (integer greater than 0)
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @NotNull @Min(1) Integer id) {
        starWarsCharacterService.deleteById(id);
    }
}
