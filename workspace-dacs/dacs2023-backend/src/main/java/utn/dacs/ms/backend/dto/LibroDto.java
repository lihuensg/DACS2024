package utn.dacs.ms.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LibroDto {
	private Long id; 
    private String titulo;          
    private String isbn;           
    private String portada;         
    private String resumen;       
    private Integer idApi;         
    private Boolean archivado;     
    private Boolean sePuedeCompartir; 
    private String nombreAutor;    
}