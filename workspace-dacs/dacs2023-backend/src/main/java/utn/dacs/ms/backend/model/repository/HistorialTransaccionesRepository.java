package utn.dacs.ms.backend.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import utn.dacs.ms.backend.model.entity.HistorialTransacciones;
import org.springframework.data.repository.query.Param;
import utn.dacs.ms.backend.model.entity.Libro;
import utn.dacs.ms.backend.model.entity.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HistorialTransaccionesRepository extends JpaRepository<HistorialTransacciones, Long> {

    @Query("SELECT h FROM HistorialTransacciones h WHERE h.libro.id = :libroId AND h.usuario.id = :usuarioId AND h.devuelto = false ORDER BY h.id DESC")
    Optional<HistorialTransacciones> findTopByLibroIdAndUsuarioIdAndDevueltoFalse(@Param("libroId") Long libroId, @Param("usuarioId") UUID usuarioId);
   
    boolean existsByLibroAndUsuarioAndDevueltoFalse(Libro libro, Usuario usuario);
    boolean existsByLibroAndUsuarioAndDevueltoTrue(Libro libro, Usuario usuario);
    boolean existsByLibroAndDevueltoFalse(Libro libro);
    void deleteByLibroId(Long libroId);
    
    @Query("SELECT ht.libro FROM HistorialTransacciones ht WHERE ht.usuario.id = :usuarioId AND ht.devuelto = false")
    List<Libro> findLibrosNoDevueltosByUsuarioId(@Param("usuarioId") UUID usuarioId);

}

