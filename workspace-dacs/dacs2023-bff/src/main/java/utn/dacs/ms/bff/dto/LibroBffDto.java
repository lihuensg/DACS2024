package utn.dacs.ms.bff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroBffDto {
    private Integer idApi;   // ID de la API
    private String titulo;   // Título del libro
    private String portada;  // URL de la imagen
    private String resumen;  // Resumen del libro
}