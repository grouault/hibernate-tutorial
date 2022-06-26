package com.hibernate4all.tutorial.repository;

import com.hibernate4all.tutorial.domain.Actor;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ActorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Actor find(Long id) {
        return entityManager.find(Actor.class, id);
    }

    public Actor getReference(Long id) {
        return entityManager.getReference(Actor.class, id);
    }

    @Transactional
    public Actor persist(Actor actor) {
        entityManager.persist(actor);
        return actor;
    }

}
