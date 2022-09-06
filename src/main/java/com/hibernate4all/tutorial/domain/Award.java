package com.hibernate4all.tutorial.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Award {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private int year;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id")
    @JsonIgnore
    private Movie movie;

    public Long getId() {
        return id;
    }

    public Award setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Award setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Award setDescription(String description) {
        this.description = description;
        return this;
    }

    public Movie getMovie() {
        return movie;
    }

    public Award setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public int getYear() {
        return year;
    }

    public Award setYear(int year) {
        this.year = year;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Award other = (Award) o;
        if (id == null && other.getId() == null) {
            return Objects.equals(name, other.getName())
                    && Objects.equals(description,other.getDescription())
                    && Objects.equals(year,other.getYear());
        }

        return Objects.equals(this.id, other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(62);
    }

    @Override
    public String toString() {
        return "Award{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", description='" + description + '\'' +
                ", movie=" + movie +
                '}';
    }

}
