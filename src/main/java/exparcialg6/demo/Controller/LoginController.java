package exparcialg6.demo.Controller;

import exparcialg6.demo.entity.Producto;
import exparcialg6.demo.entity.Usuario;
import exparcialg6.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class LoginController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/login/loginForm";
    }


    @GetMapping("/redirectByRole")
    public String redirigir(Authentication auth, HttpSession session) {
        String rol = "";

        for (GrantedAuthority role : auth.getAuthorities()) {
            rol = role.getAuthority();
        }
        if (rol.equalsIgnoreCase("administrador")) {
            Usuario usuarioLogueado = usuarioRepository.findByCorreo(auth.getName());
            session.setAttribute("user", usuarioLogueado);
            return "redirect:/admin";
        } else if (rol.equalsIgnoreCase("registrado")) {
            Usuario usuarioLogueado = usuarioRepository.findByCorreo(auth.getName());
            session.setAttribute("user", usuarioLogueado);
            ArrayList<Producto> Carrito = new ArrayList<Producto>();
            session.setAttribute("carrito", Carrito);
            return "redirect:/invitado/listarProductos";
        } else if (rol.equalsIgnoreCase("gestor")) {
            Usuario usuarioLogueado = usuarioRepository.findByCorreo(auth.getName());
            session.setAttribute("user", usuarioLogueado);
            return "redirect:/gestor";
        } else {
            return "index";
        }
    }

}
