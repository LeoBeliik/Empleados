package dao.imp;

import dao.Conection;
import dao.entities.DEmployee;
import dao.entities.Departments;
import dao.entities.DeptEmpPK;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class DepEmployeeImp extends Conection {

    /**
     * Función para buscar departamentos de empleados
     *
     * @param idDepto toma el numero de departamento
     * @param idEmp   toma el numero de empleado
     * @return departamentos del empleado
     */
    public DEmployee findEmpDep(String idDepto, int idEmp) {
        start();
        DeptEmpPK idDE = new DeptEmpPK(idDepto, idEmp);
        DEmployee findDep = em.find(DEmployee.class, idDE);
        end();
        return findDep;
    }

    /**
     * funcion apara agregar departamento de empleado
     *
     * @param idDepto  toma el numero de departamento
     * @param idEmp    toma el numero de empleado
     * @param fromDate toma la fecha inicial
     * @param toDate   toma la fecha final
     */
    public void addDepEmp(String idDepto, int idEmp, LocalDate fromDate, LocalDate toDate) {
        start();
        DeptEmpPK idDE = new DeptEmpPK(idDepto, idEmp);
        em.merge(new DEmployee(idDE, fromDate, toDate));
        end();
    }

    /**
     * funcion para borrar departamento de empleado
     *
     * @param idDepto toma numero de departamento
     * @param idEmp   toma numero de empleado
     */
    public void dltDepEmp(String idDepto, int idEmp) {
        start();
        DeptEmpPK idDE = new DeptEmpPK(idDepto, idEmp);
        em.remove(em.find(DEmployee.class, idDE));
        end();
    }

    /**
     * funcion para obtener todos los departamentos de un empleado
     *
     * @param idEmp toma numero de empleado
     * @return lista de departamentos
     */
    public List<DEmployee> getAll(int idEmp) {
        start();
        TypedQuery<DEmployee> query = em.createQuery("select d from DEmployee as d WHERE emp_no LIKE " + idEmp, DEmployee.class);
        List<DEmployee> result = query.getResultList();
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