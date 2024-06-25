package com.dar.server.data.repositories;

import com.dar.server.data.dao.PerdonDao;
import com.dar.server.data.entities.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Repository
public class PersonRepository extends DBRepository<PersonEntity> {
    private final PerdonDao perdonDao;

    @Autowired
    PersonRepository(PerdonDao perdonDao) {
        super(perdonDao);
        this.perdonDao = perdonDao;

    }

    @Transactional
    public Set<PersonEntity> getAll() {
        try {
            return perdonDao.getAll();
        } catch (Exception e) {
            e.printStackTrace(); // Вызов метода без ошибок
        }
        return Collections.emptySet();
    }

    @Transactional
    public Set<PersonEntity> getAllByUserId(long userId) {

        try {
            return perdonDao.getAllByUserId(userId)/*.stream().peek(w->spmDao.update(w)).toList()*/;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptySet();

    }

    @Transactional
    public void removeAll(Collection<PersonEntity> collection) {
        try {
            collection.forEach(perdonDao::delete);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void addAll(Collection<PersonEntity> collection) {
        try {
            collection.forEach(perdonDao::save);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
