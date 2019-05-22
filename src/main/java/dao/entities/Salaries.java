package dao.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "salaries")
public class Salaries {

    @EmbeddedId
    private SalPK idSa;
    @Column(name = "salary")
    private int salary;
    @Transient
    private LocalDate fromDateSal;
    @Transient
    private LocalDate toDateSal;
    @Transient
    private int idEmpSal;

    public Salaries() {
    }

    public Salaries(SalPK idSa, int salary) {
        this.idSa = idSa;
        this.salary = salary;
    }

    public SalPK getIdSa() {
        return idSa;
    }

    public void setIdSa(SalPK idSa) {
        this.idSa = idSa;
    }

    public String getSalary() {
        return "$ " + salary; //retorna salario con el simbolo de $
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDate getFromDateSal() {
        return idSa.getFromDate();
    }

    public void setFromDateSal(SalPK fromDateSal) {
        this.fromDateSal = fromDateSal.getFromDate();
    }

    public LocalDate getToDateSal() {
        return idSa.getToDate();
    }

    public void setToDateSal(SalPK toDateSal) {
        this.toDateSal = toDateSal.getFromDate();
    }

    public int getIdEmpSal() {
        return idSa.getEmpNo();
    }

    public void setIdEmpSal(SalPK idEmpSal) {
        this.idEmpSal = idEmpSal.getEmpNo();
    }

    @Override
    public String toString() {
        return "Salaries{" +
                "idSa=" + idSa +
                ", salary=" + salary +
                ", fromDateSal=" + fromDateSal +
                ", toDateSal=" + toDateSal +
                ", idEmpSal=" + idEmpSal +
                '}';
    }
}