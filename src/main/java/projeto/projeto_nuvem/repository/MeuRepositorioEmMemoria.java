package projeto.projeto_nuvem.repository;

import org.springframework.stereotype.Repository;
import projeto.projeto_nuvem.Usuario;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MeuRepositorioEmMemoria {
    private final List<Usuario> usuarios = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Usuario> findAll() {
        return new ArrayList<>(usuarios);
    }

    public Optional<Usuario> findById(Long id) {
        return usuarios.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(idGenerator.getAndIncrement());
            usuarios.add(usuario);
        } else {
            // Atualiza se já existe
            deleteById(usuario.getId());
            usuarios.add(usuario);
        }
        return usuario;
    }

    public void deleteById(Long id) {
        usuarios.removeIf(u -> u.getId().equals(id));
    }
}