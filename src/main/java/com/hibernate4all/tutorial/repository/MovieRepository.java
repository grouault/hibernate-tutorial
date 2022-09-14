package com.hibernate4all.tutorial.repository;

import com.hibernate4all.tutorial.domain.Actor;
import com.hibernate4all.tutorial.domain.Certification;
import com.hibernate4all.tutorial.domain.Movie;
import com.hibernate4all.tutorial.domain.MovieDetails;
import com.hibernate4all.tutorial.domain.Movie_;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.LockOptions;
import org.hibernate.jpa.QueryHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MovieRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieRepository.class);

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

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

    @Transactional
    public void displayGenres(Long id) {
        Movie m = this.find(id);
        m.getGenres().stream().forEach(genre -> LOGGER.info(genre.getName()));
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Movie find(Long id){
        Movie result = entityManager.find(Movie.class,id);
        LOGGER.trace("entityManager.contains() : " + entityManager.contains(result));
        return result;
    }

    public List<Movie> findByName(String searchString) {
        List<Movie> movies = entityManager.createQuery("select m from Movie m where lower(m.name) like : param", Movie.class)
                .setParameter("param", '%' +searchString.toLowerCase() +'%').getResultList();
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

    public List<Movie> getMoviesWithReview(){
        return entityManager
                .createQuery("select distinct m from Movie m left join fetch m.reviews", Movie.class)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
    }

    public List<Movie> getMoviesWithAwardsAndReviews(){

        EntityManager entityManagerLocal = entityManagerFactory.createEntityManager();

        List<Movie> movies = entityManagerLocal
                .createQuery("select distinct m from Movie m left join fetch m.reviews", Movie.class)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();

        LOGGER.trace("session contains movie2 : " + entityManagerLocal.contains(movies.get(0)));

        movies = entityManagerLocal
                .createQuery("select distinct m from Movie m left join fetch m.awards where m in :movies", Movie.class)
                .setParameter("movies", movies)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();

        entityManagerLocal.close();

        return movies;

    }

    public List<Movie> getAll(){
        return entityManager.createQuery("from Movie", Movie.class).getResultList();
    }


    public List<Movie> getMoviesPager(int start, int maxResults){

        List<Movie> movies = entityManager.createQuery(" select m from Movie m order by m.name", Movie.class)
                                .setFirstResult(start)
                                .setMaxResults(maxResults)
                                .getResultList();
        return movies;

    }

    public List<Movie> getMoviesWithReviewsPager(int start, int maxResults){

        EntityManager entityManagerLocal = entityManagerFactory.createEntityManager();

        List<Movie> movies = entityManagerLocal.createQuery(" select m from Movie m order by m.name", Movie.class)
                                               .setFirstResult(start)
                                               .setMaxResults(maxResults)
                                               .getResultList();

        movies = entityManagerLocal.createQuery(" select  distinct m from Movie m left join fetch m.reviews where m in :movies", Movie.class)
                                   .setParameter("movies", movies)
                                   .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                                   .getResultList();

        entityManagerLocal.close();

        return movies;


    }

    public List<MovieDetails> getAllMovieDetail() {
        // on charge les MovieDetails pour mise dans le cache
        List<MovieDetails> moviesDetails = entityManager.createQuery("select md from MovieDetails md", MovieDetails.class).getResultList();
        // chargement des movies.
        moviesDetails = entityManager
                .createQuery("select md from MovieDetails md join fetch md.movie where md in :movieDetails ", MovieDetails.class)
                .setParameter("movieDetails", moviesDetails).getResultList();
        return moviesDetails;
    }

    public MovieDetails getMovieDetails(Long id) {

        MovieDetails movieDetails = entityManager.createQuery("select distinct md from MovieDetails md " +
                                             " join fetch md.movie m" +
                                                                 " left join fetch m.reviews " +
                                                                 " left join fetch m.genres " +


                                             " where md.id = :id",
                                             MovieDetails.class)
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

    public Movie getReference(Long id) {
        Movie movie = entityManager.getReference(Movie.class, id);
        LOGGER.info("getReferecne");
        return movie;
    }

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

    public List<Movie> search(String searchText) {
        List<Movie> movies = entityManager.createQuery(
                "select m from Movie m " +
                        " where upper(m.name) like '%' || :searchText || '%' " +
                        "or upper(m.description) like '%' || :searchText || '%'", Movie.class
        ).setParameter("searchText", searchText.toUpperCase()).getResultList();
        return movies;
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

