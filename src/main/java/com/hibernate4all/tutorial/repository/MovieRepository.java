package com.hibernate4all.tutorial.repository;

import com.hibernate4all.tutorial.domain.Actor;
import com.hibernate4all.tutorial.domain.Certification;
import com.hibernate4all.tutorial.domain.Movie;
import com.hibernate4all.tutorial.domain.MovieActor;
import com.hibernate4all.tutorial.domain.MovieDetails;
import com.hibernate4all.tutorial.domain.Movie_;
import com.hibernate4all.tutorial.domain.Review;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieRepository.class);

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private ActorRepository actorRepository;

    @Transactional
    public void addMovieDetails(MovieDetails movieDetails, Long idMovie) {
        Movie movieRef = getReference(idMovie);
        movieDetails.setMovie(movieRef);
        entityManager.persist(movieDetails);
    }

    public Movie find(Long id){
        Movie result = entityManager.find(Movie.class,id);
        LOGGER.trace("entityManager.contains() : " + entityManager.contains(result));
        return result;
    }

    public List<Movie> findByName(String searchString) {
        List<Movie> movies = entityManager.createQuery("select m from Movie m where m.name = : param", Movie.class)
                .setParameter("param", searchString).getResultList();
        return  movies;
    }

    public List<Movie> findWithCertification(String operation, Certification certif) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> query = builder.createQuery(Movie.class);
        // creation d'une racine qui represente ce qu'il y a dans le Select
        Root<Movie> root = query.from(Movie.class); // equivalent select m from Movie
        Predicate predicat;
        if ("<".equals(operation)) {
            predicat= builder.lessThan(root.get(Movie_.CERTIFICATION), certif);
        } else if("<=".equals(operation)) {
           predicat = builder.lessThanOrEqualTo(root.get(Movie_.CERTIFICATION), certif);
        } else if("=".equals(operation)) {
            predicat = builder.equal(root.get(Movie_.CERTIFICATION), certif);
        } else if(">".equals(operation)) {
            predicat = builder.greaterThan(root.get(Movie_.CERTIFICATION), certif);
        } else if(">=".equals(operation)) {
            predicat = builder.greaterThanOrEqualTo(root.get(Movie_.CERTIFICATION), certif);
        } else {
            throw new IllegalArgumentException("valeur de paramètre de recherche incorreect");
        }
        query.where(predicat);

        return  entityManager.createQuery(query).getResultList();
    }

    public List<Movie> getAll(){
        return entityManager.createQuery("from Movie", Movie.class).getResultList();
    }

    public Movie getReference(Long id) {
        return entityManager.getReference(Movie.class, id);
    }

    @Transactional
    public Movie merge(Movie movie){
        Movie result = entityManager.merge(movie);
        LOGGER.trace("entityManager.contains() : " + entityManager.contains(result));
        return result;
    }

    @Transactional
    public Movie persist(Movie movie){
        LOGGER.trace("entityManager.contains() : " + entityManager.contains(movie));
        entityManager.persist(movie);
        return movie;
    }

    @Transactional
    public Movie persistCascadeWithActor(Movie movie, Actor actor, String character){
        LOGGER.trace("entityManager.contains() : " + entityManager.contains(movie));;
        Actor actorDb = actor.getId() != null ? actorRepository.find(actor.getId()) : actorRepository.persist(actor);
        movie.addActor(actorDb, character);
        entityManager.persist(movie);
        return movie;
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

}

