package exparcialg6.demo.repository;

import exparcialg6.demo.dto.MisPedidos;
import exparcialg6.demo.entity.Pedido;
import exparcialg6.demo.entity.Producto;
import exparcialg6.demo.entity.Productoxpedido;
import exparcialg6.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoxpedidoRepository extends JpaRepository<Productoxpedido,Integer> {


    @Query(value = "Select x.cantidad, pr.nombre" +
            " from pedido pe" +
            " inner join productoxpedido x on x.idpedido = pe.idpedido" +
            " inner join producto pr on pr.idproducto = x.idproducto" +
            " where pe.idpedido = ?1",nativeQuery = true)
    List<MisPedidos> listaMisPedidos(int idpedido);
}
