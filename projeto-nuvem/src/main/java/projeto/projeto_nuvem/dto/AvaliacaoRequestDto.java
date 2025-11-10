package projeto.projeto_nuvem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvaliacaoRequestDto {
    private Long filmeId;
    private int nota;
    private String comentario;

}