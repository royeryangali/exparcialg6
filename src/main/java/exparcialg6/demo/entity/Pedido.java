package exparcialg6.demo.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="pedido")
public class Pedido {
    @Id
    @Column(nullable = false, name="idpedido")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    String idpedido;

    @Column(nullable = false, name="total")
    private
    Float total;

    @Column(nullable = false, name="fecha")
    private
    Date fecha;

    @Column(nullable = false, name="codigo")
    private
    String codigo;

    public String getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(String idpedido) {
        this.idpedido = idpedido;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
