package com.hibernate4all.tutorial.repository;

import com.hibernate4all.tutorial.domain.Movie;
import com.hibernate4all.tutorial.domain.MovieDetails;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.jpa.QueryHints;
import org.springframework.transaction.annotation.Transactional;

public class MovieDetailsRepositoryExtendedImpl implements MovieDetailsRepositoryExtended {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addMovieDetails(MovieDetails movieDetails, Long idMovie) {
        Movie movieRef = entityManager.getReference(Movie.class, idMovie);
        movieDetails.setMovie(movieRef);
        entityManager.persist(movieDetails);
    }

    @Override
    @Transactional
    public MovieDetails getMovieDetails(Long id) {
        MovieDetails movieDetails = entityManager.createQuery(
                "select distinct md from MovieDetails md " +
                     " join fetch md.movie m" +
                     " left join fetch m.reviews " +
                     " left join fetch m.genres " +
                     " where md.id = :id", MovieDetails.class)
                 .setParameter("id", id)
                 .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                 .getSingleResult();

        entityManager.createQuery("select distinct m from Movie m left join fetch m.awards where m = :movie", Movie.class)
                     .setParameter("movie", movieDetails.getMovie())
                     .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                     .getSingleResult();

        entityManager.createQuery("select distinct m from Movie m left join fetch m.moviesActors where m = :movie", Movie.class)
                     .setParameter("movie", movieDetails.getMovie())
                     .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                     .getSingleResult();

        return movieDetails;
    }

    public List<MovieDetails> getAllMovieDetail() {
        // on charge les MovieDetails pour mise dans le cache
        List<MovieDetails> moviesDetails = entityManager.createQuery(
                "select md from MovieDetails md " +
                        "join fetch md.movie", MovieDetails.class).getResultList();

        // chargement des movies puis des movies en utilisant le cache
        /*
        List<MovieDetails> moviesDetails = entityManager.createQuery("select md from MovieDetails md", MovieDetails.class).getResultList();
        moviesDetails = entityManager
                .createQuery("select md from MovieDetails md join fetch md.movie where md in :movieDetails ", MovieDetails.class)
                .setParameter("movieDetails", moviesDetails).getResultList();
        */
        return moviesDetails;

    }

}
