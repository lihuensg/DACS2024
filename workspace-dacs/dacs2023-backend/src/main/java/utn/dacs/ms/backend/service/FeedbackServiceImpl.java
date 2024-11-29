package utn.dacs.ms.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utn.dacs.ms.backend.model.repository.FeedbackRepository;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public boolean eliminarFeedbackPorId(Long feedbackId) {
        // Verificamos si el Feedback existe en la base de datos
        if (feedbackRepository.existsById(feedbackId)) {
            // Si existe, lo eliminamos
            feedbackRepository.deleteById(feedbackId);
            return true;
        }
        // Si no existe, devolvemos false
        return false;
    }
}