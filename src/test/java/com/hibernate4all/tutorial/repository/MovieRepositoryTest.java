package com.hibernate4all.tutorial.repository;

import com.hibernate4all.tutorial.config.PersistenceConfigTest;
import com.hibernate4all.tutorial.domain.Certification;
import com.hibernate4all.tutorial.domain.Movie;
import com.hibernate4all.tutorial.service.MovieService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.assertj.core.api.Assertions.assertThat;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// doit s'executer dans le contexte Spring
@ExtendWith(SpringExtension.class)
// donner les classes de config dont spring a besoin pour s'initialiser
@ContextConfiguration(classes= {PersistenceConfigTest.class})
// charger les données de test
@SqlConfig(dataSource = "dataSourceH2", transactionManager = "transactionManager")
@Sql({"/datas/datas-test.sql"})
public class MovieRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieRepository.class);

    @Autowired
    private MovieRepository repository;

    @Autowired
    private MovieService service;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void save_casNominal(){
        Movie movie = new Movie();
        movie.setName("Inception V2");
        movie.setCertification(Certification.INTERDIT_MOINS_12);
        repository.persist(movie);
        System.out.println("[save_CasNominal] - session contains movie : " + entityManager.contains(movie));
        System.out.println("fin de test");
    }

    @Test
    public void merge_casimule() {
        Movie movie = new Movie();
        movie.setId(-1L);
        movie.setName("Inception 2");
        movie.setDescription("alter description");
        Movie mergedMovie = repository.merge(movie);
        assertThat(movie.getName()).as("Le nom du film n'a pas changé").isEqualTo("Inception 2");
    }

    @Test
    public void merge_casimule_without_update() {
        Movie movie = new Movie();
        movie.setId(-1L);
        movie.setName("Inception");
        movie.setDescription("description init");
        Movie mergedMovie = repository.merge(movie);
        assertThat(movie.getName()).as("Le nom du film n'a pas changé").isEqualTo("Inception");
    }

    @Test
    public void find_casNominal(){
        Movie memento = repository.find(-2L);
        assertThat(memento.getName()).as("mauvais film récupéré").isEqualTo("Memento");
        assertThat(memento.getCertification()).as("mauvaise certification").isEqualTo(Certification.INTERDIT_MOINS_12);
    }

    @Test
    public void getAll_casNominal(){
        List<Movie> movies = repository.getAll();
        assertThat(movies).as("l'ensemble des films n'a pas été récupéré").hasSize(2);
    }

    @Test
    public void remove(){
        repository.remove(-1L);
        List<Movie> movies = repository.getAll();
        assertThat(movies).as("le film n'a pas été supprimé").hasSize(1);
    }

    @Test
    public void removeAndGetAll() {
        List<Movie> movies = service.removeAndGetAll(-1L);
        assertThat(movies).as("le film n'a pas été supprimé").hasSize(1);

    }

    @Test
    public void getReference_casNominal(){
        Movie movie = repository.getReference(-2L);
        assertThat(movie.getId()).as("la référence n'a pas été correctement chargée").isEqualTo(-2L);
    }

    @Test
    public void getReference_fail(){
        Assertions.assertThrows(LazyInitializationException.class, () -> {
            Movie movie = repository.getReference(-2L);
            LOGGER.trace("movie name  : " + movie.getName());
            assertThat(movie.getId()).as("la référence n'a pas été correctement chargée").isEqualTo(-2L);
        });
    }

    @Test
    public void testFlushOrder() {
        Movie movie = service.removeThenAddMovie();
        assertThat(movie.getDescription()).as("le movie n'est pas le bon").contains("v2");
    }

}
