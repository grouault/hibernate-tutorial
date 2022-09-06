package com.hibernate4all.tutorial.mapper;

import com.hibernate4all.tutorial.domain.Review;
import com.hibernate4all.tutorial.dto.ReviewDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDTO toDto(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setContent(review.getContent());
        reviewDTO.setAuthor(review.getAuthor());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setIdMovie(review.getMovie().getId());
        return reviewDTO;
    }

    public List<ReviewDTO> toDtos(Set<Review> reviews) {
        List<ReviewDTO> reviewsDTO = new ArrayList<>();
        reviews.stream().forEach(r -> reviewsDTO.add(toDto(r)));
        return reviewsDTO;
    }

}
