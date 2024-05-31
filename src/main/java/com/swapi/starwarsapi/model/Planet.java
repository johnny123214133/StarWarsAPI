package com.swapi.starwarsapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity(name = "galaxy_planet")
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message="name cannot be blank")
    @Column(nullable=false, unique=true)
    private String name;

    @NotBlank(message="climate cannot be blank")
    private String climate;

    @NotNull(message="population cannot be null")
    @Max(value = Integer.MAX_VALUE, message = "population cannot exceed integer max")
    @Min(value = 0, message = "population cannot be less than 0")
    private Integer population;

    public Planet() {
    }

    public Planet(String name, String climate, int population) {
        this.name = name;
        this.climate = climate;
        this.population = population;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", climate='" + climate + '\'' +
                ", population=" + population +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Planet planet)) return false;
        return getId() == planet.getId() &&
                getPopulation() == planet.getPopulation() &&
                Objects.equals(getName(), planet.getName()) &&
                Objects.equals(getClimate(), planet.getClimate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getClimate(), getPopulation());
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

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
