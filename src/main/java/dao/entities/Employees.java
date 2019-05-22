package dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employees {

    @Id
    @Column(name = "emp_no")
    private int EmpNo;
    @Column(name = "first_name")
    private String FirstName;
    @Column(name = "last_name")
    private String LastName;
    @Column(name = "gender")
    private char Sex;
    @Column(name = "hire_date")
    private LocalDate HireDate;
    @Column(name = "birth_date")
    private LocalDate BirthDate;

    public Employees() {
    }

    public Employees(int empNo, String firstName, String lastName, char sex, LocalDate hireDate, LocalDate birthDate) {
        EmpNo = empNo;
        FirstName = firstName;
        LastName = lastName;
        Sex = sex;
        HireDate = hireDate;
        BirthDate = birthDate;
    }

    public int getEmpNo() {
        return EmpNo;
    }

    public void setEmpNo(int empNo) {
        EmpNo = empNo;
    }

    public LocalDate getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        BirthDate = birthDate;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public char getSex() {
        return Sex;
    }

    public void setSex(char sex) {
        Sex = sex;
    }

    public LocalDate getHireDate() {
        return HireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        HireDate = hireDate;
    }

    @Override
    public String toString() {
        return "Employees{" +
                "EmpNo=" + EmpNo +
                ", BirthDate=" + BirthDate +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Sex=" + Sex +
                ", HireDate=" + HireDate +
                '}';
    }
}