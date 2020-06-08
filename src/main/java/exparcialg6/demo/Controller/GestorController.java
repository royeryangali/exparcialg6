package exparcialg6.demo.Controller;

import exparcialg6.demo.entity.Producto;
import exparcialg6.demo.entity.Usuario;
import exparcialg6.demo.repository.ProductoRepository;
import exparcialg6.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gestor")
public class GestorController {
    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping(value = {"", "/"})
    public String listarProductos(Model model, RedirectAttributes attr) {
        model.addAttribute("listaProductos", productoRepository.findAll());
        return "producto/listProduct";
    }

    //CREAR PRODUCTO
    @GetMapping("/crearProducto")
    public String nuevoProductoForm(@ModelAttribute("producto") Producto producto, Model model) {
        producto.setIdproducto(0);
        return "gestor/newProduct";
    }

    @GetMapping("/editarProducto")
    public String editarProduct(@ModelAttribute("producto") Producto producto,
                                @RequestParam("id") int idproducto, Model model, RedirectAttributes attr){
        Optional<Producto> opt = productoRepository.findById(idproducto);
        if (opt.isPresent()) {
            producto = opt.get();
            model.addAttribute("producto", producto);
            return "gestor/newProduct";
        } else {
            return "redirect:/gestor";
        }

    }

    @PostMapping("/guardarProducto")
    public String editarProducto(@ModelAttribute("producto") @Valid Producto producto, Model model , BindingResult bindingResult,
                                 RedirectAttributes attr){

        if (bindingResult.hasErrors()) {
            return "gestor/newProduct";
        } else {
            if (producto.getIdproducto() == 0) {
                attr.addFlashAttribute("msg", "Producto creado exitosamente");
                producto.setCodigo(producto.getNombre().substring(0,2)+"99");
                productoRepository.save(producto);
                return "redirect:/gestor";
            } else {
                productoRepository.save(producto);
                attr.addFlashAttribute("msg", "Producto actualizado exitosamente");
                return "redirect:/gestor";
            }
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
