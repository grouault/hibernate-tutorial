package com.hibernate4all.tutorial.dto;

import com.hibernate4all.tutorial.domain.Certification;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

public class MovieDTO {

    private Long id;

    private String name;

    private String description;

    private Certification certification;

    private String director;

    private String image;

    List<ReviewDTO> reviews = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public MovieDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MovieDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MovieDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Certification getCertification() {
        return certification;
    }

    public MovieDTO setCertification(Certification certification) {
        this.certification = certification;
        return this;
    }

    public String getDirector() {
        return director;
    }

    public MovieDTO setDirector(String director) {
        this.director = director;
        return this;
    }

    public String getImage() {
        return image;
    }

    public MovieDTO setImage(String image) {
        this.image = image;
        return this;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public MovieDTO setReviews(List<ReviewDTO> reviewsDTO) {
        this.reviews = reviewsDTO;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MovieDTO.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .add("certification=" + certification)
                .add("director='" + director + "'")
                .add("image='" + image + "'")
                .toString();
    }

}
