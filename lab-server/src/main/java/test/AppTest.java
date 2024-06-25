package test;


import com.dar.server.data.entities.Color;
import com.dar.server.data.entities.CoordinatesEntity;
import com.dar.server.data.entities.LocationEntity;
import com.dar.server.data.entities.PersonEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AppTest {
    private EntityManager em;

    @Before
    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("local");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    @Test
    public void shouldPersistSpaceMarine() {
        PersonEntity spm = new PersonEntity("sp", new CoordinatesEntity(1L, 0L), 3L,  5f, Color.RED, new LocationEntity(1, 1L, 1));
        em.persist(spm);
        assertTrue(em.contains(spm));
        em.detach(spm);
        assertFalse(em.contains(spm));
    }


    @After
    public void close() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
        em.getEntityManagerFactory().close();
        em.close();
    }
}
