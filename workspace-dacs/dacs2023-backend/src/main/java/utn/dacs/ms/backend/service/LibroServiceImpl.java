package utn.dacs.ms.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utn.dacs.ms.backend.dto.LibroDto;
import utn.dacs.ms.backend.model.entity.Feedback;
import utn.dacs.ms.backend.model.entity.HistorialTransacciones;
import utn.dacs.ms.backend.model.entity.Libro;
import utn.dacs.ms.backend.model.entity.Usuario;
import utn.dacs.ms.backend.model.repository.LibroRepository;
import utn.dacs.ms.backend.model.repository.FeedbackRepository;
import utn.dacs.ms.backend.model.repository.HistorialTransaccionesRepository;
import utn.dacs.ms.backend.model.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HistorialTransaccionesRepository historialTransaccionesRepository;
    
    @Autowired
    private final FeedbackRepository feedbackRepository;
    
    @Override
    public ResponseEntity<List<LibroDto>> obtenerLibrosDeUsuario(UUID usuarioId) {
        // Busca al usuario por ID en la base de datos
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        // Busca los libros asociados al usuario
        List<Libro> libros = libroRepository.findByUsuario(usuario);

        // Mapea los libros a LibroDto
        List<LibroDto> librosDto = libros.stream()
        		 .map(libro -> new LibroDto(libro.getId(), libro.getTitulo(), libro.getIsbn(), libro.getPortada(), libro.getResumen(), libro.getIdapi().intValue(), libro.getArchivado(), libro.getSepuedecompartir(), libro.getNombreautor()))
                 .collect(Collectors.toList());

        return ResponseEntity.ok(librosDto);
    }


    @Override
    public ResponseEntity<String> agregarLibro(UUID usuarioId, LibroDto libroDto) {
        // Verificar si el libro ya existe en la base de datos
        Optional<Libro> libroExistente = libroRepository.findByIdapi(libroDto.getIdApi());
        
        if (libroExistente.isPresent()) {
            Libro libro = libroExistente.get();
            if (libro.getUsuario().getId().equals(usuarioId)) {
                // El libro ya pertenece al usuario actual
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Este libro ya está en tu colección.");
            } else {
                // El libro pertenece a otro usuario
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Este libro ya pertenece a otro usuario.");
            }
        }

        // Si el libro no existe, continuar con el proceso de guardado
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        Libro libro = new Libro();
        libro.setTitulo(libroDto.getTitulo());
        libro.setNombreautor(libroDto.getNombreAutor());
        libro.setIsbn(libroDto.getIsbn());
        libro.setPortada(libroDto.getPortada());
        libro.setArchivado(libroDto.getArchivado());
        libro.setSepuedecompartir(libroDto.getSePuedeCompartir());
        libro.setResumen(libroDto.getResumen());
        libro.setIdapi(libroDto.getIdApi());
        libro.setUsuario(usuario);

        libroRepository.save(libro);
        return ResponseEntity.ok("Libro agregado correctamente para el usuario " + usuarioId);
    }
    
    
    // Obtener todos los libros
    @Override
    public List<LibroDto> obtenerTodosLosLibros() {
        return libroRepository.findAll().stream()
            .map(libro -> new LibroDto(libro.getId(), libro.getTitulo(), libro.getIsbn(), libro.getPortada(), libro.getResumen(), libro.getIdapi().intValue(), libro.getArchivado(), libro.getSepuedecompartir(), libro.getNombreautor()))
            .collect(Collectors.toList());
    }

    // Obtener un libro por ID
    @Override
    public Optional<LibroDto> obtenerLibroPorId(Long id) {
        return libroRepository.findById(id)
            .map(libro -> new LibroDto(libro.getId(), libro.getTitulo(), libro.getIsbn(), libro.getPortada(), libro.getResumen(), libro.getIdapi().intValue(), libro.getArchivado(), libro.getSepuedecompartir(), libro.getNombreautor()));
    }
    
    @Override
    public List<LibroDto> obtenerLibrosCompartibles(UUID usuarioId) {
        List<Libro> librosCompartibles = libroRepository.findCompartiblesExcluyendoUsuario(usuarioId);
        return librosCompartibles.stream()
                .map(libro -> new LibroDto(
                        libro.getId(),
                        libro.getTitulo(),
                        libro.getIsbn(),
                        libro.getPortada(),
                        libro.getResumen(),
                        libro.getIdapi(),
                        libro.getArchivado(),
                        libro.getSepuedecompartir(),
                        libro.getNombreautor()
                ))
                .collect(Collectors.toList());
    }

   
    // Eliminar un libro
    @Override
    public boolean eliminarLibroConTransacciones(Long id) {
        Optional<Libro> libroOptional = libroRepository.findById(id);

        if (libroOptional.isPresent()) {
            Libro libro = libroOptional.get();

            // Eliminamos las transacciones asociadas al libro (si existen)
            historialTransaccionesRepository.deleteAll(libro.getHistorialTransacciones());

            // Ahora eliminamos el libro
            libroRepository.delete(libro);
            return true;
        } else {
            throw new EntityNotFoundException("El libro no existe.");
        }
    }


    // Actualizar un libro
    @Override
    public LibroDto actualizarLibro(Long id, LibroDto libroDto) {
        Optional<Libro> libroOptional = libroRepository.findById(id);
        if (libroOptional.isPresent()) {
            Libro libro = libroOptional.get();
            libro.setTitulo(libroDto.getTitulo());
            libro.setIsbn(libroDto.getIsbn());
            libro.setPortada(libroDto.getPortada());
            libro.setResumen(libroDto.getResumen());
            libro.setIdapi(libroDto.getIdApi());
            libro.setArchivado(libroDto.getArchivado());
            libro.setSepuedecompartir(libroDto.getSePuedeCompartir());
            libro.setNombreautor(libroDto.getNombreAutor());

            libro = libroRepository.save(libro); // Guardar el libro actualizado

            return new LibroDto(libro.getId(), libro.getTitulo(), libro.getIsbn(), libro.getPortada(), libro.getResumen(), libro.getIdapi().intValue(), libro.getArchivado(), libro.getSepuedecompartir(), libro.getNombreautor());
        }
        return null; // Puedes lanzar una excepción si no se encuentra el libro
    }
    
    public void prestarLibro(Long libroId, UUID usuarioId) {
        // Obtener el libro
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // Verificar si ya hay un préstamo activo para este libro y usuario
        Optional<HistorialTransacciones> transaccionActiva = historialTransaccionesRepository
                .findTopByLibroIdAndUsuarioIdAndDevueltoFalse(libroId, usuarioId);

        if (transaccionActiva.isPresent()) {
            throw new RuntimeException("El libro ya está prestado a este usuario.");
        }

        // Cambiar el estado del libro
        libro.setSepuedecompartir(false); // El libro ya no está disponible para compartir
        libroRepository.save(libro);

        // Registrar la transacción de préstamo
        HistorialTransacciones transaccion = new HistorialTransacciones();
        transaccion.setLibro(libro);
        transaccion.setUsuario(new Usuario(usuarioId)); // Crear un nuevo objeto Usuario con el ID
        transaccion.setDevuelto(false); // Indicar que aún no se ha devuelto
        historialTransaccionesRepository.save(transaccion);
    }

    @Override
    public void devolverLibroConFeedback(Long libroId, UUID usuarioId, int nota, String comentario) {
        // Validar que la nota esté en el rango de 1 a 5
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("La nota debe estar entre 1 y 5.");
        }

        // Obtener la transacción activa
        HistorialTransacciones transaccion = historialTransaccionesRepository
                .findTopByLibroIdAndUsuarioIdAndDevueltoFalse(libroId, usuarioId)
                .orElseThrow(() -> new RuntimeException("No hay transacción activa para este libro y usuario."));

        // Cambiar el estado de la transacción a devuelto
        transaccion.setDevuelto(true);
        historialTransaccionesRepository.save(transaccion);

        // Cambiar el estado del libro a disponible
        Libro libro = transaccion.getLibro();
        libro.setSepuedecompartir(true); // El libro puede ser prestado nuevamente
        libroRepository.save(libro);

        // Guardar el feedback
        Feedback feedback = new Feedback();
        feedback.setLibro(libro);
        feedback.setNota(nota);
        feedback.setComentario(comentario);
        feedbackRepository.save(feedback);
    }

    
    @Override
    public List<Feedback> obtenerLibrosDevueltosConFeedback(UUID usuarioId) {
        return feedbackRepository.findAllByUsuarioId(usuarioId);
    }
   
    @Override
    public List<Libro> obtenerLibrosPrestados(UUID usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return libroRepository.findAll().stream()
            .filter(libro -> !libro.getSepuedecompartir() &&
                             historialTransaccionesRepository.existsByLibroAndUsuarioAndDevueltoFalse(libro, usuario))
            .collect(Collectors.toList());
    }

    @Override
    public List<Libro> obtenerLibrosDevueltos(UUID usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return libroRepository.findAll().stream()
            .filter(libro -> libro.getSepuedecompartir() &&
                             historialTransaccionesRepository.existsByLibroAndUsuarioAndDevueltoTrue(libro, usuario))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<LibroDto> obtenerLibrosNoDevueltos(UUID usuarioId) {
        List<Libro> librosNoDevueltos = historialTransaccionesRepository.findLibrosNoDevueltosByUsuarioId(usuarioId);

        return librosNoDevueltos.stream()
                .map(libro -> new LibroDto(
                        libro.getId(),
                        libro.getTitulo(),
                        libro.getIsbn(),
                        libro.getPortada(),
                        libro.getResumen(),
                        libro.getIdapi(), 
                        libro.getArchivado(),
                        libro.getSepuedecompartir(),
                        libro.getNombreautor()
                ))
                .collect(Collectors.toList());
    }
}

