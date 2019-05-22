package dao.entities;

import dao.imp.DepEmployeeImp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "dept_emp")
public class DEmployee {

    @EmbeddedId
    private DeptEmpPK idDE;
    @Column(name = "from_date")
    private LocalDate fromDate;
    @Column(name = "to_date")
    private LocalDate toDate;
    @Transient
    private String deptNo;
    @Transient
    private List<Departments> deptName;
    @Transient
    private int empNo;

    public DEmployee() {
    }

    public DEmployee(DeptEmpPK idEmp, LocalDate fromDate, LocalDate toDate) {
        this.idDE = idEmp;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public DeptEmpPK getIdDE() {
        return idDE;
    }

    public void setIdDE(DeptEmpPK idDE) {
        this.idDE = idDE;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getDeptNo() {
        return idDE.getDeptNo();
    }

    public String getDeptName() {
        return new DepEmployeeImp().getName(getDeptNo());
    }

    public void setDeptNo(DeptEmpPK deptNo) {
        this.deptNo = deptNo.getDeptNo();
    }

    public int getEmpNo() {
        return idDE.getEmpNo();
    }

    public void setEmpNo(DeptEmpPK empNo) {
        this.empNo = empNo.getEmpNo();
    }

    @Override
    public String toString() {
        return "DEmployee{" +
                "idDE=" + idDE +
                ", fromDate=" + fromDate +
                ", ToDate=" + toDate +
                '}';
    }
}