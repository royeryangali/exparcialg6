package exparcialg6.demo.Controller;

import exparcialg6.demo.App.Application;
import exparcialg6.demo.entity.Usuario;
import exparcialg6.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
                                RedirectAttributes attr) {

        if (bindingResult.hasErrors()) {
            return "admin/A-Form";
        } else {

            if (usuario.getIdusuario() == 0) {
                byte[] array = new byte[8];
                new Random().nextBytes(array);
                String generatedPassword = new String(array, Charset.forName("UTF-8"));
                Application.sendEmail(generatedPassword, usuario.getCorreo());
                attr.addFlashAttribute("msg", "Gestor creado exitosamente");
                usuario.setActivo(1);
                usuario.setPassword(generatedPassword);
                BCryptPasswordEncoder b = new BCryptPasswordEncoder();
                usuario.setPassword(b.encode(usuario.getPassword()));
                usuario.setRol("Gestor");
                usuarioRepository.registrarUsuario(usuario.getNombre(), usuario.getApellido(), usuario.getDni(), usuario.getCorreo(),
                        usuario.getRol(), usuario.getPassword(), usuario.getActivo());
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
            model.addAttribute("usuario", usuario);
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
