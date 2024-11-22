package utn.dacs.ms.conector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import utn.dacs.ms.conector.dto.LibroResponseDto;
import utn.dacs.ms.conector.dto.LibroDto;
import utn.dacs.ms.conector.service.LibroService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping("/isbn/{isbn}")
    public List<LibroDto> obtenerLibroPorIsbn(@PathVariable String isbn) {
        LibroResponseDto apiResponse = libroService.buscarLibroPorIsbn(isbn);
        return transformarLibros(apiResponse);
    }

    @GetMapping("/titulo/{titulo}")
    public List<LibroDto> obtenerLibroPorTitulo(@PathVariable String titulo) {
        LibroResponseDto apiResponse = libroService.buscarLibroPorTitulo(titulo);
        return transformarLibros(apiResponse);
    }

    @GetMapping("/autor/{autor}")
    public List<LibroDto> obtenerLibroPorAutor(@PathVariable String autor) {
        LibroResponseDto apiResponse = libroService.buscarLibroPorAutor(autor);
        return transformarLibros(apiResponse);
    }

 // Este método transforma los libros al formato amigable
    private List<LibroDto> transformarLibros(LibroResponseDto apiResponse) {
        List<LibroDto> librosFormateados = new ArrayList<>();
        
        for (List<LibroDto> libroList : apiResponse.getBooks()) {
            for (LibroDto libro : libroList) {
                // Utiliza el constructor sin parámetros o los setters para asignar los valores
                LibroDto libroDto = new LibroDto();
                libroDto.setId(libro.getId());
                libroDto.setTitulo(libro.getTitulo());
                libroDto.setResumen(libro.getResumen());
                libroDto.setPortada(libro.getPortada());
                librosFormateados.add(libroDto);
            }
        }
        
        return librosFormateados;
    }
    
}