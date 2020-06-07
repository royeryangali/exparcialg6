package exparcialg6.demo.Controller;

import exparcialg6.demo.entity.Producto;
import exparcialg6.demo.entity.Usuario;
import exparcialg6.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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

    @PostMapping("/a")
    public String a(@ModelAttribute("usuario") Usuario usuario) {
        usuario.setActivo(1);
        usuario.setRol("Invitado");
        usuarioRepository.save(usuario);
        return "/login/loginForm";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingresult) {

        if (bindingresult.hasErrors()){
            return "invitado/registrarse";
        }
        else {
            usuario.setActivo(1);
            usuario.setRol("Invitado");
            BCryptPasswordEncoder b = new BCryptPasswordEncoder();
            usuario.setPassword(b.encode(usuario.getPassword()));
            usuarioRepository.registrarUsuario(usuario.getNombre(), usuario.getApellido(), usuario.getDni(), usuario.getCorreo(),
                    usuario.getRol(), usuario.getPassword(), usuario.getActivo());
            return "/login/loginForm";
        }
    }

}


