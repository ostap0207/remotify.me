package jpa.entities;

import db.ComputerDaoImpl;
import entities.Computer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * .
 * User: Ostap
 * Date: 2/5/14
 * Time: 9:21 PM
 */
public class TestComputer extends DBTest {

    protected ComputerDaoImpl computerDao;

    @Before
    public void setUp(){
        super.setUp();

        Computer computer = new Computer();
        computer.setIp("testIp");
        computer.setConnectionKey("testKey");
        computer.setStatus("online");
        computer.setName("name");

        computerDao = new ComputerDaoImpl();
        computerDao.setEntityManager(em);

        em.getTransaction().begin();
        computer = computerDao.create(computer);
        em.getTransaction().commit();

    }

    @Test
    public void testInsert(){
        Computer computer = new Computer();
        computer.setIp("test2");
        computer.setConnectionKey("testKey2");
        computer.setStatus("online");
        computer.setName("name1");

        assertEquals(1,computerDao.countAll(null));

        em.getTransaction().begin();
        computer = computerDao.create(computer);
        em.getTransaction().commit();

        assertEquals(computer,computerDao.find(computer.getId()));
        assertEquals(2,computerDao.countAll(null));
    }


    @Test
    public void testDelete(){
        assertEquals(1,computerDao.countAll(null));

        em.getTransaction().begin();
        computerDao.delete(1);
        em.getTransaction().commit();

        assertEquals(0,computerDao.countAll(null));
    }



    @After
    public void tearDown(){
        super.tearDown();
    }

}
