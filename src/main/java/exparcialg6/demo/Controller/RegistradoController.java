package exparcialg6.demo.Controller;

import exparcialg6.demo.entity.Carrito;
import exparcialg6.demo.entity.Pedido;
import exparcialg6.demo.entity.Producto;
import exparcialg6.demo.repository.PedidoRepository;
import exparcialg6.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/registrado")
public class RegistradoController {
    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @GetMapping("AgregarCarritoVerMas")  // Agregar al carrito
    public String AgregarCarritoVerMas(Model model, HttpSession session, @RequestParam("id") int id) {
        ArrayList<Producto> Carrito = (ArrayList<Producto>) session.getAttribute("carrito");
        Optional<Producto> producto = productoRepository.findById(id);
        int a = 0;
        for (int i = 0; i <= Carrito.size() - 1; i++) {
            if (Carrito.get(i).getIdproducto() == id) {
                a = a + 1;
            }
        }
        System.out.println("Antes de agregar el carrito tiene " + a + " " + producto.get().getNombre() + "s");
        if (a <= 3) { // AGREGAR AL CARRITO

            Carrito.add(producto.get());
            session.setAttribute("carrito", Carrito);
            model.addAttribute("producto", productoRepository.findById(id).get());
            model.addAttribute("msg", producto.get().getNombre() + "Agregado exitosamente");
            System.out.println("Luego de agregar el carrito tiene " + (a + 1) + " " + producto.get().getNombre() + "s");
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
        for (int i = 0; i <= Carrito.size() - 1; i++) {
            if (Carrito.get(i).getIdproducto() == id) {
                a = a + 1;
            }
        }
        System.out.println("Antes de agregar el carrito tiene " + a + " " + producto.get().getNombre() + "s");
        if (a <= 3) { // AGREGAR AL CARRITO

            Carrito.add(producto.get());
            session.setAttribute("carrito", Carrito);
            // GET TOTAL

            List productos = productoRepository.findAll();
            //ArrayList<exparcialg6.demo.entity.Carrito> lista = new ArrayList<exparcialg6.demo.entity.Carrito>();

            double total = 0.0;
            int veces = 0;
            for (int i = 0; i < productos.size(); i++) {

                veces = Collections.frequency(Carrito, productos.get(i));
                if (veces > 0) {
                    exparcialg6.demo.entity.Carrito carrito = new Carrito();
                    carrito.setCantidad(veces);
                    carrito.setProducto((Producto) productos.get(i));

                    total = total + carrito.getCantidad() * carrito.getProducto().getPrecio();
                    //lista.add(carrito);
                }
            }


            session.setAttribute("total", total);
            // END GET TOTAL
            model.addAttribute("listaProductos", productoRepository.findAll());
            model.addAttribute("msg", producto.get().getNombre() + "Agregado exitosamente");
            System.out.println("Luego de agregar el carrito tiene " + (a + 1) + " " + producto.get().getNombre() + "s");
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
    @GetMapping("VerCarrito")
    public String VerCarrito(Model model, HttpSession session) {
        ArrayList<Producto> Carrito = (ArrayList<Producto>) session.getAttribute("carrito");
        List productos = productoRepository.findAll();
        ArrayList<exparcialg6.demo.entity.Carrito> lista = new ArrayList<exparcialg6.demo.entity.Carrito>();

        double total = 0.0;
        int veces = 0;
        for (int i = 0; i < productos.size(); i++) {

            veces = Collections.frequency(Carrito, productos.get(i));
            if (veces > 0) {
                exparcialg6.demo.entity.Carrito carrito = new Carrito();
                carrito.setCantidad(veces);
                carrito.setProducto((Producto) productos.get(i));
                total = total + carrito.getCantidad() * carrito.getProducto().getPrecio();
                lista.add(carrito);
            }
        }
        session.setAttribute("total", total);
        model.addAttribute("lista", lista); // DA UNA LISTA DEL ENTITY CARRITO
        //TODO Ordenar la lista en orden del total a gastar
        return "producto/VerCarrito";
    }

    @GetMapping("Checkout")
    public String Checkout(Model model, HttpSession session) {
        return "checkout"; //TODO PONER EL NOMBRE DEL HTML DE CHECKOUT
    }

    @PostMapping("guardarPedido")
    public String guardarPedido(@ModelAttribute("tarjeta") String tarjeta,
                                  BindingResult bindingResult,
                                  RedirectAttributes attr,
                                  Model model,
                                  HttpSession session) {

            //VALIDAR LA TARJETA
            if(ComprobarTarjeta(tarjeta)){

                Pedido pedido = new Pedido();
                LocalDate date = java.time.LocalDate.now();
                pedido.setFecha(date);
                pedido.setTotal((float) session.getAttribute("total"));
                String codigo;
                String fecha = String.valueOf(date.getDayOfMonth())  + String.valueOf(date.getMonthValue()) +  String.valueOf(date.getYear());
                codigo = "PE" + fecha + (pedidoRepository.findAll().size() + 1 );
                pedido.setCodigo(codigo);
                pedidoRepository.save(pedido);
                attr.addFlashAttribute("msg", "Pedido realizado exitosamente");
            }else{
                attr.addFlashAttribute("msg", "Error en el numero de tarjeta ingresado");
                return "redirect:/registrado/Checkout";
            }
            return "redirect:/invitado/listarProductos";

    }


    public boolean ComprobarTarjeta(String Tarjeta){
        if(Tarjeta != null){
            if(Tarjeta.matches("^\\d{16}$")){
                String mochado = Tarjeta.substring(0,15);
                return true;

            }else {
                return false;
            }
        }else{
            return false;
        }
    }

}
