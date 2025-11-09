package projeto.projeto_nuvem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.projeto_nuvem.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Exemplo de método customizado:
    // Optional<Usuario> findByEmail(String email);
}