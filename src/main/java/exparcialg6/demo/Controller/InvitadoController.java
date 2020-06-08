package exparcialg6.demo.Controller;


import exparcialg6.demo.entity.Producto;
import exparcialg6.demo.entity.Usuario;
import exparcialg6.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/invitado", "", "/"})
public class InvitadoController {
    @Autowired
    ProductoRepository productoRepository;


    @GetMapping(value = {"/listarProductos", "", "/"})  // LISTAR PRODUCTOS
    public String listarProductos(Model model, RedirectAttributes attr,  @RequestParam(required = false) Integer pag) {
        if(pag == null){
            pag = 0;
        }
        List<Producto> xd = productoRepository.findAll();
        int a = xd.size() / 7;


        ArrayList<Producto> enviar = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if(xd.size() > i + pag*7) {
                enviar.add(xd.get(i + pag * 7));
            }
        }

        model.addAttribute("listaProductos", enviar);
        model.addAttribute("paginacion", a);
        return "producto/listProduct";
    }

    @GetMapping("/listarVerMas") // VER UN SOLO PRODUCTO
    public String VerMas(Model model, @RequestParam("id") int id) {
        model.addAttribute("producto", productoRepository.findById(id).get());
        return "producto/vermasProduct";
    }


    @GetMapping("/registrarse") // MUESTRA LA PAGINA DE REGISTRO DE USUARIO REGISTRADO
    public String registrarse(@ModelAttribute("usuario") Usuario usuario) {
        return "usuario/registrarUsuario";
    }

    @GetMapping("/recuperar") // MUESTRA LA PAGINA DE RECUPERAR CONTRA
    public String recuperar(Model model) {
        return "login/recoveryForm";
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


}
