package com.swapi.starwarsapi.repository;

import com.swapi.starwarsapi.model.Starship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface StarshipRepository extends JpaRepository<Starship, Integer> {
    // Logic for findByName is already taken care of under the hood. No need for a native query,
    // so I hope my update and delete entity queries are impressive enough

    Optional<Starship> findByName(String name);

    @Modifying
    @Transactional
    @Query("update starship_master s set s.model=:model, s.costInCredits=:costInCredits where s.name=:name")
    void updateByName(
            @Param("name") String name,
            @Param("model") String model,
            @Param("costInCredits") Double costInCredits
    );

    @Modifying
    @Transactional
    @Query("update starship_master s set s.name=:name, s.model=:model, s.costInCredits=:costInCredits where s.id=:id")
    void updateById(
            @Param("id") Integer id,
            @Param("name") String name,
            @Param("model") String model,
            @Param("costInCredits") Double costInCredits
    );

    @Modifying
    @Transactional
    @Query("delete from starship_master s where s.name=:name")
    void deleteByName(@Param("name") String name);
}
