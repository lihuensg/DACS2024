package utn.dacs.ms.bff.controller;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import utn.dacs.ms.backend.model.entity.Feedback;
//import utn.dacs.ms.bff.dto.AlumnoDto;
import utn.dacs.ms.bff.dto.BuildInfoDTO;
import utn.dacs.ms.bff.dto.LibroDto;
import utn.dacs.ms.bff.exceptions.BffException;
import utn.dacs.ms.bff.exceptions.ErrorEnum;
import utn.dacs.ms.bff.service.MsApiBackendService;

@RestController
@RequestMapping("/backend")
@Slf4j
public class MsBackendController {

    @Autowired
    private MsApiBackendService apiBackendService;

    @GetMapping("/ping")
    public String ping() {
        return apiBackendService.ping();
    }
    
    @GetMapping("/version")
    public BuildInfoDTO version() {
        return apiBackendService.version();
    }
    
   
//    @GetMapping("/alumno")
//    public List<AlumnoDto> getAlumnos() {
//    	return apiBackendService.getAlumnos();
//    }
//   
//    @GetMapping("/alumno/{id}")
//    public AlumnoDto getAlumnoById(@PathVariable Long id) {
//        return apiBackendService.getAlumnoById(id);
//    }
    
    @PostMapping("/libros/{libroId}/devolver")
    public ResponseEntity<String> devolverLibroConFeedback(
            @PathVariable Long libroId,
            @RequestParam UUID usuarioId,
            @RequestParam int nota,
            @RequestParam String comentario) {
        // Llama al servicio para procesar la devolución del libro
        return apiBackendService.devolverLibroConFeedback(libroId, usuarioId, nota, comentario);
    }
    
    @PostMapping("/libros/{libroId}/prestar/{usuarioId}")
    public ResponseEntity<String> prestarLibro(@PathVariable Long libroId, @PathVariable UUID usuarioId) {
        try {
            apiBackendService.prestarLibro(libroId, usuarioId);
            return ResponseEntity.ok("Libro prestado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al prestar el libro.");
        }
    }
    
    @GetMapping("/libros/devueltos")
    public ResponseEntity<List<Feedback>> obtenerLibrosDevueltosConFeedback(@RequestParam UUID usuarioId) {
        // Llama al servicio para obtener los libros devueltos con feedback
        return apiBackendService.obtenerLibrosDevueltosConFeedback(usuarioId);
    }
    
    @GetMapping("/libros/prestados")
    public List<LibroDto> obtenerLibrosPrestados(@RequestParam UUID usuarioId) {
        return apiBackendService.obtenerLibrosPrestados(usuarioId);
    }
    
    @GetMapping("/libros/{id}")
    public LibroDto obtenerLibroPorId(@PathVariable Long id) { 
           return apiBackendService.obtenerLibroPorId(id);
    }

    @GetMapping("/libros")
    public List<LibroDto> obtenerTodosLosLibros() {
        return apiBackendService.obtenerTodosLosLibros();
    }
    
    @GetMapping("/libros/compartibles/{usuarioId}")
    public List<LibroDto> obtenerLibrosCompartiblesExcluyendoUsuario(@PathVariable UUID usuarioId) {
        return apiBackendService.obtenerLibrosCompartibles(usuarioId);
    }

    @PostMapping("/libros/agregar/{usuarioId}")
    public ResponseEntity<String> agregarLibro(@PathVariable UUID usuarioId, @RequestBody LibroDto libroDto) {
        log.info("Agregando libro para el usuario con ID {}", usuarioId);

        ResponseEntity<String> response = apiBackendService.agregarLibro(usuarioId, libroDto);

        // Verificar los errores específicos
        if (response.getStatusCode() == HttpStatus.BAD_REQUEST && response.getBody() != null) {
            String errorMessage = response.getBody();
            
            // Error cuando el libro ya está en la colección del mismo usuario
            if (errorMessage.contains("Este libro ya está en tu colección")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                     .body("Este libro ya está en tu colección. No puedes agregarlo de nuevo.");
            }
            
            // Error cuando el libro ya pertenece a otro usuario
            if (errorMessage.contains("Este libro ya pertenece a otro usuario")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                     .body("Este libro ya pertenece a otro usuario. No se puede agregar.");
            }
        }

        return response; // Devolvemos la respuesta directamente si no es uno de los errores anteriores
    }


    @DeleteMapping("/libros/{id}")
    public ResponseEntity<?> eliminarLibroConTransacciones(@PathVariable Long id) {
        try {
            apiBackendService.eliminarLibroConTransacciones(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Error al eliminar el libro: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error inesperado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/libros/{id}")
    public LibroDto actualizarLibro(@PathVariable Long id, @RequestBody LibroDto libroDto) {
        return apiBackendService.actualizarLibro(id, libroDto);
    }
    
    @GetMapping("/libros/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerLibrosDeUsuario(@PathVariable("idUsuario") UUID idUsuario) {
        try {
            log.info("Recibiendo solicitud para obtener libros del usuario con ID: {}", idUsuario);
            List<LibroDto> libros = apiBackendService.obtenerLibrosDeUsuario(idUsuario);
            return ResponseEntity.ok(libros);
        } catch (RuntimeException e) {
            log.error("Error al obtener libros: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @GetMapping("/libros/nodevueltos/{usuarioId}")
    public ResponseEntity<List<LibroDto>> obtenerLibrosNoDevueltos(@PathVariable UUID usuarioId) {
        List<LibroDto> librosNoDevueltos = apiBackendService.obtenerLibrosNoDevueltos(usuarioId);
        return ResponseEntity.ok(librosNoDevueltos);
    }
    
    @DeleteMapping("/feedback/eliminar/{feedbackId}")
    public ResponseEntity<String> eliminarFeedback(@PathVariable Long feedbackId) {
        boolean eliminado = apiBackendService.eliminarFeedback(feedbackId);
        
        if (eliminado) {
            return ResponseEntity.ok("Feedback eliminado correctamente");
        } else {
            return ResponseEntity.status(404).body("No se encontró el Feedback para eliminar");
        }
    }
 }
