package dao.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TitlePK implements Serializable {

    @Column(name = "emp_no")
    private int EmpNo;
    @Column(name = "title")
    private String Title;

    public TitlePK() {
    }

    public TitlePK(int empNo, String title) {
        EmpNo = empNo;
        Title = title;
    }

    public int getEmpNo() {
        return EmpNo;
    }

    public void setEmpNo(int empNo) {
        EmpNo = empNo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TitlePK titlePK = (TitlePK) o;
        return EmpNo == (titlePK.EmpNo) &&
                Title.equals(titlePK.Title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(EmpNo, Title);
    }

    @Override
    public String toString() {
        return "TitlePK{" +
                "EmpNo=" + EmpNo +
                ", Title='" + Title + '\'' +
                '}';
    }
}