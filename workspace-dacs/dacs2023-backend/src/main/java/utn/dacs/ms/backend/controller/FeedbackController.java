package utn.dacs.ms.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import utn.dacs.ms.backend.service.FeedbackService;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarFeedback(@PathVariable Long id) {
        boolean eliminado = feedbackService.eliminarFeedbackPorId(id);
        if (eliminado) {
            return ResponseEntity.ok("Feedback eliminado correctamente.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Feedback no encontrado.");
    }
}