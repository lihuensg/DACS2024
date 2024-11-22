package utn.dacs.ms.backend.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class TransaccionLibroRequestDto {
    private UUID usuarioId;
    private Long libroId;
}