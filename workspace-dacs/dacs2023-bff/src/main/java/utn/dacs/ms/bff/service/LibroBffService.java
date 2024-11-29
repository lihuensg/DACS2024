package utn.dacs.ms.bff.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utn.dacs.ms.bff.api.client.MsApiConectorClient;
import utn.dacs.ms.bff.dto.LibroBffDto;
import utn.dacs.ms.bff.dto.LibroConectorDto;

@Service
public class LibroBffService {

    @Autowired
    private MsApiConectorClient msApiConectorClient;
 

  //-------------------------CONECTOR---------------------------------------------//
    
    public List<LibroBffDto> buscarLibroPorIsbn(String isbn) {
        List<LibroConectorDto> libros = msApiConectorClient.buscarLibroPorIsbn(isbn);
        return transformarLibrosABff(libros);
    }

    public List<LibroBffDto> buscarLibroPorTitulo(String titulo) {
        List<LibroConectorDto> libros = msApiConectorClient.buscarLibroPorTitulo(titulo);
        return transformarLibrosABff(libros);
    }

    public List<LibroBffDto> buscarLibroPorAutor(String autor) {
        List<LibroConectorDto> libros = msApiConectorClient.buscarLibroPorAutor(autor);
        return transformarLibrosABff(libros);
    }

    // Método para transformar de LibroDto a LibroBffDto
    private List<LibroBffDto> transformarLibrosABff(List<LibroConectorDto> libros) {
        return libros.stream()
        		.map(libro -> new LibroBffDto(libro.getId(), libro.getTitulo(), libro.getPortada(), libro.getResumen()))
                     .collect(Collectors.toList());
    }
}

