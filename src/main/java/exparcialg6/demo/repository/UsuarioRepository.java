package exparcialg6.demo.repository;

import exparcialg6.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    public Usuario findByCorreo(String email);
    public List<Usuario> findByRol(String tipo);

}
