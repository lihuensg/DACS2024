package utn.dacs.ms.backend.model.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import feign.Param;
import utn.dacs.ms.backend.model.entity.Feedback;


@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
	@Query("SELECT f FROM Feedback f WHERE f.libro.usuario.id = :usuarioId")
    List<Feedback> findAllByUsuarioId(@Param("usuarioId") UUID usuarioId);

}