package dao.imp;

import dao.Conection;
import dao.entities.TitlePK;
import dao.entities.Titles;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class TitlesImp extends Conection {

    /**
     * Función para buscar títulos
     *
     * @param idEmp toma el numero del empleado
     * @param title toma el título del empleado
     * @return objeto title
     */
    public Titles findTit(int idEmp, String title) {
        start();
        TitlePK tpk = new TitlePK(idEmp, title);
        Titles findTitle = em.find(Titles.class, tpk);
        end();
        return findTitle;
    }

    /**
     * Función para agregar titulo a empleados
     *
     * @param idEmp    toma el numero de empleado
     * @param title    el titulo a agregar
     * @param fromDate fecha desde la obtencion del titulo
     * @param toDate   fecha hasta la caducacion del titulo
     */
    public void addTit(int idEmp, String title, LocalDate fromDate, LocalDate toDate) {
        start();
        TitlePK tpk = new TitlePK(idEmp, title);
        em.merge(new Titles(tpk, fromDate, toDate));
        end();
    }

    /**
     * Función para borrar titulo a empleados
     *
     * @param idEmp toma el numero de empleado
     * @param title toma el titulo del empleado
     */
    public void dltTit(int idEmp, String title) {
        start();
        TitlePK tpk = new TitlePK(idEmp, title);
        em.remove(em.find(Titles.class, tpk));
        end();
    }

    /**
     * funcion para obtener los titulos de un empleado
     *
     * @param idEmp toma el numero del empleado
     * @return lista con todos los titulos del empleado
     */
    public List<Titles> getAll(int idEmp) {
        start();
        TypedQuery<Titles> query = em.createQuery("SELECT s FROM Titles s WHERE emp_no LIKE " + idEmp, Titles.class);
        List<Titles> result = query.getResultList();
        end();
        return result;
    }
}