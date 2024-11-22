import { Component, OnInit  } from '@angular/core';
import { ApiService } from '../core/services/apiservice.service';
import { LibroDto } from '../core/models/libroDto.model';
import { KeycloakService } from 'keycloak-angular';


@Component({
  selector: 'app-libros-compartibles',
  templateUrl: './libros-compartibles.component.html',
  styleUrls: ['./libros-compartibles.component.css']
})
export class LibrosCompartiblesComponent implements OnInit {
  librosCompartibles: any[] = [];
  usuarioId: string = '';
  mensaje: string | null = null;

  constructor(
    private apiService: ApiService,
    private keycloakService: KeycloakService
  ) {}

  ngOnInit(): void {
    this.obtenerUsuarioId();
    this.cargarLibrosCompartibles();
  }

  obtenerUsuarioId() {
    // Extrae el ID del usuario desde el token JWT
    const token = this.keycloakService.getKeycloakInstance().tokenParsed;
    if (token && token.sub) {
      this.usuarioId = token.sub; // El campo "sub" contiene el ID del usuario
    } else {
      console.error('No se pudo obtener el ID del usuario.');
    }
  }
  // Obtener libros compartibles
  cargarLibrosCompartibles() {
    this.apiService.getLibrosCompartibles().subscribe(
      (libros) => {
        this.librosCompartibles = libros;
      },
      (error) => {
        console.error('Error al obtener libros compartibles:', error);
        this.mensaje = 'Hubo un problema al cargar los libros compartibles.';
      }
    );
  }

  prestarLibro(libroId: number): void {
    if (!this.usuarioId) {
      this.mensaje = 'Por favor, ingresa un usuario válido.';
      return;
    }

    // Llamamos al ApiService para prestar el libro
    this.apiService.prestarLibro(libroId, this.usuarioId).subscribe(
      (response) => {
        // Aquí 'response' es el mensaje del backend
        this.mensaje = response;  // Ejemplo: "Libro prestado con éxito."
        this.cargarLibrosCompartibles(); // Actualizar la lista de libros después de prestar
      },
      (error) => {
        console.error('Error al prestar el libro:', error);
        this.mensaje = 'Hubo un problema al prestar el libro. Inténtalo de nuevo.';
      }
    );
  }
}