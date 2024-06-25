package com.dar.server.data.dao;

import com.dar.server.data.entities.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

@Component
public class PerdonDao implements Dao<PersonEntity> {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final CriteriaQuery<PersonEntity> query;
    private final Root<PersonEntity> root;

    @Autowired
    public PerdonDao(EntityManager entityManager) {
        this.entityManager = entityManager;

        criteriaBuilder = entityManager.getCriteriaBuilder();
        query = criteriaBuilder.createQuery(PersonEntity.class);
        root = query.from(PersonEntity.class);

    }

    public Set<PersonEntity> getAllByUserId(long userId) {
        try {
            List<PersonEntity> result = entityManager.createQuery(query.where(criteriaBuilder.equal(root.get("user_id"), userId))).getResultList();
            return new HashSet<>(result);
        } catch (Exception e) {
            return Collections.emptySet();
        }
    }

    public Set<PersonEntity> getAll() {
        try {
            query.select(root);
            List<PersonEntity> result = entityManager.createQuery(query).getResultList();
            return new HashSet<>(result);
        } catch (Exception e) {
            return Collections.emptySet();
        }
    }

    @Override
    public void save(PersonEntity person) {
        entityManager.persist(person);
        entityManager.flush();
    }

    @Override
    public Optional<PersonEntity> get(long id) {
        entityManager.flush();
        Optional<PersonEntity> person;
        try {
            person = Optional.of(entityManager.find(PersonEntity.class, id));
        } catch (Exception e) {
            person = Optional.empty();
        }
        return person;
    }

    @Override
    public void commit() {
        entityManager.flush();
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public void rollback() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void beginTransaction() {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
    }

    @Override
    public void delete(PersonEntity person) {
        entityManager.remove(person);
        entityManager.flush();
    }

    @Override
    public void update(PersonEntity person) {
        entityManager.flush();
        entityManager.merge(person);
        entityManager.flush();
    }
}