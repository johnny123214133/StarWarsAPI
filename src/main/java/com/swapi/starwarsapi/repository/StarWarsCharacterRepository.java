package com.swapi.starwarsapi.repository;

import com.swapi.starwarsapi.model.StarWarsCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface StarWarsCharacterRepository extends JpaRepository<StarWarsCharacter, Integer> {
    // Logic for findByName is already taken care of under the hood. No need for a native query,
    // so I hope my update and delete entity queries are impressive enough

    Optional<StarWarsCharacter> findByName(String name);

    @Modifying
    @Transactional
    @Query("update star_wars_character s set s.homePlanetId=:homePlanetId, s.starships=:starships where s.name=:name")
    void updateByName(
            @Param("name") String name,
            @Param("homePlanetId") Integer homePlanetId,
            @Param("starships") List<Integer> starships
    );

    @Modifying
    @Transactional
    @Query("update star_wars_character s set s.name=:name, s.homePlanetId=:homePlanetId, s.starships=:starships where s.id=:id")
    void updateById(
            @Param("id") Integer id,
            @Param("name") String name,
            @Param("homePlanetId") Integer homePlanetId,
            @Param("starships") List<Integer> starships
    );

    @Modifying
    @Transactional
    @Query("delete from star_wars_character s where s.name=:name")
    void deleteByName(@Param("name") String name);

}
