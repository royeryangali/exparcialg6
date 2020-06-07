package exparcialg6.demo.Controller;

import exparcialg6.demo.entity.Usuario;
import exparcialg6.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping(value = {"", "/listar"})
    public String listarGestores(Model model) {
        model.addAttribute("listaGestores", usuarioRepository.findByRol("Gestor"));
        return "admin/A-ListaGestores";
    }

    @GetMapping("/new")
    public String nuevoGestor(@ModelAttribute("usuario") Usuario usuario, Model model) {
        usuario.setIdusuario(0);
        return "admin/A-Form";
    }

    @PostMapping("/save")
    public String guardarGestor(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingResult,
                                  RedirectAttributes attr, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/A-Form";
        } else {

            if (usuario.getIdusuario() == 0) {
                attr.addFlashAttribute("msg", "Gestor creado exitosamente");
                usuario.setActivo(1);
                usuario.setPassword("12345678");
                usuario.setRol("Gestor");
                usuarioRepository.save(usuario);
                return "redirect:/admin";
            } else {
                usuarioRepository.save(usuario);
                attr.addFlashAttribute("msg", "Gestor actualizado exitosamente");
                return "redirect:/admin";
            }
        }
    }

    @GetMapping("/edit")
    public String editarGestor(@ModelAttribute("usuario") Usuario usuario,
                                 @RequestParam("id") int idusuario, Model model, RedirectAttributes attr) {

        Optional<Usuario> opt = usuarioRepository.findById(idusuario);
        if (opt.isPresent()) {
            usuario = opt.get();
            model.addAttribute("usuario",usuario);
            return "admin/A-Form";
        } else {
            return "redirect:/admin";
        }
    }

    @GetMapping("/delete")
    public String borrarGestor(Model model,
                                 @RequestParam("id") int id,
                                 RedirectAttributes attr) {

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            usuarioRepository.deleteById(id);
            attr.addFlashAttribute("msg", "Gestor borrado exitosamente");
        }
        return "redirect:/admin";

    }

    @PostMapping("/buscarGestor")
    public String buscar(@RequestParam("busqueda") String busqueda, Model model) {
        model.addAttribute("listaGestores", usuarioRepository.buscarPorNombre(busqueda));
        return "admin/A-ListaGestores";
    }


}
