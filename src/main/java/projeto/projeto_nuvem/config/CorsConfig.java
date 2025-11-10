package projeto.projeto_nuvem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração global para Cross-Origin Resource Sharing (CORS).
 * Permite que aplicações frontend (como seu app.js) acessem a API.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica as regras a TODOS os endpoints da API (ex: /usuarios, /outros)
                .allowedOrigins("*") // Permite acesso de qualquer origem (seguro para desenvolvimento)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite todos os métodos HTTP usados no CRUD
                .allowedHeaders("*"); // Permite todos os cabeçalhos
    }
}