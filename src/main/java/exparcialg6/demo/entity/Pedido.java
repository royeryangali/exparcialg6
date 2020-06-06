package exparcialg6.demo.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="pedido")
public class Pedido {
    @Id
    @Column(name="idpedido")
    String idpedido;
    @Column(nullable = false, name="Total")
    Float total;
    @Column(nullable = false, name="Fecha")
    Date fecha;


}
