package utn.dacs.ms.conector.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroDto {
    private Long id;
    
    @JsonProperty("title")
    private String titulo;
    
    @JsonProperty("image")
    private String portada;
    
    @JsonProperty("subtitle")
    private String resumen;
}