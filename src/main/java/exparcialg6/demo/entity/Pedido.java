package exparcialg6.demo.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="pedido")
public class Pedido {
    @Id
    @Column(nullable = false, name="idpedido")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpedido;

    @Column(nullable = false, name="total")
    private double total;

    @Column(nullable = false, name="fecha")
    private LocalDate fecha;

    @Column(nullable = false, name="codigo")
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;



    public Integer getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(Integer idpedido) {
        this.idpedido = idpedido;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }



    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
