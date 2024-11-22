package utn.dacs.ms.bff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroConectorDto {
    private Integer id;
    
    @JsonProperty("title")
    private String titulo;
    
    @JsonProperty("image")
    private String portada;
    
    @JsonProperty("subtitle")
    private String resumen;
}