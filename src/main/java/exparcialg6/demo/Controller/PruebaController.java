package exparcialg6.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prueba")
public class PruebaController {
    @GetMapping(value={"/",""})
    public String prueba(){

        return "login/recoveryForm";
    }
}
