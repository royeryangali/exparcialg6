package exparcialg6.demo.Controller;

import exparcialg6.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdministradorController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping(value = {"","principal"})
    public String principalAdmin(Model model){
        model.addAttribute("usuarios", usuarioRepository.findByRol("Gestor"));
        return "Administrador/A-PagPrincipal"; //PAGINA DE CRUD DE GESTORES
    }




}
