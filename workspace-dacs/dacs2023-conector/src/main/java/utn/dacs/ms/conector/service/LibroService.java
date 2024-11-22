package utn.dacs.ms.conector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import utn.dacs.ms.conector.api.client.LibroApiClient;
import utn.dacs.ms.conector.dto.LibroResponseDto;

@Service
public class LibroService {

    @Autowired
    private LibroApiClient libroApiClient;

    @Value("${API_KEY}")
    private String apiKey;

    public LibroResponseDto buscarLibroPorIsbn(String isbn) {
        return libroApiClient.buscarLibrosPorIsbn(isbn, apiKey);
    }

    public LibroResponseDto buscarLibroPorTitulo(String titulo) {
        return libroApiClient.buscarLibrosPorTitulo(titulo, apiKey);
    }

    public LibroResponseDto buscarLibroPorAutor(String autor) {
        return libroApiClient.buscarLibrosPorAutor(autor, apiKey);
    }
}

