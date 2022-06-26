package com.hibernate4all.tutorial.domain;

import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="movie_actor")
public class MovieActor {

    @EmbeddedId
    private MovieActorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("actorId")
    private Actor actor;

    private String character;

    private MovieActor() {
    }

    public MovieActor(Movie movie, Actor actor) {
        this.movie = movie;
        this.actor = actor;
        this.id = new MovieActorId(movie.getId(), actor.getId());
    }

    public MovieActorId getId() {
        return id;
    }

    public MovieActor setId(MovieActorId id) {
        this.id = id;
        return this;
    }

    public Movie getMovie() {
        return movie;
    }

    public MovieActor setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public Actor getActor() {
        return actor;
    }

    public MovieActor setActor(Actor actor) {
        this.actor = actor;
        return this;
    }

    public String getCharacter() {
        return character;
    }

    public MovieActor setCharacter(String character) {
        this.character = character;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieActorId)) return false;
        MovieActor that = (MovieActor) o;
        return Objects.equals(movie, that.movie)
                && Objects.equals(actor, that.actor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, actor);
    }

}
