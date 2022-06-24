package com.hibernate4all.tutorial.service;

import com.hibernate4all.tutorial.domain.Genre;
import com.hibernate4all.tutorial.domain.Movie;
import com.hibernate4all.tutorial.domain.Review;
import com.hibernate4all.tutorial.repository.MovieRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie getMovie(Long id) {
        Movie movie = movieRepository.find(id);
        // Movie proxy = movieRepository.getReference(id);
        // Movie  movie = (Movie) Hibernate.unproxy(proxy);
        return movie;
    }

    public Movie addMovie(Movie movie){
        return movieRepository.persist(movie);
    }

    @Transactional
    public void addReview(Long idMovie, Review review) {
        Movie movieBd = movieRepository.getReference(idMovie);
        movieBd.addReview(review);
    }

    @Transactional
    public void removeGenre(Long idMovie, Genre genre) {
        Movie movie = movieRepository.getReference(idMovie);
        movie.removeGenre(genre);
    }

    public Optional<Movie> updateMovie(Movie movie) {
        return movieRepository.update(movie);
    }

    public Boolean removeMovie(Long id) {
        return movieRepository.remove(id);
    }

    @Transactional
    public void updateDescription(Long id, String description) {
        Movie movie = movieRepository.find(id);
        movie.setDescription(description);
    }

    @Transactional
    public List<Movie> removeAndGetAll(Long id){
        movieRepository.remove(id);
        return movieRepository.getAll();
    }

    public List<Movie> getMovies(){
        List<Movie> movies = movieRepository.getAll();
        return movies;
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



}
