package utn.dacs.ms.conector.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import utn.dacs.ms.conector.dto.LibroResponseDto;

@FeignClient(name = "apiClientLibro", url = "${feign.client.config.apiClientLibro.url}")
public interface LibroApiClient {

	@GetMapping("/search-books")
    LibroResponseDto buscarLibrosPorIsbn(@RequestParam("isbn") String isbn, 
                                          @RequestParam("api-key") String apiKey);

    @GetMapping("/search-books")
    LibroResponseDto buscarLibrosPorTitulo(@RequestParam("query") String titulo, 
                                            @RequestParam("api-key") String apiKey);
    
    @GetMapping("/search-books")
    LibroResponseDto buscarLibrosPorAutor(@RequestParam("query") String autor, 
                                           @RequestParam("api-key") String apiKey);
}