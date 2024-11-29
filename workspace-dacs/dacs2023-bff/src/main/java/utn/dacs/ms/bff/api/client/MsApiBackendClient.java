package utn.dacs.ms.bff.api.client;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import utn.dacs.ms.bff.dto.UsuarioDto;
import utn.dacs.ms.backend.model.entity.Feedback;
//import utn.dacs.ms.bff.dto.AlumnoDto;
import utn.dacs.ms.bff.dto.BuildInfoDTO;
import utn.dacs.ms.bff.dto.LibroDto;



@FeignClient(
			name = "msApiBackendClient", 
			url = "${feign.client.config.msApiBackendClient.url}"
			)

public interface MsApiBackendClient {

    @GetMapping("/ping")
    String ping();
    
    @GetMapping("/version")
    BuildInfoDTO version();
    
//    @GetMapping("/alumno")
//    List<AlumnoDto> alumnos();
//    
//    @GetMapping("/alumno/{id}")
//    AlumnoDto alumno(@PathVariable("id") Long id);

    @PostMapping("/usuario/guardar")
    void guardarUsuario(@RequestBody UsuarioDto usuarioDto); 
    
    @PostMapping("/libros/{libroId}/devolver")
    ResponseEntity<String> devolverLibroConFeedback(
            @PathVariable("libroId") Long libroId,
            @RequestParam("usuarioId") UUID usuarioId,
            @RequestParam("nota") int nota,
            @RequestParam("comentario") String comentario);

    
    @PostMapping("/libros/{libroId}/prestar/{usuarioId}")
    void prestarLibro(@PathVariable("libroId") Long libroId, @PathVariable("usuarioId") UUID usuarioId);
    
    @GetMapping("/libros/prestados")
    List<LibroDto> obtenerLibrosPrestados(@RequestParam("usuarioId") UUID usuarioId);
    
    @GetMapping("/libros/devueltos")
    ResponseEntity<List<Feedback>> obtenerLibrosDevueltosConFeedback(@RequestParam("usuarioId") UUID usuarioId);
    
    @GetMapping("/libros")
    List<LibroDto> obtenerTodosLosLibros();

    @GetMapping("/libros/{id}")
    LibroDto obtenerLibroPorId(@PathVariable("id") Long id);
    
    @GetMapping("/libros/compartibles/{usuarioId}")
    List<LibroDto> obtenerLibrosCompartibles(@PathVariable("usuarioId") UUID usuarioId);

    @PostMapping("/libros/agregar/{usuarioId}")
    void agregarLibro(@PathVariable("usuarioId") UUID usuarioId, @RequestBody LibroDto libroDto);

    @DeleteMapping("/libros/{id}")
    void eliminarLibroConTransacciones(@PathVariable("id") Long id);

    @PutMapping("/libros/{id}")
    LibroDto actualizarLibro(@PathVariable("id") Long id, @RequestBody LibroDto libroDto);
    
    @GetMapping("/libros/usuario/{idUsuario}")
    List<LibroDto> obtenerLibrosDeUsuario(@PathVariable("idUsuario") UUID idUsuario);
    
    @GetMapping("libros/nodevueltos/{usuarioId}")
    List<LibroDto> obtenerLibrosNoDevueltos(@PathVariable UUID usuarioId);
    
    @DeleteMapping("/feedback/eliminar/{feedbackId}")
    void eliminarFeedback(@PathVariable("feedbackId") Long feedbackId);
}