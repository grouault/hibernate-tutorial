package com.hibernate4all.tutorial.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="Movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    @Version
    private short version;

    private String description;

    private Certification certification;

    // Dans Review, l'attribut Movie correspond à la clé étrangère
    // Qd on sauvegarde un movie, ca va sauvegarder les reviews de ce Movie
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Award> awards = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name="Movie_Genre",
        joinColumns = {@JoinColumn(name="movie_id")},
        inverseJoinColumns = {@JoinColumn(name="genre_id")}
    )
    List<Genre> genres = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MovieActor> moviesActors = new ArrayList<>();

    public void addActor(Actor actor, String character) {
        MovieActor movieActor = new MovieActor(this, actor).setCharacter(character);
        this.moviesActors.add(movieActor);
        actor.getMoviesActors().add(movieActor);
    }

    public Movie addAward(Award award) {
        if (award != null) {
            this.awards.add(award);
            award.setMovie(this);
        }
        return this;
    }

    public Movie addReview(Review review) {
        if (review != null) {
            this.reviews.add(review);
            review.setMovie(this);
        }
        return this;
    }

    public Movie addGenre(Genre genre) {
        if (genre != null) {
            this.genres.add(genre);
            genre.getMovies().add(this);
        }
        return this;
    }

    public Movie removeAward(Award award) {
        if (award != null) {
            this.awards.remove(award);
            award.setMovie(null);
        }
        return this;
    }

    public Movie removeActor(Actor actor) {
        if (actor != null) {
            for (Iterator<MovieActor> iter = moviesActors.iterator(); iter.hasNext();) {
                MovieActor movieActor = iter.next();
                if (movieActor.getMovie().equals(this) && movieActor.getActor().equals(actor)) {
                    iter.remove();
                    movieActor.getActor().getMoviesActors().remove(movieActor);
                    movieActor.setActor(null);
                    movieActor.setMovie(null);
                }
            }
        }
        return this;
    }

    public Movie removeReview(Review review) {
        if (review != null) {
            this.reviews.remove(review);
            review.setMovie(null);
        }
        return this;
    }

    public Movie removeGenre(Genre genre) {
        if (genre != null) {
            this.genres.remove(genre);
            genre.getMovies().remove(this);
        }
                return this;
    }

    public void updateActor(Actor actorBd, String character) {
        Optional<MovieActor> movieActor = this.moviesActors.stream().filter(
                ma -> ma.getActor().equals(actorBd) && ma.getMovie().equals(this)
        ).findFirst();
        if (!movieActor.isEmpty()) {
            movieActor.get().setCharacter(character);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Movie setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Movie setDescription(String description) {
        this.description = description;
        return this;
    }

    public Certification getCertification() {
        return certification;
    }

    public Movie setCertification(Certification certification) {
        this.certification = certification;
        return this;
    }

    public Set<Review> getReviews() {
        return Collections.unmodifiableSet(reviews);
    }

    public List<Award> getAwards() {
        return Collections.unmodifiableList(awards);
    }

    public List<Genre> getGenres() {
        return Collections.unmodifiableList(genres);
    }

    public List<MovieActor> getMoviesActors() {
        return Collections.unmodifiableList(moviesActors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie other = (Movie) o;
        if (this.getId() == null && other.getId() == null) {
            return Objects.equals(this.getName(), other.getName()) &&
                    Objects.equals(this.getDescription(), other.getDescription()) &&
                    Objects.equals(this.getCertification(), other.getCertification());
        }
        return this.getId() != null && Objects.equals(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(4);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", certification=" + certification +
                '}';
    }

}
