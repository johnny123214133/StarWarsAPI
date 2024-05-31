package com.swapi.starwarsapi.repository;

import com.swapi.starwarsapi.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PlanetRepository extends JpaRepository<Planet, Integer> {
    // Logic for findByName is already taken care of under the hood. No need for a native query,
    // so I hope my update and delete entity queries are impressive enough

    Optional<Planet> findByName(String name);

    @Modifying
    @Transactional
    @Query("Update galaxy_planet p set p.climate = :climate, p.population = :population where p.name=:name")
    void updateByName(
            @Param("name") String name,
            @Param("climate") String climate,
            @Param("population") Integer population
    );

    @Modifying
    @Transactional
    @Query("Update galaxy_planet p set p.name=:name, p.climate = :climate, p.population = :population where p.id=:id")
    void updateById(
            @Param("id") Integer id,
            @Param("name") String name,
            @Param("climate") String climate,
            @Param("population") Integer population
    );

    @Modifying
    @Transactional
    @Query("delete from galaxy_planet p where p.name=:name")
    void deleteByName(@Param("name") String name);
}
