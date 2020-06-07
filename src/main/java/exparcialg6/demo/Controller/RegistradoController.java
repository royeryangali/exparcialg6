package exparcialg6.demo.Controller;

import exparcialg6.demo.entity.Carrito;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/registrado")
public class RegistradoController {
    @Autowired
    ProductoRepository productoRepository;

    @GetMapping("AgregarCarritoVerMas")  // Agregar al carrito
    public String AgregarCarritoVerMas(Model model, HttpSession session, @RequestParam("id") int id) {
        ArrayList<Producto> Carrito = (ArrayList<Producto>) session.getAttribute("carrito");
        Optional<Producto> producto = productoRepository.findById(id);
        int a = 0;
        for (int i = 0; i <= Carrito.size()-1; i++) {
            if (Carrito.get(i).getIdproducto() == id) {
                a = a + 1;
            }
        }
        System.out.println("Antes de agregar el carrito tiene " + a + " " + producto.get().getNombre() + "s");
        if (a <= 3) { // AGREGAR AL CARRITO

            Carrito.add(producto.get());
            session.setAttribute("carrito", Carrito);
            session.setAttribute("Tamano", Carrito.size());
            model.addAttribute("producto", productoRepository.findById(id).get());
            model.addAttribute("msg", producto.get().getNombre() + "Agregado exitosamente");
            System.out.println("Luego de agregar el carrito tiene " + (a+1) + " " + producto.get().getNombre() + "s");
            System.out.println("Tamano de carrito: " + Carrito.size());


            return "producto/vermasProduct";

        } else {// MENSAJE DE ERROR
            model.addAttribute("producto", productoRepository.findById(id).get());
            model.addAttribute("msg", "Ya ha llegado al limite de productos del mismo tipo");
            System.out.println("No se agrego");
            System.out.println("Tamano de carrito: " + Carrito.size());
            return "producto/vermasProduct";
        }
    }

    @GetMapping("AgregarCarrito")  // Agregar al carrito
    public String agregarCarrito(Model model, HttpSession session, @RequestParam("id") int id) {
        ArrayList<Producto> Carrito = (ArrayList<Producto>) session.getAttribute("carrito");
        Optional<Producto> producto = productoRepository.findById(id);
        int a = 0;
        for (int i = 0; i <= Carrito.size()-1; i++) {
            if (Carrito.get(i).getIdproducto() == id) {
                a = a + 1;
            }
        }
        System.out.println("Antes de agregar el carrito tiene " + a + " " + producto.get().getNombre() + "s");
        if (a <= 3) { // AGREGAR AL CARRITO

            Carrito.add(producto.get());
            session.setAttribute("carrito", Carrito);
            session.setAttribute("Tamano", Carrito.size());
            model.addAttribute("listaProductos", productoRepository.findAll());
            model.addAttribute("msg", producto.get().getNombre() + "Agregado exitosamente");
            System.out.println("Luego de agregar el carrito tiene " + (a+1) + " " + producto.get().getNombre() + "s");
            System.out.println("Tamano de carrito: " + Carrito.size());
            return "producto/listProduct";

        } else {// MENSAJE DE ERROR
            model.addAttribute("listaProductos", productoRepository.findAll());
            model.addAttribute("msg", "Ya ha llegado al limite de productos del mismo tipo");
            System.out.println("No se agrego");
            System.out.println("Tamano de carrito: " + Carrito.size());
            return "producto/listProduct";
        }
    }







        //VER CARRITO
    @GetMapping("VerCarrito")public String VerCarrito(Model model, HttpSession session) {
        ArrayList<Producto> Carrito = (ArrayList<Producto>) session.getAttribute("carrito");
        List productos = productoRepository.findAll();
        ArrayList<exparcialg6.demo.entity.Carrito> lista = new ArrayList<exparcialg6.demo.entity.Carrito>();

        int veces = 0;
        for (int i = 0; i < productos.size(); i++) {

            veces = Collections.frequency(Carrito, productos.get(i));
            if(veces > 0){
                exparcialg6.demo.entity.Carrito carrito = new Carrito();
                carrito.setCantidad(veces);
                carrito.setProducto((Producto) productos.get(i));
                lista.add(carrito);
            }
        }
        model.addAttribute("lista", lista); // DA UNA LISTA DEL ENTITY CARRITO
        //TODO Ordenar la lista en orden del total a gastar
        return "vercarrito"; //TODO PONER EL NOMBRE DEL HTML PARA VER EL CARRITO





    }







}
