package exparcialg6.demo.Controller;

import exparcialg6.demo.dto.MisPedidos;
import exparcialg6.demo.entity.*;
import exparcialg6.demo.repository.PedidoRepository;
import exparcialg6.demo.repository.ProductoRepository;
import exparcialg6.demo.repository.ProductoxpedidoRepository;
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

    @Autowired
    ProductoxpedidoRepository productoxpedidoRepository;

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


                veces = repetir(Carrito, (Producto) productos.get(i));
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
            System.out.println("debug del producto");
            System.out.println("total: " + total);
            System.out.println(producto.get().getNombre());
            System.out.println(producto.get().getPrecio());
            System.out.println(producto.get().getCodigo());
            System.out.println(producto.get().getDescripcion());
            System.out.println(producto.get().getStock());

            return "producto/listProduct";

        } else {// MENSAJE DE ERROR
            model.addAttribute("listaProductos", productoRepository.findAll());
            model.addAttribute("msg", "Ya ha llegado al limite de productos del mismo tipo");
            System.out.println("No se agrego");
            System.out.println("Tamano de carrito: " + Carrito.size());
            return "producto/listProduct";
        }
    }

    public int repetir(ArrayList<Producto> carrito, Producto o) {
        int a = 0;
        for (int i = 0; i < carrito.size(); i++) {
            if ((int) carrito.get(i).getIdproducto() == (int) o.getIdproducto()) {
                a = a + 1;
            }
        }

        return a;
    }


    //VER CARRITO
    @GetMapping(value={"/VerCarrito","","/"})
    public String VerCarrito(Model model, HttpSession session) {
        ArrayList<Producto> Carrito = (ArrayList<Producto>) session.getAttribute("carrito");
        List productos = productoRepository.findAll();
        ArrayList<exparcialg6.demo.entity.Carrito> lista = new ArrayList<exparcialg6.demo.entity.Carrito>();
        double total = 0.0;
        int veces = 0;
        for (int i = 0; i < productos.size(); i++) {

            veces = repetir(Carrito, (Producto) productos.get(i));
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
        return "producto/verCarrito";
    }

    @GetMapping("Checkout")
    public String Checkout(Model model, HttpSession session) {
        ArrayList<Producto> Carrito = (ArrayList<Producto>) session.getAttribute("carrito");
        List productos = productoRepository.findAll();
        double total = 0.0;
        int veces = 0;
        for (int i = 0; i < productos.size(); i++) {

            veces = repetir(Carrito, (Producto) productos.get(i));
            if (veces > 0) {
                exparcialg6.demo.entity.Carrito carrito = new Carrito();
                carrito.setCantidad(veces);
                carrito.setProducto((Producto) productos.get(i));
                total = total + carrito.getCantidad() * carrito.getProducto().getPrecio();
            }
        }
        session.setAttribute("total", total);
        return "producto/verCheckout";
    }

    @PostMapping("guardarPedido")
    public String guardarPedido(@ModelAttribute("tarjeta") String tarjeta,
                                BindingResult bindingResult,
                                RedirectAttributes attr,
                                Model model,
                                HttpSession session) {

        //VALIDAR LA TARJETA
        System.out.println(tarjeta);
        if (ComprobarTarjeta(tarjeta)) {
            System.out.println("TARJETA COMPROBADA");
            Pedido pedido = new Pedido();
            LocalDate date = java.time.LocalDate.now();
            pedido.setFecha(date);
            pedido.setTotal((double) session.getAttribute("total"));
            String codigo;
            String fecha = String.valueOf(date.getDayOfMonth()) + String.valueOf(date.getMonthValue()) + String.valueOf(date.getYear());
            codigo = "PE" + fecha + (pedidoRepository.findAll().size() + 1);
            pedido.setCodigo(codigo);
            pedido.setUsuario((Usuario) session.getAttribute("user"));
            pedidoRepository.save(pedido);
            ArrayList<Producto> carrito = (ArrayList<Producto>) session.getAttribute("carrito");
            List productos = productoRepository.findAll();
            int veces = 0;
            for (int i = 0; i < productos.size(); i++) {

                veces = repetir(carrito, (Producto) productos.get(i));
                if (veces > 0) {
                    Productoxpedido productoxpedido = new Productoxpedido();
                    productoxpedido.setCantidad(veces);
                    productoxpedido.setPedido(pedido);
                    productoxpedido.setProducto((Producto) productos.get(i));
                    productoxpedidoRepository.save(productoxpedido);
                }
            }

            attr.addFlashAttribute("msg", "Pedido realizado exitosamente");
        } else {
            System.out.println("TARJETA ERRONEA");
            attr.addFlashAttribute("msg", "Error en el numero de tarjeta ingresado");
            return "redirect:/registrado/Checkout";
        }
        return "redirect:/invitado/listarProductos";

    }


    @GetMapping("verPedidos") // TODO BUSCADOR DE MIS PEDIDOS
    public String verPedidos(
                             Model model,
                             HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("user");
        List<Pedido> listaPedidos = pedidoRepository.findByUsuario(usuario);
        if (listaPedidos.isEmpty()) {
            model.addAttribute("msg", "Todavia no ha realizado pedidos");
            return "producto/misPedidos";
        }
        ArrayList<Joke> jokeArrayList = new ArrayList<Joke>();
        for (Pedido pedido: listaPedidos ) {
            Joke joke = new Joke();
            List<MisPedidos> misPedidos = productoxpedidoRepository.listaMisPedidos(pedido.getIdpedido());
            joke.setMisPedidos(misPedidos);
            joke.setPedido(pedido);
            jokeArrayList.add(joke);

        }
        model.addAttribute("lista", jokeArrayList );
        return "producto/misPedidos";
    }


    public boolean ComprobarTarjeta(String Tarjeta) {
        if (Tarjeta != null) {
            if (Tarjeta.matches("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$")) {
                Tarjeta = Tarjeta.replaceAll("-","");

                String mochado = Tarjeta.substring(0, 15); // quita el ultimo

                StringBuilder input1 = new StringBuilder(); //EMPIEZA A REVERSE
                input1.append(mochado);
                String volteado = input1.reverse().toString(); // Fin voltear

                int a1 = Integer.parseInt(volteado.substring(0, 1));
                int a2 = Integer.parseInt(volteado.substring(1, 2));
                int a3 = Integer.parseInt(volteado.substring(2, 3));
                int a4 = Integer.parseInt(volteado.substring(3, 4));
                int a5 = Integer.parseInt(volteado.substring(4, 5));
                int a6 = Integer.parseInt(volteado.substring(5, 6));
                int a7 = Integer.parseInt(volteado.substring(6, 7));
                int a8 = Integer.parseInt(volteado.substring(7, 8));
                int a9 = Integer.parseInt(volteado.substring(8, 9));
                int a10 = Integer.parseInt(volteado.substring(9, 10));
                int a11 = Integer.parseInt(volteado.substring(10, 11));
                int a12 = Integer.parseInt(volteado.substring(11, 12));
                int a13 = Integer.parseInt(volteado.substring(12, 13));
                int a14 = Integer.parseInt(volteado.substring(13, 14));
                int a15 = Integer.parseInt(volteado.substring(14));
                int[] temp = {a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15};
                int sumador = 0;
                int x = 1;
                for (int entero : temp) {
                    if (x % 2 == 1) { // IMPAR
                        entero = entero * 2;
                    } //fin *2 si impar
                    if (entero > 9) {
                        entero = entero - 9;
                    }
                    x= x+1;
                    sumador = sumador + entero;
                }

                boolean bool = ((10 - (sumador % 10)) % 10) == Integer.parseInt(Tarjeta.substring(15));
                if (bool) {
                    return true;
                } else {
                    return false;
                }

            } else {
                return false;
            }
        } else {
            return false;
        }
    }


}
