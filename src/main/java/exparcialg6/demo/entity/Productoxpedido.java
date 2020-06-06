package exparcialg6.demo.entity;

import javax.persistence.*;

@Entity
@Table(name="productoxpedido")
public class Productoxpedido {

    @Id
    @Column(name="idproductoxpedido")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproductoxpedido;

    @Column(nullable = false, name="cantidad")
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "idpedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "idproducto")
    private Producto producto;

    public Integer getIdproductoxpedido() {
        return idproductoxpedido;
    }

    public void setIdproductoxpedido(Integer idproductoxpedido) {
        this.idproductoxpedido = idproductoxpedido;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
