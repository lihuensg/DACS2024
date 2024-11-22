package utn.dacs.ms.bff.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import utn.dacs.ms.bff.dto.BuildInfoDTO;
//import utn.dacs.ms.bff.dto.ItemDto;
import utn.dacs.ms.bff.dto.LibroConectorDto;



@FeignClient(
			name = "msApiConectorClient", 
			url = "${feign.client.config.msApiConectorClient.url}"
			)

public interface MsApiConectorClient {

    @GetMapping("/ping")
    String ping();
    
    @GetMapping("/version")
    BuildInfoDTO version();
    
//    @GetMapping("/todos")
//    List<ItemDto> todos();
    
 // Métodos para buscar libros
    
    @GetMapping("/libros/isbn/{isbn}")
    List<LibroConectorDto> buscarLibroPorIsbn(@PathVariable("isbn") String isbn);

    @GetMapping("/libros/titulo/{titulo}")
    List<LibroConectorDto> buscarLibroPorTitulo(@PathVariable("titulo") String titulo);

    @GetMapping("/libros/autor/{autor}")
    List<LibroConectorDto> buscarLibroPorAutor(@PathVariable("autor") String autor); 
}
