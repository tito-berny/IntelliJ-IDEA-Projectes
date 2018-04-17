package Tablas;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class TblHistorialEntityPK implements Serializable {
    private int contId;
    private int vehiId;

    @Column(name = "Cont_ID")
    @Id
    public int getContId() {
        return contId;
    }

    public void setContId(int contId) {
        this.contId = contId;
    }

    @Column(name = "Vehi_Id")
    @Id
    public int getVehiId() {
        return vehiId;
    }

    public void setVehiId(int vehiId) {
        this.vehiId = vehiId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblHistorialEntityPK that = (TblHistorialEntityPK) o;
        return contId == that.contId &&
                vehiId == that.vehiId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(contId, vehiId);
    }
}
