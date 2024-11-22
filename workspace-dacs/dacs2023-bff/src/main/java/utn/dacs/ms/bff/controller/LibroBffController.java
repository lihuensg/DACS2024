package utn.dacs.ms.bff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import utn.dacs.ms.bff.dto.LibroBffDto;
import utn.dacs.ms.bff.service.LibroBffService;

@RestController
@RequestMapping("/libros")
public class LibroBffController {

    @Autowired
    private LibroBffService libroBffService;

  //-----------------Conector-------------------------// 
    
    
    @GetMapping("/isbn/{isbn}")
    public List<LibroBffDto> obtenerLibroPorIsbn(@PathVariable String isbn) {
        return libroBffService.buscarLibroPorIsbn(isbn);
    }

    @GetMapping("/titulo/{titulo}")
    public List<LibroBffDto> obtenerLibroPorTitulo(@PathVariable String titulo) {
        return libroBffService.buscarLibroPorTitulo(titulo);
    }

    @GetMapping("/autor/{autor}")
    public List<LibroBffDto> obtenerLibroPorAutor(@PathVariable String autor) {
        return libroBffService.buscarLibroPorAutor(autor);
    }
}    
    
