package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Conection {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Empleados");
    protected EntityManager em;

    protected void start(){
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
    protected void end(){
        em.close();
        em.getTransaction().commit();
        System.gc();
    }
}
