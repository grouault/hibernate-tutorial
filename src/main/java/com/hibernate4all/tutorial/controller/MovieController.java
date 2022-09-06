package com.hibernate4all.tutorial.controller;

import com.hibernate4all.tutorial.domain.Movie;
import com.hibernate4all.tutorial.domain.MovieDetails;
import com.hibernate4all.tutorial.dto.MovieDTO;
import com.hibernate4all.tutorial.service.MovieService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    MovieService movieService;

    @GetMapping("/all")
    public List<MovieDTO> getAll() {
        List<MovieDTO> movies = movieService.getMovies();
        return movies;
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<MovieDetails> getMovieDetails(@PathVariable Long id) {
        ResponseEntity<MovieDetails> result = null;
        try {
            MovieDetails movieDetails = movieService.getMovieDetails(id);
            result = ResponseEntity.ok(movieDetails);
        } catch (EmptyResultDataAccessException ex) {
            LOGGER.warn(ex.getMessage());
            LOGGER.warn("Movie Not Found : id = {}", id);
            result = ResponseEntity.notFound().build();
        }
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> get(@PathVariable Long id) {
        MovieDTO movie = movieService.getMovie(id);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(movie);
        }
    }

    @GetMapping("/search")
    public List<MovieDTO> search(@RequestParam("text") String text) {
        LOGGER.debug("text = " + text);
        List<MovieDTO> movieDTOS = movieService.search(text);
        return movieDTOS;
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.persist(movie);
    }

    @PutMapping("/")
    public ResponseEntity<Movie> update(@RequestBody Movie movie) {
        Optional<Movie> result = movieService.updateMovie(movie);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return movieService.removeMovie(id) ? ResponseEntity.ok(id) : ResponseEntity.notFound().build();
    }

}
