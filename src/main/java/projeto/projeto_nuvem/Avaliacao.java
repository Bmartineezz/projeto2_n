package projeto.projeto_nuvem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "filme_id")
    private Filme filme;

    @Column(nullable = false)
    private int nota; // 1 a 5

    private String comentario;

    // Getters e Setters
}