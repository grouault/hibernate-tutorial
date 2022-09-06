package com.hibernate4all.tutorial.service;

import com.hibernate4all.tutorial.config.PersistenceConfig;
import com.hibernate4all.tutorial.domain.Actor;
import com.hibernate4all.tutorial.domain.Movie;
import com.hibernate4all.tutorial.dto.MovieDTO;
import com.hibernate4all.tutorial.repository.ActorRepository;
import com.hibernate4all.tutorial.repository.MovieRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= {PersistenceConfig.class})
@SqlConfig(dataSource = "dataSource", transactionManager = "transactionManager")
@Sql({"/datas/datas-test-postgre.sql"})
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Test
    public void addActor_existingMovie() {
        Movie movie = movieRepository.getReference(-1L);
        Actor actor = new Actor().setName("Eisenberg").setFirstName("Jesse");
        movieService.addMovieActor(movie, actor, "Mark Zuckerberg");
    }

    @Test
    public void associateActorAndFilm() {
        Movie movie = movieRepository.getReference(-1L);
        Actor actor = actorRepository.getReference(-2L);
        movieService.addMovieActor(movie, actor, "Mark Zuckerberg");
    }

    @Test
    public void addMovie_withActor() {
        Movie movie = new Movie().setName("La grande vadrouille").setDescription("film mythique");
        Actor actor = new Actor().setName("De Funès").setFirstName("Louis");
        // movieService.addMovieWithActor(movie, actor, "Stanislas Lefort");
        movieService.persist(movie, actor, "Stanilas Lefort");
    }

    @Test
    public void addMovie_existingActor() {
        Movie movie = new Movie().setName("La grande vadrouille").setDescription("film mythique");
        Actor actor = actorRepository.getReference(-3L);
       movieService.persist(movie, actor, "Augustin Bouvet");
        // movieService.addMovieWithActor(movie, actor, "Augustin Bouvet");
    }

    @Test
    public void removeActor() {
        Movie movie = movieRepository.getReference(-1L);
        Actor diCaprio = new Actor().setId(-1L);
        movieService.removeActor(movie, diCaprio);
    }

    @Test
    public void updateActor(){
        Movie movie = movieRepository.getReference(-1L);
        Actor actor = actorRepository.getReference(-1L);
        movieService.updateActor(movie, actor, "inconnu");
    }

    @Test
    public void updateDescription() {
        movieService.updateDescription(-2L, "super film mais j'ai oublié le pitch");
    }

    @Test
    public void updateDescription_fromAdmin1(){
        movieService.updateDescription(-1L, "desc from admin 1");
    }

    @Test
    public void updateDescription_fromAdmin2(){
        movieService.updateDescription(-1L, "desc from admin 2");
    }

    @Test
    public void updateMovie() {
        Movie movie =  movieRepository.find(-1L);
        movie.setDescription("titi");
        Optional<Movie> updatedMovie = movieService.updateMovie(movie);
        assertThat(updatedMovie.get().getDescription()).as("la description n'est pas correcte").isEqualTo("titi");
    }

}
