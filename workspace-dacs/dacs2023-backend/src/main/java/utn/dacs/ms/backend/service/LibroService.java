package utn.dacs.ms.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import utn.dacs.ms.backend.dto.LibroDto;
import utn.dacs.ms.backend.model.entity.Feedback;
import utn.dacs.ms.backend.model.entity.Libro;

public interface LibroService {
    List<LibroDto> obtenerTodosLosLibros();
    Optional<LibroDto> obtenerLibroPorId(Long id);
//    LibroDto guardarLibro(LibroDto libroDto); // Método para guardar
    boolean eliminarLibroConTransacciones(Long id); // Método para eliminar
    LibroDto actualizarLibro(Long id, LibroDto libroDto); // Método para actualizar
    List<LibroDto> obtenerLibrosCompartibles(UUID usuarioId);
   
    void prestarLibro(Long libroId, UUID usuarioId);
    void devolverLibroConFeedback(Long libroId, UUID usuarioId, int nota, String comentario);
    List<Feedback> obtenerLibrosDevueltosConFeedback(UUID usuarioId);
 
    ResponseEntity<List<LibroDto>> obtenerLibrosDeUsuario(UUID usuarioId);
    ResponseEntity<String> agregarLibro(UUID usuarioId, LibroDto libroDto);
    
    List<Libro> obtenerLibrosPrestados(UUID usuarioId);
    List<Libro> obtenerLibrosDevueltos(UUID usuarioId);
    
    List<LibroDto> obtenerLibrosNoDevueltos(UUID usuarioId);
}

