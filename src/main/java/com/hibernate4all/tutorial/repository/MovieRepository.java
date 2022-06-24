package com.hibernate4all.tutorial.repository;

import com.hibernate4all.tutorial.domain.Movie;
import com.hibernate4all.tutorial.domain.MovieDetails;
import com.hibernate4all.tutorial.domain.Review;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieRepository.class);

    @PersistenceContext
    EntityManager entityManager;


    public Movie find(Long id){
        Movie result = entityManager.find(Movie.class,id);
        LOGGER.trace("entityManager.contains() : " + entityManager.contains(result));
        return result;
    }

    public List<Movie> getAll(){
        return entityManager.createQuery("from Movie", Movie.class).getResultList();
    }

    public Movie getReference(Long id) {
        Movie proxy = entityManager.getReference(Movie.class, id);
        return proxy;
    }

    @Transactional
    public Movie persist(Movie movie){
        LOGGER.trace("entityManager.contains() : " + entityManager.contains(movie));
        entityManager.persist(movie);
        return movie;
    }

    @Transactional
    public void addMovieDetails(MovieDetails movieDetails, Long idMovie) {
        Movie movieRef = getReference(idMovie);
        movieDetails.setMovie(movieRef);
        entityManager.persist(movieDetails);
    }

    @Transactional
    public Movie merge(Movie movie){
        Movie result = entityManager.merge(movie);
        LOGGER.trace("entityManager.contains() : " + entityManager.contains(result));
        return result;
    }

    @Transactional
    public Optional<Movie> update(Movie movie) {
        if (movie.getId() == null) {
            return Optional.empty();
        }
        Movie toUpdate = find(movie.getId());
        if (toUpdate != null) {
            merge(movie);
        }
        return Optional.ofNullable(toUpdate);
    }

    @Transactional
    public Boolean remove(Long id) {
        Boolean result = false;
        if (id != null) {
            Movie movie = entityManager.find(Movie.class, id);
            if (movie != null) {
                entityManager.remove(movie);
                result = true;
            }
        }
        return result;
    }



}

