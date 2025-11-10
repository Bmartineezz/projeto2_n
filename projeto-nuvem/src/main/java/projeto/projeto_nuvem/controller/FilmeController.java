package projeto.projeto_nuvem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.projeto_nuvem.dto.FilmeRequestDto;
import projeto.projeto_nuvem.dto.FilmeResponseDto;
import projeto.projeto_nuvem.service.FilmeService;
import projeto.projeto_nuvem.Filme;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    public FilmeService filmeService;

    @GetMapping
    public List<FilmeResponseDto> listarFilmes() {
        return filmeService.listarTodos()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmeResponseDto> buscarPorId(@PathVariable Long id) {
        return filmeService.buscarPorId(id)
                .map(filme -> ResponseEntity.ok(toResponseDTO(filme)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FilmeResponseDto> criarFilme(@RequestBody FilmeRequestDto dto) {
        Filme filme = toEntity(dto);
        Filme salvo = filmeService.salvar(filme);
        return ResponseEntity.ok(toResponseDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmeResponseDto> atualizarFilme(@PathVariable Long id, @RequestBody FilmeRequestDto dto) {
        return filmeService.buscarPorId(id)
                .map(filme -> {
                    filme.setTitulo(dto.getTitulo());
                    filme.setDescricao(dto.getDescricao());
                    Filme atualizado = filmeService.salvar(filme);
                    return ResponseEntity.ok(toResponseDTO(atualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFilme(@PathVariable Long id) {
        if (filmeService.buscarPorId(id).isPresent()) {
            filmeService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Conversão manual entre DTO e entidade
    private FilmeResponseDto toResponseDTO(Filme filme) {
        FilmeResponseDto dto = new FilmeResponseDto();
        dto.setId(filme.getId());
        dto.setTitulo(filme.getTitulo());
        dto.setDescricao(filme.getDescricao());
        return dto;
    }

    private Filme toEntity(FilmeRequestDto dto) {
        Filme filme = new Filme();
        filme.setTitulo(dto.getTitulo());
        filme.setDescricao(dto.getDescricao());
        return filme;
    }
}