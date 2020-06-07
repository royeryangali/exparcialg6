package exparcialg6.demo.Controller;


import exparcialg6.demo.entity.Usuario;
import exparcialg6.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/invitado")
public class InvitadoController {
    @Autowired
    ProductoRepository productoRepository;




    @GetMapping(value={"listarProductos","","/"})  // LISTAR PRODUCTOS
    public String listarProductos( Model model) {
            model.addAttribute("listaProductos", productoRepository.findAll());
            return "producto/listProduct";
    }

    @GetMapping("/listarVerMas") // VER UN SOLO PRODUCTO
    public String VerMas( Model model,@RequestParam("id") int id ) {
        model.addAttribute("producto", productoRepository.findById(id).get());
        return "producto/vermasProduct";
    }


    @GetMapping("/registrarse") // MUESTRA LA PAGINA DE REGISTRO DE USUARIO REGISTRADO
    public String registrarse(@ModelAttribute("usuario") Usuario usuario) {
        return "usuario/registrarUsuario";
    }

    @GetMapping("/recuperar") // MUESTRA LA PAGINA DE RECUPERAR CONTRA
    public String recuperar( Model model) {
        return "login/recoveryForm";
    }





















}
