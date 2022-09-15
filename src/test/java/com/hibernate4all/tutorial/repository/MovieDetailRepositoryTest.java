package com.hibernate4all.tutorial.repository;

import com.hibernate4all.tutorial.domain.MovieDetails;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import static org.assertj.core.api.Assertions.assertThat;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql({"/datas/datas-test-postgre.sql"})
public class MovieDetailRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieDetailRepositoryTest.class);

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private MovieDetailsRepository movieDetailsRepository;

    @Test
    public void addMovieDetails_casNominal(){
        MovieDetails details = new MovieDetails().setPlot("Intrique du film Memento très longue!");
        movieDetailsRepository.addMovieDetails(details, -2L);
        assertThat(details.getId()).as("l'entité aurait dû être persistée").isNotNull();
    }
    @Test
    @Sql({"/datas/datas-test-n+1.sql"})
    public void getMovieDetail(){
        MovieDetails movieDetails = movieDetailsRepository.getMovieDetails(-1L);
        assertThat(movieDetails).as("impossible de recuperer le MovieDetail").isNotNull();
    }

    @Test
    @Sql({"/datas/datas-test-n+1.sql"})
    public void getMovieDetail_byPlot(){
        MovieDetails movieDetails = movieDetailsRepository.findByPlotContaining("Dominick");
        assertThat(movieDetails).as("impossible de récupérer le MovieDetail").isNotNull();
    }

    @Test
    @Sql({"/datas/datas-test-n+1.sql"})
    public void getAllMovieDetail_ByFindAllWithEntityGraph(){
        Statistics stats = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
        stats.setStatisticsEnabled(true);
        List<MovieDetails> movieDetails = movieDetailsRepository.findAll();
        assertThat(movieDetails).as("le nombre de MovieDetails devrait être de 3").hasSize(3);
        assertThat(stats.getPrepareStatementCount()).as("n+1 select sur la requete").isEqualTo(1);
    }

    @Test
    @Sql({"/datas/datas-test-n+1.sql"})
    public void getAllMovieDetails_bySpecifiMethod(){
        List<MovieDetails> movieDetails =  movieDetailsRepository.getAllMovieDetail();
        assertThat(movieDetails).as("le nombre de MovieDetails devrait être de 3").hasSize(3);
    }

}
