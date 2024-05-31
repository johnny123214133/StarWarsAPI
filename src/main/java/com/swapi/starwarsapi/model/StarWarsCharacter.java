package com.swapi.starwarsapi.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Entity(name = "star_wars_character")
public class StarWarsCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false, unique=true)
    @NotBlank(message="name cannot be blank")
    private String name;

    @ManyToOne(targetEntity = Planet.class, fetch=FetchType.LAZY)
    @JoinColumn(name="home_planet", insertable=false, updatable=false)
    private Planet homePlanet;

    @Column(name="home_planet", nullable=false)
    @NotNull(message="home_planet cannot be null")
    private Integer homePlanetId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition="jsonb")
    @NotNull(message="starships can be an empty array, but not null")
    private List<Integer> starships;

    public StarWarsCharacter() {
    }

    public StarWarsCharacter(String name, int homePlanetId, List<Integer> starships) {
        this.name = name;
        this.homePlanetId = homePlanetId;
        this.starships = starships;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", homePlanet=" + homePlanetId +
                ", starships=" + starships.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StarWarsCharacter starWarsCharacter)) return false;
        return getId() == starWarsCharacter.getId() &&
                Objects.equals(getHomePlanetId(), starWarsCharacter.getHomePlanetId()) &&
                Objects.equals(getName(), starWarsCharacter.getName()) &&
                Objects.equals(getStarships(), starWarsCharacter.getStarships());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getHomePlanetId(), getStarships());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHomePlanetId() {
        return homePlanetId;
    }

    public void setHomePlanetId(int homePlanetId) {
        this.homePlanetId = homePlanetId;
    }

    public List<Integer> getStarships() {
        return starships;
    }

    public void setStarships(List<Integer> starships) {
        this.starships = starships;
    }

}
