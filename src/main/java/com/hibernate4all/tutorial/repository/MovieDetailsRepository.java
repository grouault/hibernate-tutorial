package com.hibernate4all.tutorial.repository;

import com.hibernate4all.tutorial.domain.MovieDetails;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieDetailsRepository extends JpaRepository<MovieDetails, Long>, MovieDetailsRepositoryExtended {

    @Override
    @EntityGraph(attributePaths = {"movie"})
    List<MovieDetails> findAll();

    @Query
    MovieDetails findByPlotContaining(String text);

}
