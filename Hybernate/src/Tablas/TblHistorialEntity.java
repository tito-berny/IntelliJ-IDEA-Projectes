package Tablas;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tbl_historial", schema = "dgt", catalog = "")

@IdClass(TblHistorialEntityPK.class)
public class TblHistorialEntity {
    private int contId;
    private int vehiId;
    private String dataAlta;
    private String dataBaixa;

    @Id
    @Column(name = "Cont_ID")
    public int getContId() {
        return contId;
    }

    public void setContId(int contId) {
        this.contId = contId;
    }

    @Id
    @Column(name = "Vehi_Id")
    public int getVehiId() {
        return vehiId;
    }

    public void setVehiId(int vehiId) {
        this.vehiId = vehiId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblHistorialEntity that = (TblHistorialEntity) o;
        return contId == that.contId &&
                vehiId == that.vehiId &&
                Objects.equals(dataAlta, that.dataAlta) &&
                Objects.equals(dataBaixa, that.dataBaixa);
    }

    @Override
    public int hashCode() {

        return Objects.hash(contId, vehiId, dataAlta, dataBaixa);
    }
}
