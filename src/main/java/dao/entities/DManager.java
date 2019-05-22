package dao.entities;

import dao.imp.DepManagerImp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "dept_manager")
public class DManager {

    @EmbeddedId
    private DeptManaPK idMan;
    @Column(name = "from_date")
    private LocalDate FromDate;
    @Column(name = "to_date")
    private LocalDate ToDate;
    @Transient
    private String deptNo;
    @Transient
    private List<Departments> deptName;
    @Transient
    private int empNo;

    public DManager() {
    }

    public DManager(DeptManaPK idMan, LocalDate fromDate, LocalDate toDate) {
        this.idMan = idMan;
        FromDate = fromDate;
        ToDate = toDate;
    }

    public DeptManaPK getIdMan() {
        return idMan;
    }

    public void setIdMan(DeptManaPK idMan) {
        this.idMan = idMan;
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

    public String getDeptNo() {
        return idMan.getDeptNo();
    }

    public String getDeptName() {
        return new DepManagerImp().getName(getDeptNo());
    }

    public void setDeptNo(DeptManaPK deptNo) {
        this.deptNo = deptNo.getDeptNo();
    }

    public int getEmpNo() {
        return idMan.getEmpNo();
    }

    public void setEmpNo(DeptManaPK empNo) {
        this.empNo = empNo.getEmpNo();
    }

    @Override
    public String toString() {
        return "DManager{" +
                "idMan=" + idMan +
                ", FromDate=" + FromDate +
                ", ToDate=" + ToDate +
                ", deptNo='" + deptNo + '\'' +
                ", empNo=" + empNo +
                '}';
    }
}