package projeto.projeto_nuvem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.projeto_nuvem.Avaliacao;
import projeto.projeto_nuvem.repository.AvaliacaoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public List<Avaliacao> listarTodas() {
        return avaliacaoRepository.findAll();
    }

    public Optional<Avaliacao> buscarPorId(Long id) {
        return avaliacaoRepository.findById(id);
    }

    public Avaliacao salvar(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    public void deletar(Long id) {
        avaliacaoRepository.deleteById(id);
    }
}