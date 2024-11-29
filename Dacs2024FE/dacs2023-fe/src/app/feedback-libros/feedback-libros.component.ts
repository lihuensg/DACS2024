// feedback-libros.component.ts
import { Component, OnInit } from '@angular/core';
import { ApiService } from '../core/services/apiservice.service';
import { Feedback } from '../core/models/feedback.model';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-feedback-libros',
  templateUrl: './feedback-libros.component.html',
  styleUrls: ['./feedback-libros.component.css']
})
export class FeedbackLibrosComponent implements OnInit {

  feedbackLibros: Feedback[] = [];
  mensaje: string = '';

  constructor(
    private apiService: ApiService,
    private keycloakService: KeycloakService
  ) {}

  ngOnInit(): void {
    this.cargarFeedbackLibros();
  }

  cargarFeedbackLibros(): void {
    const usuarioId = this.keycloakService.getKeycloakInstance().subject;
    if (usuarioId) {
      this.apiService.getFeedbackLibrosDevueltos(usuarioId).subscribe({
        next: (feedbacks) => {
          this.feedbackLibros = feedbacks;
        },
        error: (err) => {
          console.error('Error al obtener feedback de los libros:', err);
          this.mensaje = 'Hubo un error al obtener los feedbacks. Intente nuevamente.';
        }
      });
    }
  }

  eliminarFeedback(feedbackId: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar este feedback?')) {
      this.apiService.eliminarFeedback(feedbackId).subscribe({
        next: (mensaje) => {
          this.mensaje = 'Feedback eliminado correctamente.';
          // Actualiza la lista localmente eliminando el feedback
          this.feedbackLibros = this.feedbackLibros.filter(
            (feedback) => feedback.id !== feedbackId
          );
        },
        error: (err) => {
          console.error('Error al eliminar el feedback:', err);
          this.mensaje = 'No se pudo eliminar el feedback. Intente nuevamente.';
        },
      });
    }
  }
}
