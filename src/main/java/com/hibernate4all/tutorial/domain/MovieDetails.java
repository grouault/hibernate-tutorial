package com.hibernate4all.tutorial.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Movie_Details")
public class MovieDetails {

    @Id
    private Long id;

    @Column(length=4000)
    private String plot;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Movie movie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlot() {
        return plot;
    }

    public MovieDetails setPlot(String plot) {
        this.plot = plot;
        return this;
    }

    public Movie getMovie() {
        return movie;
    }

    public MovieDetails setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieDetails)) return false;

        MovieDetails other = (MovieDetails) o;
        if (this.getId() == null && other.getId() == null) {
            return Objects.equals(this.getPlot(), other.getPlot()) ;
        }

        return this.getId() != null && Objects.equals(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(58);
    }

}
