package dao.imp;

import dao.Conection;
import dao.entities.*;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class DepManagerImp extends Conection {

    /**
     * Función para buscar departamentos de manager
     *
     * @param idDepto toma el numero de departamento
     * @param idEmp   toma el numero de empleado
     * @return departamentos del empleado
     */
    public DManager findManaDep(String idDepto, int idEmp) {
        start();
        DeptManaPK idMan = new DeptManaPK(idDepto, idEmp);
        DManager findDep = em.find(DManager.class, idMan);
        end();
        return findDep;
    }

    /**
     * funcion apara agregar departamento de manager
     *
     * @param idDepto  toma el numero de departamento
     * @param idEmp    toma el numero de empleado
     * @param fromDate toma la fecha inicial
     * @param toDate   toma la fecha final
     */
    public void addDepMana(String idDepto, int idEmp, LocalDate fromDate, LocalDate toDate) {
        start();
        DeptManaPK idMan = new DeptManaPK(idDepto, idEmp);
        em.merge(new DManager(idMan, fromDate, toDate));
        end();
    }

    /**
     * funcion para borrar departamento de manager
     *
     * @param idDepto toma numero de departamento
     * @param idEmp   toma numero de empleado
     */
    public void dltDepMana(String idDepto, int idEmp) {
        start();
        DeptManaPK idMan = new DeptManaPK(idDepto, idEmp);
        em.remove(em.find(DManager.class, idMan));
        end();
    }

    /**
     * funcion para obtener todos los departamentos de un manager
     *
     * @param idEmp toma numero de empleado
     * @return lista de departamentos
     */
    public List<DManager> getAll(int idEmp) {
        start();
        TypedQuery<DManager> query = em.createQuery("select m from DManager as m WHERE emp_no LIKE " + idEmp, DManager.class);
        List<DManager> result = query.getResultList();
        end();
        return result;
    }

    /**
     * funcion para obtener nombre de departamento
     *
     * @param deptoNo toma numero de departamento
     * @return nombre del departamento
     */
    public String getName(String deptoNo) {
        start();
        TypedQuery<Departments> query = em.createQuery("select d from Departments as d WHERE dept_no like '" + deptoNo + "'", Departments.class);
        List<Departments> result = query.getResultList();
        String deptName = result.toString().replace("]", "").replace("[", "");
        end();
        return deptName;
    }
}