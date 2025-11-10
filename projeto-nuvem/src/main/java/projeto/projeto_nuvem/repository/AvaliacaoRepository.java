package projeto.projeto_nuvem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.projeto_nuvem.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    // Exemplo de método customizado:
    // List<Avaliacao> findByFilmeId(Long filmeId);
}