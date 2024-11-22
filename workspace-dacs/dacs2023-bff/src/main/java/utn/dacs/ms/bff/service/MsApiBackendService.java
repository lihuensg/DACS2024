package utn.dacs.ms.bff.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
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
    
    public void devolverLibro(Long libroId, UUID usuarioId) {
        try {
            msApiBackendClient.devolverLibro(libroId, usuarioId);
        } catch (Exception e) {
            log.error("Error al devolver libro con ID {} para el usuario con ID {}", libroId, usuarioId, e);
            throw new BffException(ErrorEnum.ERROR_API);
        }
    }

    
    public void prestarLibro(Long libroId, UUID usuarioId) {
        try {
            msApiBackendClient.prestarLibro(libroId, usuarioId);
        } catch (Exception e) {
            log.error("Error al prestar libro con ID {} para el usuario con ID {}", libroId, usuarioId, e);
            throw new BffException(ErrorEnum.ERROR_API);
        }
    }
    
    public List<LibroDto> obtenerLibrosDevueltos(UUID usuarioId) {
        try {
            return msApiBackendClient.obtenerLibrosDevueltos(usuarioId);
        } catch (Exception e) {
            log.error("Error al obtener libros devueltos para el usuario con ID {}", usuarioId, e);
            throw new BffException(ErrorEnum.ERROR_API);
        }
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

    
    public List<LibroDto> obtenerLibrosCompartibles() {
        try {
            return msApiBackendClient.obtenerLibrosCompartibles();
        } catch (Exception e) {
            log.error("Error producido al solicitar los libros compartibles", e);
            throw new BffException(ErrorEnum.ERROR_API);
        }
    }
    
    
    public void agregarLibro(UUID usuarioId, LibroDto libroDto) {
        try {
            msApiBackendClient.agregarLibro(usuarioId, libroDto);
        } catch (Exception e) {
            log.error("Error al agregar libro para el usuario con ID {}", usuarioId, e);
            throw new BffException(ErrorEnum.ERROR_API);
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
   
}
