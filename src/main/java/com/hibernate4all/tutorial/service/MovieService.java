package com.hibernate4all.tutorial.service;

import com.hibernate4all.tutorial.domain.Actor;
import com.hibernate4all.tutorial.domain.Genre;
import com.hibernate4all.tutorial.domain.Movie;
import com.hibernate4all.tutorial.domain.Review;
import com.hibernate4all.tutorial.repository.ActorRepository;
import com.hibernate4all.tutorial.repository.MovieRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    public Movie getMovie(Long id) {
        Movie movie = movieRepository.find(id);
        // Movie proxy = movieRepository.getReference(id);
        // Movie  movie = (Movie) Hibernate.unproxy(proxy);
        return movie;
    }

    /**
     * associe un acteur à un film
     *
     * actor: en création ou modification
     * movie : en création ou modification
     *
     * @param movie
     * @param actor
     * @param character
     * @return
     */
    @Transactional
    public Movie addMovieActor(Movie movie, Actor actor, String character) {
        Actor actorDb = actor.getId() != null ? actorRepository.find(actor.getId()) : actorRepository.persist(actor);
        Movie movieDb = movie.getId() != null ? movieRepository.find(movie.getId()) : movieRepository.persist(movie);
        movieDb.addActor(actorDb, character);
        return movieDb;
    }

    /**
     * crée un film
     * crée un acteur ou associe l'acteur au film s'il existe
     *
     * @param movie
     * @param actor
     * @param character
     * @return
     */
    @Transactional
    public Movie addMovieActorCascade(Movie movie, Actor actor, String character) {
        Actor actorDb = actor.getId() != null ? actorRepository.find(actor.getId()) : actorRepository.persist(actor);
        movie.addActor(actorDb, character);
        return movieRepository.persist(movie);
    }

    @Transactional
    public void addReview(Long idMovie, Review review) {
        Movie movieBd = movieRepository.getReference(idMovie);
        movieBd.addReview(review);
    }

    public List<Movie> getMovies(){
        List<Movie> movies = movieRepository.getAll();
        return movies;
    }

    public Movie persist(Movie movie){
        return movieRepository.persist(movie);
    }

    public Movie persist(Movie movie, Actor actor, String character) {
            return movieRepository.persistCascadeWithActor(movie, actor, character);
    }

    @Transactional
    public void removeGenre(Long idMovie, Genre genre) {
        Movie movie = movieRepository.getReference(idMovie);
        movie.removeGenre(genre);
    }

    public Boolean removeMovie(Long id) {
        return movieRepository.remove(id);
    }

    @Transactional
    public void removeActor(Movie movie, Actor actor) {
        Movie movieBd = movieRepository.find(movie.getId());
        Actor actorBd = actorRepository.find(actor.getId());
        movieBd.removeActor(actorBd);
    }

    @Transactional
    public List<Movie> removeAndGetAll(Long id){
        movieRepository.remove(id);
        return movieRepository.getAll();
    }

    @Transactional
    public Movie removeThenAddMovie() {
        movieRepository.remove(-2L);
        Movie movie = new Movie();
        movie.setName("Memento");
        movie.setDescription("memento v2");
        movieRepository.persist(movie);
        return movie;
    }


    @Transactional
    public void updateDescription(Long id, String description) {
        Movie movie = movieRepository.find(id);
        movie.setDescription(description);
    }

    @Transactional
    public Movie updateActor(Movie movie, Actor actor, String character) {
        Movie movieBd = movieRepository.find(movie.getId());
        Actor actorBd = actorRepository.find(actor.getId());
        movieBd.updateActor(actorBd, character);
        return movieBd;
    }

    public Optional<Movie> updateMovie(Movie movie) {
        return movieRepository.update(movie);
    }


}
