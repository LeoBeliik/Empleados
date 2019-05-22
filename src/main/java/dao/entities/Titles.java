package dao.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "titles")
public class Titles {

    @EmbeddedId
    private TitlePK idTi;
    @Column(name = "from_Date")
    private LocalDate fromDateTit;
    @Column(name = "to_Date")
    private LocalDate toDateTit;
    @Transient
    private String title;
    @Transient
    private int idEmpTit;

    public Titles() {
    }

    public Titles(TitlePK idTi, LocalDate fromDate, LocalDate toDateTit) {
        this.idTi = idTi;
        fromDateTit = fromDate;
        this.toDateTit = toDateTit;
    }

    public TitlePK getIdTi() {
        return idTi;
    }

    public void setIdTi(TitlePK idTi) {
        this.idTi = idTi;
    }

    public LocalDate getFromDateTit() {
        return fromDateTit;
    }

    public void setFromDateTit(LocalDate fromDateTit) {
        this.fromDateTit = fromDateTit;
    }

    public LocalDate getToDateTit() {
        return toDateTit;
    }

    public void setToDateTit(LocalDate toDateTit) {
        this.toDateTit = toDateTit;
    }

    public String getTitle() {
        return idTi.getTitle();
    }

    public void setTitle(TitlePK title) {
        this.title = title.getTitle();
    }

    public int getIdEmpTit() {
        return idTi.getEmpNo();
    }

    public void setIdEmpTit(TitlePK idEmpTit) {
        this.idEmpTit = idEmpTit.getEmpNo();
    }

    @Override
    public String toString() {
        return "Titles{" +
                "idTi=" + idTi +
                ", fromDateTit=" + fromDateTit +
                ", toDateTit=" + toDateTit +
                ", title='" + title + '\'' +
                ", idEmpTit=" + idEmpTit +
                '}';
    }
}