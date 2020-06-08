package exparcialg6.demo.Controller;

import exparcialg6.demo.entity.Producto;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gestor")
public class GestorController {
    @Autowired
    ProductoRepository productoRepository;

    @GetMapping(value = {"", "/"})
    public String listarProductos(Model model, RedirectAttributes attr) {
        model.addAttribute("listaProductos", productoRepository.findAll());
        return "producto/listProduct";
    }

    //CREAR PRODUCTO
    @GetMapping("/crearProducto")
    public String nuevoProductoForm() {
        return "gestor/newProduct";
    }

    @GetMapping("/hola")
    public String nuevoProductoForm1() {
        return "producto/verCheckout";
    }

    @GetMapping("editarProducto")
    public String editarProducto(@RequestParam("id") int id, @ModelAttribute("producto") Producto producto, Model model) {
        Optional<Producto> productoid = productoRepository.findById(id);
        if (productoid.isPresent()) {
            producto = productoid.get();
            model.addAttribute("usuario", producto);
            return "gestor/newProduct";
        } else {
            return "redirect:/gestor/";
        }
    }


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
