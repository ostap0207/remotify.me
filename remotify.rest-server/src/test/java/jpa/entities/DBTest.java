package jpa.entities;

import org.junit.After;
import org.junit.Before;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * .
 * User: Ostap
 * Date: 2/5/14
 * Time: 9:21 PM
 */
public class DBTest {

    protected EntityManager em;
    private EntityManagerFactory emf;

    @Before
    public void setUp(){
        emf = Persistence.createEntityManagerFactory("testMysqlUnit");
        em = emf.createEntityManager();
    }

    @After
    public void tearDown(){
        em.close();
        emf.close();
    }

}
