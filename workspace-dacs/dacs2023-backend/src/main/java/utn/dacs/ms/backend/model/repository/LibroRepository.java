package utn.dacs.ms.backend.model.repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import feign.Param;
import utn.dacs.ms.backend.model.entity.Libro;
import utn.dacs.ms.backend.model.entity.Usuario;


public interface LibroRepository extends JpaRepository<Libro, Long> {
	List<Libro> findBySepuedecompartirTrue();
	List<Libro> findByUsuario(Usuario usuario);
	
	@Query("SELECT l FROM Libro l WHERE l.sepuedecompartir = true AND l.usuario.id <> :usuarioId")
    List<Libro> findCompartiblesExcluyendoUsuario(@Param("usuarioId") UUID usuarioId);

	Optional<Libro> findByIdapi(Integer idApi);
}
