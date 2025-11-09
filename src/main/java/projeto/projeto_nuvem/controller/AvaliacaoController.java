package projeto.projeto_nuvem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.projeto_nuvem.service.AvaliacaoService;
import projeto.projeto_nuvem.dto.AvaliacaoRequestDto;
import projeto.projeto_nuvem.dto.AvaliacaoResponseDto;
import projeto.projeto_nuvem.Avaliacao;
import projeto.projeto_nuvem.Filme;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    public final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @GetMapping
    public List<AvaliacaoResponseDto> listarAvaliacoes() {
        return avaliacaoService.listarTodas()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDto> buscarPorId(@PathVariable Long id) {
        Optional<Avaliacao> avaliacaoOpt = avaliacaoService.buscarPorId(id);
        return avaliacaoOpt
                .map(avaliacao -> ResponseEntity.ok(toResponseDTO(avaliacao)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AvaliacaoResponseDto> criarAvaliacao(@Valid @RequestBody AvaliacaoRequestDto dto) {
        Avaliacao avaliacao = toEntity(dto);
        Avaliacao salvo = avaliacaoService.salvar(avaliacao);
        return ResponseEntity.ok(toResponseDTO(salvo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAvaliacao(@PathVariable Long id) {
        avaliacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private AvaliacaoResponseDto toResponseDTO(Avaliacao avaliacao) {
        AvaliacaoResponseDto dto = new AvaliacaoResponseDto();
        dto.setId(avaliacao.getId());
        dto.setFilmeId(avaliacao.getFilme().getId());
        dto.setNota(avaliacao.getNota());
        dto.setComentario(avaliacao.getComentario());
        return dto;
    }

    private Avaliacao toEntity(AvaliacaoRequestDto dto) {
        Avaliacao avaliacao = new Avaliacao();
        // Ideal: buscar o Filme pelo id via service/repository
        Filme filme = new Filme();
        filme.setId(dto.getFilmeId());
        avaliacao.setFilme(filme);
        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());
        return avaliacao;
    }
}