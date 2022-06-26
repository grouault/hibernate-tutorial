package com.hibernate4all.tutorial.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MovieActorId implements Serializable {

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name= "actor_id")
    private Long actorId;

    public MovieActorId() {
    }

    public MovieActorId(Long movieId, Long actorId) {
        this.movieId = movieId;
        this.actorId = actorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof MovieActorId)) {
            return false;
        }

        MovieActorId other = (MovieActorId) o;
        return Objects.equals(movieId, other.movieId) && Objects.equals(actorId, other.actorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, actorId);
    }

}
