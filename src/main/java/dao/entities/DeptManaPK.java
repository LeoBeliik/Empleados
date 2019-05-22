package dao.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DeptManaPK implements Serializable {
    /*
     *Esta clase se encarga de manejar las llaves derivadas de las clases DEmployee y DManager
     */
    @Column(name = "dept_no")
    private String DeptNo;
    @Column(name = "emp_no")
    private int EmpNo;

    public DeptManaPK() {
    }

    public DeptManaPK(String deptNo, int empNo) {
        DeptNo = deptNo;
        EmpNo = empNo;
    }

    public String getDeptNo() {
        return DeptNo;
    }

    public void setDeptNo(String deptNo) {
        DeptNo = deptNo;
    }

    public int getEmpNo() {
        return EmpNo;
    }

    public void setEmpNo(int empNo) {
        EmpNo = empNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeptManaPK deptPK = (DeptManaPK) o;
        return DeptNo.equals(deptPK.DeptNo) &&
                EmpNo == deptPK.EmpNo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(DeptNo, EmpNo);
    }

    @Override
    public String toString() {
        return "DeptManaPK{" +
                "DeptNo=" + DeptNo +
                ", EmpNo=" + EmpNo +
                '}';
    }
}