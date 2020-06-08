package exparcialg6.demo.repository;

import exparcialg6.demo.entity.Pedido;
import exparcialg6.demo.entity.Producto;
import exparcialg6.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Integer> {
    List<Producto> findByNombre(String nombre);
}
