package com.hibernate4all.tutorial.mapper;

import com.hibernate4all.tutorial.domain.Movie;
import com.hibernate4all.tutorial.domain.Review;
import com.hibernate4all.tutorial.dto.MovieDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    @Autowired
    private ReviewMapper reviewMapper;

    public MovieDTO toDto(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId());
        movieDTO.setDescription(movie.getDescription());
        movieDTO.setName(movie.getName());
        movieDTO.setCertification(movie.getCertification());
        movieDTO.setImage(movie.getImage());
        movieDTO.setDirector(movie.getDirector());
        movieDTO.setReviews(reviewMapper.toDtos(movie.getReviews()));
        return movieDTO;
    }

    public List<MovieDTO> toDtos(List<Movie> movies){
        List<MovieDTO> movieDtos = new ArrayList<MovieDTO>();
        movies.stream().forEach(movie -> movieDtos.add(toDto(movie)));
        return movieDtos;
    }

}
