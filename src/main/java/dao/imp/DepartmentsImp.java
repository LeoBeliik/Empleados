package dao.imp;

import dao.Conection;
import dao.entities.Departments;
import javax.persistence.TypedQuery;
import java.util.List;

public class DepartmentsImp extends Conection {

    /**
     * Función para buscar departamento
     *
     * @param noDep toma el numero del departamento
     * @return Objeto departamento
     */
    public Departments findDep(String noDep) {
        start();
        Departments findDep = em.find(Departments.class, noDep);
        end();
        return findDep;
    }

    /**
     * Función para agregar departamento
     *
     * @param noDep   numero del departamento
     * @param nameDep nombre del departamento
     */
    public void addDep(String noDep, String nameDep) {
        start();
        em.merge(new Departments(noDep, nameDep));
        end();
    }

    /**
     * Función para borrar departamento
     *
     * @param noDep numero del departamento
     */
    public void dltDep(String noDep) {
        start();
        em.remove(em.find(Departments.class, noDep));
        end();
    }

    /**
     * funcion para obtener todos los departamentos
     *
     * @return lista de departamentos
     */
    public List<Departments> getAll() {
        start();
        TypedQuery<Departments> query = em.createQuery("select d from Departments as d", Departments.class);
        List<Departments> result = query.getResultList();
        end();
        return result;
    }
}