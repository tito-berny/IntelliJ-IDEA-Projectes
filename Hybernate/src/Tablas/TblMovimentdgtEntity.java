package Tablas;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tbl_movimentdgt", schema = "dgt", catalog = "")
public class TblMovimentdgtEntity {
    private long id;
    private String abm;
    private String tipus;
    private String data;
    private String matricula;
    private String bastidor;
    private String nMotor;
    private String dni;
    private String gognomsNom;
    private String adreca;

    @Id
    @Column(name = "Id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ABM")
    public String getAbm() {
        return abm;
    }

    public void setAbm(String abm) {
        this.abm = abm;
    }

    @Basic
    @Column(name = "TIPUS")
    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    @Basic
    @Column(name = "Data")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Basic
    @Column(name = "Matricula")
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Basic
    @Column(name = "BASTIDOR")
    public String getBastidor() {
        return bastidor;
    }

    public void setBastidor(String bastidor) {
        this.bastidor = bastidor;
    }

    @Basic
    @Column(name = "N_MOTOR")
    public String getnMotor() {
        return nMotor;
    }

    public void setnMotor(String nMotor) {
        this.nMotor = nMotor;
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
    @Column(name = "GOGNOMS_NOM")
    public String getGognomsNom() {
        return gognomsNom;
    }

    public void setGognomsNom(String gognomsNom) {
        this.gognomsNom = gognomsNom;
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
        TblMovimentdgtEntity that = (TblMovimentdgtEntity) o;
        return id == that.id &&
                Objects.equals(abm, that.abm) &&
                Objects.equals(tipus, that.tipus) &&
                Objects.equals(data, that.data) &&
                Objects.equals(matricula, that.matricula) &&
                Objects.equals(bastidor, that.bastidor) &&
                Objects.equals(nMotor, that.nMotor) &&
                Objects.equals(dni, that.dni) &&
                Objects.equals(gognomsNom, that.gognomsNom) &&
                Objects.equals(adreca, that.adreca);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, abm, tipus, data, matricula, bastidor, nMotor, dni, gognomsNom, adreca);
    }
}
