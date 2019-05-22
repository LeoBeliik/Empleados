package dao.imp;

import dao.Conection;
import dao.entities.SalPK;
import dao.entities.Salaries;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class SalariesImp extends Conection {

    /**
     * Función para encontrar salario de empleado
     *
     * @param idEmp    toma id empleaod
     * @param fromDate toma fecha inicial del salario
     * @param toDate   toma fecha final del salario
     * @return objeto salario
     */
    public Salaries findSal(int idEmp, LocalDate fromDate, LocalDate toDate) {
        start();
        SalPK idSa = new SalPK(idEmp, fromDate, toDate);
        Salaries sal = em.find(Salaries.class, idSa);
        end();
        return sal;
    }

    /**
     * Función para agregar salario de empleado
     *
     * @param idEmp    toma numero empleado
     * @param fromDate fecha inicial del salario
     * @param toDate   fecha final del salario
     * @param salary   cantidad el salario
     */
    public void addSal(int idEmp, LocalDate fromDate, LocalDate toDate, int salary) {
        start();
        SalPK idSa = new SalPK(idEmp, fromDate, toDate);
        em.merge(new Salaries(idSa, salary));
        end();
    }

    /**
     * Función para borrar salario de empleado
     *
     * @param idEmp    toma numero de empleado
     * @param fromDate fecha inicial del salario
     * @param toDate   fecha final del salario
     */
    public void dltSal(int idEmp, LocalDate fromDate, LocalDate toDate) {
        start();
        SalPK idSa = new SalPK(idEmp, fromDate, toDate);
        em.remove(em.find(Salaries.class, idSa));
        end();
    }

    /**
     * funcion para obtener todos los salarios de un empleado
     *
     * @param idEmp toma el numero de empleado
     * @return lista con todos los salarios del empleado
     */
    public List<Salaries> getAll(int idEmp) {
        start();
        TypedQuery<Salaries> query = em.createQuery("SELECT s FROM Salaries s WHERE emp_no LIKE " + idEmp, Salaries.class);
        List<Salaries> result = query.getResultList();
        end();
        return result;
    }
}