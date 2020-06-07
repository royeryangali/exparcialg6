package exparcialg6.demo.Controller;

import exparcialg6.demo.entity.Producto;
import exparcialg6.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/registrado")
public class RegistradoController {
    @Autowired
    ProductoRepository productoRepository;

    @GetMapping("AgregarCarritoVerMas")  // Agregar al carrito
    public String AgregarCarritoVerMas(Model model, HttpSession session, @RequestParam("id") int id) {
        List<Producto> Carrito = (List<Producto>) session.getAttribute("carrito");
        Optional<Producto> producto = productoRepository.findById(id);
        int a = 0;
        for (int i = 1; i <= Carrito.size(); i++) {
            if (Carrito.get(i).getIdproducto() == id) {
                a = a + 1;
            }
        }

        if (a <= 3) { // AGREGAR AL CARRITO

            Carrito.add(producto.get());
            session.setAttribute("carrito", Carrito);
            session.setAttribute("Tamano", Carrito.size());
            model.addAttribute("producto", productoRepository.findById(id).get());
            model.addAttribute("msg", producto.get().getNombre() + "Agregado exitosamente");
            return "producto/vermasProduct?id=" + id;

        } else {// MENSAJE DE ERROR
            model.addAttribute("producto", productoRepository.findById(id));
            model.addAttribute("msg", "Ya ha llegado al limite de productos del mismo tipo");
            return "producto/vermasProduct?id=" + id;
        }
    }

    @GetMapping("AgregarCarrito")  // Agregar al carrito
    public String agregarCarrito(Model model, HttpSession session, @RequestParam("id") int id) {
        List<Producto> Carrito = (List<Producto>) session.getAttribute("carrito");
        Optional<Producto> producto = productoRepository.findById(id);
        int a = 0;
        for (int i = 1; i <= Carrito.size(); i++) {
            if (Carrito.get(i).getIdproducto() == id) {
                a = a + 1;
            }
        }

        if (a <= 3) { // AGREGAR AL CARRITO

            Carrito.add(producto.get());
            session.setAttribute("carrito", Carrito);
            session.setAttribute("Tamano", Carrito.size());
            model.addAttribute("listaProductos", productoRepository.findAll());
            model.addAttribute("msg", producto.get().getNombre() + "Agregado exitosamente");
            return "producto/listProduct";

        } else {// MENSAJE DE ERROR
            model.addAttribute("listaProductos", productoRepository.findAll());
            model.addAttribute("msg", "Ya ha llegado al limite de productos del mismo tipo");
            return "producto/listProduct";
        }
    }


}
