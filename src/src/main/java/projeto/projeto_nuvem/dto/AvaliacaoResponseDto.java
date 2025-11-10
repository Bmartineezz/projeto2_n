package projeto.projeto_nuvem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvaliacaoResponseDto {
    private Long id;
    private Long filmeId;
    private int nota;
    private String comentario;


}