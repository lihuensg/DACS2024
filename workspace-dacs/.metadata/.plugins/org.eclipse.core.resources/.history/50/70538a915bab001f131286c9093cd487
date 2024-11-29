package utn.dacs.ms.backend.model.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import utn.dacs.ms.backend.model.entity.Libro;
import utn.dacs.ms.backend.model.entity.Usuario;


public interface LibroRepository extends JpaRepository<Libro, Long> {
	List<Libro> findBySepuedecompartirTrue();
	List<Libro> findByUsuario(Usuario usuario);
	
}
