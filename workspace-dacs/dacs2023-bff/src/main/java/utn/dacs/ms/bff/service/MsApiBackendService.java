package utn.dacs.ms.bff.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import utn.dacs.ms.backend.model.entity.Feedback;
import utn.dacs.ms.bff.api.client.MsApiBackendClient;
//import utn.dacs.ms.bff.dto.AlumnoDto;
import utn.dacs.ms.bff.dto.BuildInfoDTO;
import utn.dacs.ms.bff.dto.LibroDto;
import utn.dacs.ms.bff.exceptions.BffException;
import utn.dacs.ms.bff.exceptions.ErrorEnum;

@Service
@Slf4j
public class MsApiBackendService {

	@Autowired
    private MsApiBackendClient msApiBackendClient;
	
	
    public String ping() {
        try {
            return this.msApiBackendClient.ping();
        } catch (Exception e) {
           log.error("Error producido al solicitar un recurso a /backend/ping", e);
            throw new BffException(ErrorEnum.ERROR_API);
        }
    }
    
    public BuildInfoDTO version() {
        try {
            return this.msApiBackendClient.version();
        } catch (Exception e) {
            log.error("Error producido al solicitar un recurso a /backend/version", e);
            throw new BffException(ErrorEnum.ERROR_API);
        }
    }    
   
//    public List<AlumnoDto> getAlumnos() {
//        try {
//            return this.msApiBackendClient.alumnos();
//        } catch (Exception e) {
//            log.error("Error producido al solicitar un recurso a /backend/alumno", e);
//            throw new BffException(ErrorEnum.ERROR_API);
//        }
//    }
//    
//    public AlumnoDto getAlumnoById(Long id) {
//        try {
//            return this.msApiBackendClient.alumno(id);
//        } catch (Exception e) {
//            log.error("Error producido al solicitar un recurso a /backend/alumno/",+id, e);
//            throw new BffException(ErrorEnum.ERROR_API);
//        }
//    }
    
    public ResponseEntity<String> devolverLibroConFeedback(Long libroId, UUID usuarioId, int nota, String comentario) {
        // Llama al método del Feign Client para invocar el backend
        return msApiBackendClient.devolverLibroConFeedback(libroId, usuarioId, nota, comentario);
    }

    
    public void prestarLibro(Long libroId, UUID usuarioId) {
        try {
            msApiBackendClient.prestarLibro(libroId, usuarioId);
        } catch (Exception e) {
            log.error("Error al prestar libro con ID {} para el usuario con ID {}", libroId, usuarioId, e);
            throw new BffException(ErrorEnum.ERROR_API);
        }
    }
    
    public ResponseEntity<List<Feedback>> obtenerLibrosDevueltosConFeedback(UUID usuarioId) {
        // Llama al método del Feign Client para invocar el backend
        return msApiBackendClient.obtenerLibrosDevueltosConFeedback(usuarioId);
    }
    
    public List<LibroDto> obtenerLibrosPrestados(UUID usuarioId) {
        try {
            return msApiBackendClient.obtenerLibrosPrestados(usuarioId);
        } catch (Exception e) {
            log.error("Error al obtener libros prestados para el usuario con ID {}", usuarioId, e);
            throw new BffException(ErrorEnum.ERROR_API);
        }
    }
    
    public List<LibroDto> obtenerLibrosDeUsuario(UUID idUsuario) {
        try {
            log.info("Consultando libros del usuario con ID: {}", idUsuario);
            return msApiBackendClient.obtenerLibrosDeUsuario(idUsuario);
        } catch (FeignException.NotFound e) {
            log.error("No se encontraron libros para el usuario con ID: {}", idUsuario);
            throw new RuntimeException("Usuario no tiene libros registrados o no existe");
        } catch (Exception e) {
            log.error("Error inesperado al consultar libros para el usuario con ID: {}", idUsuario, e);
            throw new RuntimeException("Hubo un error al consultar la API");
        }
    }
    
    
    public LibroDto obtenerLibroPorId(Long id) {
    	try {
    	return this.msApiBackendClient.obtenerLibroPorId(id);
    	 } catch (Exception e) {
    		 log.error("Error producido al solicitar un recurso a /backend/libro/",+id, e);
             throw new BffException(ErrorEnum.ERROR_API);
         }
    }

    public List<LibroDto> obtenerTodosLosLibros() {
        return msApiBackendClient.obtenerTodosLosLibros();
    }

    
    public List<LibroDto> obtenerLibrosCompartibles(UUID usuarioId) {
        try {
            return msApiBackendClient.obtenerLibrosCompartibles(usuarioId);
        } catch (Exception e) {
            log.error("Error al obtener los libros compartibles excluyendo los del usuario con ID: {}", usuarioId, e);
            throw new BffException(ErrorEnum.ERROR_API);
        }
    }
    
    
    public ResponseEntity<String> agregarLibro(UUID usuarioId, LibroDto libroDto) {
        try {
            // Realizamos la llamada al microservicio backend utilizando Feign
            msApiBackendClient.agregarLibro(usuarioId, libroDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body("Libro agregado correctamente para el usuario " + usuarioId);
        } catch (FeignException.BadRequest e) {
            log.error("Error al agregar libro para el usuario con ID {}: {}", usuarioId, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error al agregar el libro: " + e.getMessage());
        } catch (FeignException.NotFound e) {
            log.error("Usuario no encontrado para el ID {}: {}", usuarioId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Usuario no encontrado.");
        } catch (Exception e) {
            log.error("Error inesperado al agregar libro para el usuario con ID {}: {}", usuarioId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error interno del servidor.");
        }
    }
    
    public void eliminarLibroConTransacciones(Long id) {
        try {
            msApiBackendClient.eliminarLibroConTransacciones(id);
            log.info("Libro con ID {} eliminado correctamente junto con sus transacciones.", id);
        } catch (FeignException.NotFound e) {
            log.error("Libro con ID {} no encontrado: {}", id, e.getMessage());
            throw new RuntimeException("El libro no fue encontrado.");
        } catch (FeignException e) {
            log.error("Error al comunicarse con el backend para eliminar el libro con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al comunicarse con el backend.");
        }
    }
    
    public LibroDto actualizarLibro(Long id, LibroDto libroDto) {
        return msApiBackendClient.actualizarLibro(id, libroDto);
    }
   
    public List<LibroDto> obtenerLibrosNoDevueltos(UUID usuarioId) {
        return msApiBackendClient.obtenerLibrosNoDevueltos(usuarioId);
    }
    
    public boolean eliminarFeedback(Long feedbackId) {
        try {
            // Llamamos al cliente para eliminar el feedback
            msApiBackendClient.eliminarFeedback(feedbackId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
