package com.hibernate4all.tutorial.model;

import com.hibernate4all.tutorial.domain.Genre;
import com.hibernate4all.tutorial.repository.GenreRepository;
import java.util.HashSet;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

import com.hibernate4all.tutorial.domain.Movie;
import com.hibernate4all.tutorial.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// doit s'executer dans le contexte Spring
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql({"/datas/datas-test-postgre.sql"})
public class EqualsHashCodeTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void equalsTest() {

        Movie movie = new Movie().setName("Dune");
        movieRepository.persist(movie);

        // A ce statd, MOVIE est detached
        Movie bdMovie = movieRepository.find(movie.getId());
        assertThat(movie).as("les deux entités devraient être les mêmes").isEqualTo(bdMovie);

        // Test de la reference
        // necessite d'avoir le test sur les ids.
        Movie refMovie = movieRepository.getReference(movie.getId());
        assertThat(bdMovie).as("refMovie devrait etre egale à bdMovie").isEqualTo(refMovie);

    }

    @Test
    public void equalsGenreTest() {
        Genre genre = new Genre().setName("Sciences-fiction");
        genreRepository.persist(genre);
        // le succès du test est garantit en faisant ici
        // un test sur l'identifiant et non le nom.
        Genre genreRef = genreRepository.getReference(genre.getId());
        assertThat(genre).as("test d'égalité avec la reférence").isEqualTo(genreRef);
    }

    @Test
    public void setContainsGenreTest() {
        // Test : on met dans un set objet transient.
        // on le persiste et regarde s'il est encore dans la liste.
        // Test OK : car le test se fait sur l'identifiant fonctionnel.
        Genre genre = new Genre().setName("sciences-fiction");
        Set<Genre> monSet = new HashSet<>();
        monSet.add(genre);
        assertThat(monSet.contains(genre)).as("1- le set devrait contenir le genre").isTrue();
        genreRepository.persist(genre);
        assertThat(monSet.contains(genre)).as("2- le set devrait contenir le genre").isTrue();
    }

    @Test
    public void setContainsGenreProxyTest() {
        // Test: on charge le proxy dans un set.
        // Si le hash est détermnié sur le name : pb ?
        // TODO: impossible de charger des proxy dans un Set???
        // NOTE : j'ai l'impression que c'est une opération impossible.
        Genre proxy = genreRepository.getReference(-1L);
        Set<Genre> monSet = new HashSet<>();
        monSet.add(proxy);
        assertThat(monSet.contains(proxy)).as("le set devrait contenir le proxy").isTrue();
    }

    @Test
    public void setContainsMovieTest() {
        Movie movie = new Movie().setName("Dune");
        Set<Movie> monSet = new HashSet<>();
        monSet.add(movie);
        assertThat(monSet.contains(movie)).as("1- le set devrait contenir le movie").isTrue();
        movieRepository.persist(movie);
        assertThat(monSet.contains(movie)).as("2- le set devrait contenir le movie").isTrue();
    }

}
