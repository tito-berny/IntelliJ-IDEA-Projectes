package Tablas;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tbl_vehicles", schema = "dgt", catalog = "")
public class TblVehiclesEntity {
    private long id;
    private String matricula;
    private String bastidor;
    private String nMotor;
    private String dataAlta;
    private String dataBaixa;
    private String tipusBaixa;

    @Id
    @Column(name = "Id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    @Column(name = "Data_Alta")
    public String getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(String dataAlta) {
        this.dataAlta = dataAlta;
    }

    @Basic
    @Column(name = "Data_Baixa")
    public String getDataBaixa() {
        return dataBaixa;
    }

    public void setDataBaixa(String dataBaixa) {
        this.dataBaixa = dataBaixa;
    }

    @Basic
    @Column(name = "Tipus_Baixa")
    public String getTipusBaixa() {
        return tipusBaixa;
    }

    public void setTipusBaixa(String tipusBaixa) {
        this.tipusBaixa = tipusBaixa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblVehiclesEntity that = (TblVehiclesEntity) o;
        return id == that.id &&
                Objects.equals(matricula, that.matricula) &&
                Objects.equals(bastidor, that.bastidor) &&
                Objects.equals(nMotor, that.nMotor) &&
                Objects.equals(dataAlta, that.dataAlta) &&
                Objects.equals(dataBaixa, that.dataBaixa) &&
                Objects.equals(tipusBaixa, that.tipusBaixa);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, matricula, bastidor, nMotor, dataAlta, dataBaixa, tipusBaixa);
    }
}
