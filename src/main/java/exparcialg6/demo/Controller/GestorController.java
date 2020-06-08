package exparcialg6.demo.Controller;

import exparcialg6.demo.entity.Producto;
import exparcialg6.demo.entity.Usuario;
import exparcialg6.demo.repository.ProductoRepository;
import exparcialg6.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
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
                                @RequestParam("id") int idproducto, Model model, RedirectAttributes attr) {
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
    public String editarProducto(@ModelAttribute("producto") @Valid Producto producto, Model model, BindingResult
            bindingResult, RedirectAttributes attr, @RequestParam("archivo") MultipartFile file) {

        if (bindingResult.hasErrors()) {
            return "gestor/newProduct";
        } else {
            if (producto.getIdproducto() == 0) {
                attr.addFlashAttribute("msg", "Producto creado exitosamente");
                producto.setCodigo(producto.getNombre().substring(0, 2) + "99");
                if (file.isEmpty()) {
                    model.addAttribute("msg", "Debe subir un archivo");
                    return "producto/listProduct";

                }
                String filename = file.getOriginalFilename();
                if (filename.contains("..")) {
                    model.addAttribute("msg", "Debe subir un archivo");
                    return "producto/listProduct";
                }
                try {
                    producto.setFoto(file.getBytes());
                    producto.setFotonombre(filename);
                    producto.setFotocontenttype(file.getContentType());
                    productoRepository.save(producto);
                    return "redirect:/gestor";

                } catch (
                        IOException e) {
                    e.printStackTrace();
                    model.addAttribute("msg", "Ocurrio un error");
                }

                return "redirect:/gestor";
            } else {
                productoRepository.save(producto);
                attr.addFlashAttribute("msg", "Producto actualizado exitosamente");
                return "redirect:/gestor";
            }
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> mostrarImagen(@PathVariable("id") int id) {
        Optional<Producto> opt = productoRepository.findById(id);
        if (opt.isPresent()) {
            Producto p = opt.get();

            byte[] imagencomobytes = p.getFoto();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.parseMediaType(p.getFotocontenttype()));

            return new ResponseEntity<>(
                    imagencomobytes,
                    httpHeaders,
                    HttpStatus.OK);

        } else {
            return null;
        }

    }

    @PostMapping("/buscarProducto")
    public String buscarProducto(@RequestParam("searchField") String searchField,
                                 Model model) {

        List<Producto> listaProductos = productoRepository.findByNombre(searchField);
        model.addAttribute("listaProductos", listaProductos);
        return "producto/listProduct";
    }

    @GetMapping("/borrarProducto")
    public String borrarProducto(Model model,
                                 @RequestParam("id") int id,
                                 RedirectAttributes attr) {

        Optional<Producto> producto = productoRepository.findById(id);

        if (producto.isPresent()) {
            productoRepository.deleteById(id);
            attr.addFlashAttribute("msg", "Producto borrado exitosamente");
        }
        return "redirect:/gestor";

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

