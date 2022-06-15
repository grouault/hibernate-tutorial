package com.hibernate4all.tutorial.repository;

import com.hibernate4all.tutorial.domain.Movie;
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

    @Transactional
    public Movie persist(Movie movie){
        LOGGER.trace("entityManager.contains() : " + entityManager.contains(movie));
        entityManager.persist(movie);
        return movie;
    }

    @Transactional
    public Movie merge(Movie movie){
        Movie result = entityManager.merge(movie);
        entityManager.contains(result);
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
    public Movie find(Long id){
        Movie result = entityManager.find(Movie.class,id);
        LOGGER.trace("entityManager.contains() : " + entityManager.contains(result));
        return result;
    }

    @Transactional
    public List<Movie> getAll(){
        return entityManager.createQuery("from Movie", Movie.class).getResultList();
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

    @Transactional
    public Movie getReference(Long id) {
        Movie proxy = entityManager.getReference(Movie.class, id);
        return proxy;
    }

}

