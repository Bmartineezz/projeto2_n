package projeto.projeto_nuvem.controller;

import org.springframework.stereotype.Controller; // Use @Controller
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController; <- REMOVA esta importação

@Controller // AGORA É @Controller
public class HomeController {

    // Mapeia requisições GET para a raiz da aplicação (http://localhost:8080/)
    @GetMapping("/")
    public String home() {
        // Retorna o nome do arquivo de template (o Spring procurará por 'templates/home.html')
        return "home"; 
    }
}