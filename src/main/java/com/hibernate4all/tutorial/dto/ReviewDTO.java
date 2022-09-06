package com.hibernate4all.tutorial.dto;

public class ReviewDTO {

    private Long id;

    private String author;

    private String content;

    private Integer rating;

    private Long idMovie;

    public Long getId() {
        return id;
    }

    public ReviewDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ReviewDTO setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ReviewDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public ReviewDTO setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public Long getIdMovie() {
        return idMovie;
    }

    public ReviewDTO setIdMovie(Long idMovie) {
        this.idMovie = idMovie;
        return this;
    }

}
