package exparcialg6.demo.Controller;


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




    @GetMapping("listarProductos")  // LISTAR PRODUCTOS
    public String listarProductos( Model model) {
            model.addAttribute("listaProductos", productoRepository.findAll());
            return "producto/listProduct";
    }

    @GetMapping("/listarVerMas")
    public String VerMas( Model model,@RequestParam("id") int id ) {
        model.addAttribute("producto", productoRepository.findById(id));
        return "producto/vermasProduct";
    }
























}
