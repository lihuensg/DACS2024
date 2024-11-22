import { Component, OnInit } from '@angular/core';
import { ApiService } from '../core/services/apiservice.service';
import { LibroBffDto } from '../core/models/libroBffDto.model';
import { LibroDto } from '../core/models/libroDto.model';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-buscar-libro',
  templateUrl: './buscar-libro.component.html',
  styleUrls: ['./buscar-libro.component.css'],
})
export class BuscarLibroComponent  implements OnInit{
  criterioBusqueda: string = '';
  tipoBusqueda: 'isbn' | 'titulo' | 'autor' = 'isbn';
  resultadosBusqueda: LibroBffDto[] = [];
  libroEnEdicion: LibroDto | null = null;
  usuarioId: string | null = null;
  
  constructor(
    private apiService: ApiService,
    private keycloakService: KeycloakService
  ) {}
 
  ngOnInit() {
    // Obtén el ID del usuario desde Keycloak
    const userProfile = this.keycloakService.getKeycloakInstance().profile;
    if (userProfile && userProfile.id) {
      this.usuarioId = userProfile.id;
    } else {
      console.error('No se pudo obtener el ID del usuario desde Keycloak.');
    }
  }
  
  buscarLibros() {
    if (!this.criterioBusqueda.trim()) {
      alert('Por favor, ingresa un criterio de búsqueda.');
      return;
    }

    switch (this.tipoBusqueda) {
      case 'isbn':
        this.apiService.buscarLibroPorIsbn(this.criterioBusqueda).subscribe((resultados) => {
          this.resultadosBusqueda = resultados;
        });
        break;
      case 'titulo':
        this.apiService.buscarLibroPorTitulo(this.criterioBusqueda).subscribe((resultados) => {
          this.resultadosBusqueda = resultados;
        });
        break;
      case 'autor':
        this.apiService.buscarLibroPorAutor(this.criterioBusqueda).subscribe((resultados) => {
          this.resultadosBusqueda = resultados;
        });
        break;
    }
  }

  seleccionarLibro(libro: LibroBffDto) {
    this.libroEnEdicion = {
      id: 0, // Lo asignará el backend
      titulo: libro.titulo,
      isbn: '', // Completar manualmente
      portada: libro.portada || '',
      resumen: libro.resumen || '', // Si el resumen no está disponible, dejar vacío
      idApi: libro.idApi,
      archivado: false,
      sePuedeCompartir: true,
      nombreAutor: '', // Completar manualmente
    };
  }

  agregarLibro() {
    if (this.libroEnEdicion && this.usuarioId) {
      this.apiService.agregarLibro(this.usuarioId, this.libroEnEdicion).subscribe(() => {
        alert('Libro agregado exitosamente.');
        this.libroEnEdicion = null;
      }, (error) => {
        console.error('Error al agregar el libro:', error);
        alert('No se pudo agregar el libro. Por favor, inténtalo nuevamente.');
      });
    } else {
      alert('Error: No se pudo obtener el ID del usuario o los datos del libro están incompletos.');
    }
  }
}