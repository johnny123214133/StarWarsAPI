package com.swapi.starwarsapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity(name = "starship_master")
public class Starship {
    private static final double MONEY_MAX_VALUE = 92233720368547758.07;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message="name cannot be blank, empty, or null")
    @Column(nullable=false, unique=true)
    private String name;

    @NotBlank(message="model cannot be blank, empty, or null")
    private String model;

    // I couldn't get the money type to work,
    // so I'm verifying that the request is in the right format and range at the service level
    // I put tighter restrictions on it there since I don't even accept negative cost
    @NotNull(message="costInCredits cannot be null")
    @Column(name="cost_in_credits")
    private Double costInCredits;

    public Starship() {
    }

    public Starship(String name, String model, double costInCredits) {
        this.name = name;
        this.model = model;
        this.costInCredits = costInCredits;
    }

    @Override
    public String toString() {
        return "Starship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", costInCredits=" + costInCredits +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Starship starship)) return false;
        return getId() == starship.getId() &&
                Double.compare(getCostInCredits(), starship.getCostInCredits()) == 0 &&
                Objects.equals(getName(), starship.getName()) &&
                Objects.equals(getModel(), starship.getModel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getModel(), getCostInCredits());
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getCostInCredits() {
        return costInCredits;
    }

    public void setCostInCredits(double costInCredits) {
        this.costInCredits = costInCredits;
    }
}
