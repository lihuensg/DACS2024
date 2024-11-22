import { Component,OnInit} from '@angular/core';
import { ApiService } from '../core/services/apiservice.service';
import { LibroDto } from '../core/models/libroDto.model';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-mis-libros',
  templateUrl: './mis-libros.component.html',
  styleUrls: ['./mis-libros.component.css']
})
export class MisLibrosComponent implements OnInit {
  misLibros: LibroDto[] = [];
  usuarioId: string | null | undefined = null;/* Aquí asigna el ID del usuario, obtenido al iniciar sesión */;
  libroEnEdicion: LibroDto | null = null; // Almacena el libro que se está editando


  constructor(private apiService: ApiService, private keycloakService: KeycloakService) {}

  async ngOnInit(): Promise<void> {
    try {
      // Obtiene el ID del usuario desde Keycloak
      const keycloakUserProfile = await this.keycloakService.loadUserProfile();
      this.usuarioId = keycloakUserProfile.id;

      // Llama al servicio para obtener los libros del usuario
      if (this.usuarioId) {
        this.apiService.getLibrosDeUsuario(this.usuarioId).subscribe(
          (libros: LibroDto[]) => {
            this.misLibros = libros;
          },
          (error: any) => {
            console.error('Error al obtener los libros del usuario:', error);
          }
        );
      }
    } catch (error) {
      console.error('Error al cargar el perfil del usuario de Keycloak:', error);
    }
  }

  eliminarLibro(libro: LibroDto): void {
    if (confirm(`¿Estás seguro de que deseas eliminar el libro "${libro.titulo}"?`)) {
      this.apiService.deleteLibro(libro.id).subscribe({
        next: () => {
          // Actualizar la lista de libros localmente
          this.misLibros = this.misLibros.filter((l) => l.id !== libro.id);
          alert('El libro se eliminó correctamente.');
        },
        error: (err) => {
          console.error('Error al eliminar el libro:', err);
          alert(
            'Hubo un problema al eliminar el libro. Por favor verifica si fue eliminado.'
          );
        },
      });
    }
  }
  

  editarLibro(libro: LibroDto): void {
    this.libroEnEdicion = { ...libro }; // Clon del libro para evitar modificaciones directas
  }
  cancelarEdicion(): void {
    this.libroEnEdicion = null; // Resetea el formulario
  }
  
  guardarCambios(): void {
  if (this.libroEnEdicion) {
    this.apiService.updateLibro(this.libroEnEdicion.id, this.libroEnEdicion).subscribe(
      (libroActualizado: LibroDto) => {
        // Encuentra el índice del libro en el arreglo original
        const index = this.misLibros.findIndex((l) => l.id === libroActualizado.id);
        if (index !== -1) {
          // Actualiza el arreglo con los nuevos datos
          this.misLibros[index] = libroActualizado;
        }
        this.libroEnEdicion = null; // Limpia el libro en edición
        alert('El libro fue actualizado exitosamente.');
      },
      (error) => {
        console.error('Error al actualizar el libro:', error);
        alert('Hubo un error al actualizar el libro.');
      }
    );
  }
}

}