package Tablas;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tbl_contribuyentes", schema = "dgt", catalog = "")

public class TblContribuyentesEntity {
    
    private long id;
    private String dni;
    private String cognomNom;
    private String adreca;

    @Basic
    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DNI")
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Basic
    @Column(name = "COGNOM_NOM")
    public String getCognomNom() {
        return cognomNom;
    }

    public void setCognomNom(String cognomNom) {
        this.cognomNom = cognomNom;
    }

    @Basic
    @Column(name = "ADRECA")
    public String getAdreca() {
        return adreca;
    }

    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblContribuyentesEntity that = (TblContribuyentesEntity) o;
        return id == that.id &&
                Objects.equals(dni, that.dni) &&
                Objects.equals(cognomNom, that.cognomNom) &&
                Objects.equals(adreca, that.adreca);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, dni, cognomNom, adreca);
    }
}
