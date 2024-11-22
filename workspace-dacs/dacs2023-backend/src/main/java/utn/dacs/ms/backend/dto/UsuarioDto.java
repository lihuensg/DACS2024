package utn.dacs.ms.backend.dto;

import lombok.Data;

@Data
public class UsuarioDto {
    private String userId; // ID de Keycloak como String
    private Boolean disponible; // Puedes dejarlo aquí si es necesario
    private Boolean cuentabloqueada; // Puedes dejarlo aquí si es necesario
}