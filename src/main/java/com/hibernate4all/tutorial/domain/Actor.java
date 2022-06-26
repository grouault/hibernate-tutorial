package com.hibernate4all.tutorial.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String firstName;

    private LocalDate birthdate;

    @OneToMany(mappedBy = "actor", orphanRemoval = true, cascade = CascadeType.ALL)
    List<MovieActor> moviesActors = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public Actor setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Actor setName(String name) {
        this.name = name;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Actor setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Actor setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public List<MovieActor> getMoviesActors() {
        return moviesActors;
    }

    public Actor setMoviesActors(List<MovieActor> moviesActors) {
        this.moviesActors = moviesActors;
        return this;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Actor)) return false;
        Actor other = (Actor) o;

        if (this.id == null && other.getId() == null) {
            return Objects.equals(name, other.name)
                    && Objects.equals(firstName, other.firstName)
                    && Objects.equals(birthdate, other.birthdate);
        }

        return Objects.equals(id, other.id);

    }

    @Override
    public int hashCode() {
        return Objects.hash(182);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }

}
