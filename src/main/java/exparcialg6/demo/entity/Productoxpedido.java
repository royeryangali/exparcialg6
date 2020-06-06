package exparcialg6.demo.entity;

import javax.persistence.*;

@Entity
@Table(name="productoxpedido")
public class Productoxpedido {

    @Id
    @Column(name="idproductoxpedido")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idproductoxpedido;
    Integer cantidad;
    @ManyToOne
    @JoinColumn(name = "idpedido")
    Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "idproducto")
    Producto producto;
}
