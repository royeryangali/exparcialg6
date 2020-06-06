package exparcialg6.demo.Controller;

import exparcialg6.demo.entity.Usuario;
import exparcialg6.demo.repository.ProductoRepository;
import exparcialg6.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gestor")
public class GestorController {
    @Autowired
    ProductoRepository productoRepository;

    //CREAR PRODUCTO



    //EDITAR PRODUCTO



    /*
    @GetMapping("gestorEditUsuarioSede")
    public String editarUsuarioSede(@RequestParam("idusuarios") int idusuario, @ModelAttribute("usuarios") Usuario usuario, Model model) {
        Optional<Usuario> usuarioID = usuarioRepository.findById(idusuario);
        if (usuarioID.isPresent()) {
            usuario = usuarioID.get();
            model.addAttribute("usuario", usuario);
            return "Gestor/G-EditUsuarioSede";
        } else {
            return "redirect:/gestor/gestorListaUsuarioSede";
        }
    }
    */




    //BORRAR PRODUCTO










    //VER ESTADISTICAS

    /*
- Cantidad de compras realizadas
- Total facturado
- Cantidad de productos vendidos
- Producto m&aacute;s vendido
- Producto menos vendido
- Producto m&aacute;s caro
- Usuario que m&aacute;s ha gastado en el sistema
    */
}
