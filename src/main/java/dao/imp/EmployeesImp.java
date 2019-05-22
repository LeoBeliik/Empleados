package dao.imp;

import dao.Conection;
import dao.entities.Employees;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class EmployeesImp extends Conection {

    /**
     * Función para agregar empleados
     *
     * @param empNo     toma el numero del empleado
     * @param firstName toma el nombre del empleado
     * @param lastName  toma el apellido del empleado
     * @param sex       toma el sexo(M,F) del empleado
     * @param hireDate  toma el dia de contrato del empleado
     * @param birthDate toma la fecha de nacimiento del empleado
     */
    public void addEmp(int empNo, String firstName, String lastName, char sex, LocalDate hireDate, LocalDate birthDate) {
        start();
        Employees nEmp = new Employees(empNo, firstName, lastName, sex, hireDate, birthDate);
        em.merge(nEmp);
        end();
    }

    /**
     * Función para borrar empleados
     *
     * @param idEmp toma el numero del empleado
     */
    public void dltEmp(int idEmp) {
        start();
        em.remove(em.find(Employees.class, idEmp));
        end();
    }

    /**
     * funcion para buscar todos los empleados
     *
     * @return lista de empleados
     */
    public List<Employees> getAll() {
        start();
        TypedQuery<Employees> query = em.createQuery("select e from Employees as e", Employees.class);
        List<Employees> result = query.getResultList();
        end();
        return result;
    }
}
