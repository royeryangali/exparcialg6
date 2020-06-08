package exparcialg6.demo.repository;

import exparcialg6.demo.entity.Pedido;
import exparcialg6.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido,Integer> {
    List<Pedido> findByUsuario(Usuario usuario);
}
