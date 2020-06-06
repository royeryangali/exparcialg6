package exparcialg6.demo.repository;

import exparcialg6.demo.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,String> {
}
