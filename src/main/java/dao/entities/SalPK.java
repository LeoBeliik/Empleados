package dao.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class SalPK implements Serializable {
    /*
     *Esta clase se encarga de manejar las llaves derivadas de las clases Salaries
     */
    @Column(name = "emp_no")
    private int EmpNo;
    @Column(name = "from_date")
    private LocalDate FromDate;
    @Column(name = "to_date")
    private LocalDate ToDate;

    public SalPK() {
    }

    public SalPK(int empNo, LocalDate fromDate, LocalDate toDate) {
        EmpNo = empNo;
        FromDate = fromDate;
        ToDate = toDate;
    }

    public int getEmpNo() {
        return EmpNo;
    }

    public void setEmpNo(int empNo) {
        EmpNo = empNo;
    }

    public LocalDate getFromDate() {
        return FromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        FromDate = fromDate;
    }

    public LocalDate getToDate() {
        return ToDate;
    }

    public void setToDate(LocalDate toDate) {
        ToDate = toDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalPK salPK = (SalPK) o;
        return EmpNo == (salPK.EmpNo) &&
                FromDate.equals(salPK.FromDate) &&
                ToDate.equals(salPK.ToDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(EmpNo, FromDate, ToDate);
    }

    @Override
    public String toString() {
        return EmpNo + "," + FromDate + "," + ToDate;
    }
}