package com.hibernate4all.tutorial.repository;

import com.hibernate4all.tutorial.domain.Genre;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.hibernate.jpa.QueryHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GenreRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenreRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    public Genre findById(Long id) {
        Genre genre = entityManager.find(Genre.class, id);
        LOGGER.debug("Find - genre : " + genre.toString());
        return genre;
    }

    public List<Genre> getAll(){
        return entityManager.createQuery("from Genre", Genre.class)
                            .setHint(QueryHints.HINT_CACHEABLE, "true")
                            .getResultList();
    }

    public Genre getReference(Long id) {
        Genre genre = entityManager.getReference(Genre.class, id);
        LOGGER.debug("Find - getReference : " + genre.getId());
        return genre;
    }

    @Transactional
    public Genre persist(Genre genre) {
        entityManager.persist(genre);
        LOGGER.debug("Persist - genre  = " + genre.toString());
        return genre;
    }

}
