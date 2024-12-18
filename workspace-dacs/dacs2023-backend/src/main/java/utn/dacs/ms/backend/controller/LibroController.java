package utn.dacs.ms.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.dacs.ms.backend.dto.LibroDto;
import utn.dacs.ms.backend.dto.TransaccionLibroRequestDto;
import utn.dacs.ms.backend.model.entity.Feedback;
import utn.dacs.ms.backend.model.entity.Libro;
import utn.dacs.ms.backend.service.LibroService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/libros")
public class LibroController {

	private static final Logger log = LoggerFactory.getLogger(LibroController.class);
   
	@Autowired
    private LibroService libroService;
	
	
	// 1. Obtener todos los libros de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<LibroDto>> obtenerLibrosDeUsuario(@PathVariable UUID usuarioId) {
        return libroService.obtenerLibrosDeUsuario(usuarioId);
    }

    // 2. Agregar un libro para un usuario
    @PostMapping("/agregar/{usuarioId}")
    public ResponseEntity<String> agregarLibro(@PathVariable UUID usuarioId, @RequestBody LibroDto libroDto) {
        return libroService.agregarLibro(usuarioId, libroDto);
    }
	
 // Eliminar un libro
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLibroConTransacciones(@PathVariable Long id) {
        try {
            boolean eliminado = libroService.eliminarLibroConTransacciones(id);
            if (eliminado) {
                return ResponseEntity.ok("Libro y sus transacciones eliminados con éxito.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el libro.");
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el libro.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el libro: " + e.getMessage());
        }
    }

    // Actualizar un libro
    @PutMapping("/{id}")
    public ResponseEntity<LibroDto> actualizarLibro(@PathVariable Long id, @RequestBody LibroDto libroDto) {
        LibroDto libroActualizado = libroService.actualizarLibro(id, libroDto);
        if (libroActualizado != null) {
            return ResponseEntity.ok(libroActualizado);
        } else {
            return ResponseEntity.notFound().build(); // Devuelve 404 si no encuentra el libro
        }
    }
    
    // Obtener todos los libros
    @GetMapping("")
    public ResponseEntity<List<LibroDto>> obtenerTodosLosLibros() {
        List<LibroDto> libros = libroService.obtenerTodosLosLibros();
        return ResponseEntity.ok(libros);
    }

    // Obtener un libro por ID
    @GetMapping("/{id}")
    public ResponseEntity<LibroDto> obtenerLibroPorId(@PathVariable Long id) {
        Optional<LibroDto> libro = libroService.obtenerLibroPorId(id);
        if (libro.isPresent()) {
            return ResponseEntity.ok(libro.get());
        } else {
            return ResponseEntity.notFound().build(); // Devuelve 404 si no encuentra el libro
        }
    }
    
   
    @GetMapping("/compartibles/{usuarioId}")
    public List<LibroDto> obtenerLibrosCompartibles(@PathVariable UUID usuarioId) {
        return libroService.obtenerLibrosCompartibles(usuarioId);
    }
    
   
    @PostMapping("/{libroId}/prestar/{usuarioId}")
    public ResponseEntity<String> prestarLibro(@PathVariable Long libroId, @PathVariable UUID usuarioId) {
        libroService.prestarLibro(libroId, usuarioId);
        return ResponseEntity.ok("Libro prestado con éxito.");
    }

    @PostMapping("/{libroId}/devolver")
    public ResponseEntity<String> devolverLibroConFeedback(
            @PathVariable Long libroId,
            @RequestParam UUID usuarioId,
            @RequestParam int nota,
            @RequestParam String comentario) {
        libroService.devolverLibroConFeedback(libroId, usuarioId, nota, comentario);
        return ResponseEntity.ok("Libro devuelto correctamente con feedback agregado.");
    }

    
    @GetMapping("/prestados")
    public List<Libro> obtenerLibrosPrestados(@RequestParam UUID usuarioId) {
        return libroService.obtenerLibrosPrestados(usuarioId);
    }

 // Endpoint para obtener libros devueltos con feedback
    @GetMapping("/devueltos")
    public ResponseEntity<List<Feedback>> obtenerLibrosDevueltosConFeedback(@RequestParam UUID usuarioId) {
        List<Feedback> feedbacks = libroService.obtenerLibrosDevueltosConFeedback(usuarioId);
        return ResponseEntity.ok(feedbacks);
    }

    
    @GetMapping("/nodevueltos/{usuarioId}")
    public ResponseEntity<List<LibroDto>> obtenerLibrosNoDevueltos(@PathVariable UUID usuarioId) {
        List<LibroDto> librosNoDevueltos = libroService.obtenerLibrosNoDevueltos(usuarioId);
        return ResponseEntity.ok(librosNoDevueltos);
    }
}


