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
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/recuperarContraseña")
    public String recuperarContraseña(@RequestParam("correo") String correo, Model model) {
        Boolean existe = false;
        for (String correo2 : usuarioRepository.listaCorreos()) {
            if (correo.equals(correo2)) {
                existe = true;
            }
        }
        if (existe) {
            model.addAttribute("msg", "Se le ha enviado un correo a su dirección para restablecer la contraseña");
            return "/login/loginForm";
        } else {
            model.addAttribute("msg", "No existe dicho usuario");
            return "/login/recoveryForm";
        }
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingresult,
                                 RedirectAttributes attr, @RequestParam("confirmar") String confirmar, Model model) {

        if (confirmar.equals(usuario.getPassword())) {
            if (bindingresult.hasErrors()) {
                return "usuario/registrarUsuario";
            } else {
                usuario.setActivo(1);
                usuario.setRol("registrado");
                BCryptPasswordEncoder b = new BCryptPasswordEncoder();
                usuario.setPassword(b.encode(usuario.getPassword()));
                usuarioRepository.registrarUsuario(usuario.getNombre(), usuario.getApellido(), usuario.getDni(), usuario.getCorreo(),
                        usuario.getRol(), usuario.getPassword(), usuario.getActivo());
                model.addAttribute("msg", "Se ha creado su usuario");
                return "/login/loginForm";
            }
        } else {
            model.addAttribute("msg", "Las contraseñas no coinciden");
            return "usuario/registrarUsuario";
        }
    }

}


