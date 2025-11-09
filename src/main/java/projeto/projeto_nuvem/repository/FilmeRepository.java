package projeto.projeto_nuvem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.projeto_nuvem.Filme;;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    // Exemplo de método customizado:
    // List<Filme> findByTituloContainingIgnoreCase(String titulo);
}