import { Component, OnInit } from '@angular/core';
import { ApiService } from '../core/services/apiservice.service';
import { LibroDto } from '../core/models/libroDto.model';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-devolver-libros',
  templateUrl: './devolver-libros.component.html',
  styleUrls: ['./devolver-libros.component.css']
})
export class DevolverLibrosComponent implements OnInit {
  librosNoDevueltos: LibroDto[] = [];
  mensaje: string = '';
  libroFeedback: { [key: number]: { nota: number, comentario: string } } = {};  // Usamos un objeto para manejar las notas y comentarios por libro
  errorNota: { [key: number]: string } = {};  // Para almacenar errores de validación por libro

  constructor(private apiService: ApiService, private keycloakService: KeycloakService) {}

  ngOnInit(): void {
    this.cargarLibrosNoDevueltos();
  }

  // Cargar libros no devueltos
  cargarLibrosNoDevueltos(): void {
    const usuarioId = this.keycloakService.getKeycloakInstance().subject;
    if (usuarioId) {
      this.apiService.getLibrosNoDevueltos(usuarioId).subscribe({
        next: (libros) => {
          this.librosNoDevueltos = libros;
          this.librosNoDevueltos.forEach(libro => {
            // Inicializamos las propiedades de nota y comentario para cada libro
            this.libroFeedback[libro.id] = { nota: 0, comentario: '' };
          });
        },
        error: (err) => console.error('Error al obtener libros no devueltos:', err)
      });
    }
  }

  // Devolver libro con nota y comentario
  devolverLibro(libroId: number): void {
    const { nota, comentario } = this.libroFeedback[libroId];

    // Validar que la nota esté entre 1 y 5
    if (nota < 1 || nota > 5) {
      this.errorNota[libroId] = 'La nota debe estar entre 1 y 5.';
      return;  // No hacemos la llamada si la nota no es válida
    } else {
      // Si la validación pasa, eliminamos el mensaje de error
      this.errorNota[libroId] = '';
    }

    const usuarioId = this.keycloakService.getKeycloakInstance().subject;
    if (usuarioId) {
      this.apiService.devolverLibro(libroId, usuarioId, nota, comentario).subscribe({
        next: (mensaje) => {
          this.mensaje = mensaje;
          this.cargarLibrosNoDevueltos(); // Refrescar lista de libros
        },
        error: (err) => {
          console.error('Error al devolver libro:', err);
          this.mensaje = 'Hubo un error al devolver el libro. Por favor, intente de nuevo.';
        }
      });
    }
  }
}
