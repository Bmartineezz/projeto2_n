package projeto.projeto_nuvem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.projeto_nuvem.dto.UsuarioRequestDto;
import projeto.projeto_nuvem.dto.UsuarioResponseDto;
import projeto.projeto_nuvem.Usuario;
import projeto.projeto_nuvem.service.UsuarioService;
<<<<<<< HEAD
=======

>>>>>>> cff9082d6621641ece4e7e740721cfb35994c672
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")


public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioResponseDto> listarUsuarios() {
        return usuarioService.listarTodos()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);
        return usuarioOpt
                .map(usuario -> ResponseEntity.ok(toResponseDTO(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> criarUsuario(@Valid @RequestBody UsuarioRequestDto dto) {
        Usuario usuario = toEntity(dto);
        Usuario salvo = usuarioService.salvar(usuario);
        return ResponseEntity.ok(toResponseDTO(salvo));
    }

    // UPDATE: Atualizar um usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDto dto) {
        
        // 1. Buscar o usuário existente pelo ID
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);

        if (usuarioOpt.isEmpty()) {
            // Se o usuário não for encontrado, retorna 404 Not Found
            return ResponseEntity.notFound().build();
        }
        
        // 2. Atualizar os dados do usuário existente
        Usuario usuarioExistente = usuarioOpt.get();
        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setEmail(dto.getEmail());
        // O ID original é mantido

        // 3. Salvar (atualizar) o usuário no serviço
        Usuario salvo = usuarioService.salvar(usuarioExistente); 
        
        // 4. Retornar a resposta de sucesso (200 OK)
        return ResponseEntity.ok(toResponseDTO(salvo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public void atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDto dto) {
        Usuario usuario = toEntity(dto); // Converte o DTO para entidade
        usuarioService.atualizar(id, usuario);
    }

    // Conversão manual entre DTO e entidade
    private UsuarioResponseDto toResponseDTO(Usuario usuario) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        return dto;
    }

    private Usuario toEntity(UsuarioRequestDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        return usuario;
    }
}