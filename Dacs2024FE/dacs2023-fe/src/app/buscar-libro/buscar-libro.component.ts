import { Component, OnInit } from '@angular/core';
import { ApiService } from '../core/services/apiservice.service';
import { LibroBffDto } from '../core/models/libroBffDto.model';
import { LibroDto } from '../core/models/libroDto.model';
import { KeycloakService } from 'keycloak-angular';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-buscar-libro',
  templateUrl: './buscar-libro.component.html',
  styleUrls: ['./buscar-libro.component.css'],
})
export class BuscarLibroComponent implements OnInit {
  criterioBusqueda: string = '';
  tipoBusqueda: 'isbn' | 'titulo' | 'autor' = 'isbn';
  resultadosBusqueda: LibroBffDto[] = [];
  libroEnEdicion: LibroDto | null = null;
  usuarioId: string | null = null;
  mensaje: string = '';  // Para mostrar mensajes de error o éxito
  cargando: boolean = false;  // Para manejar el estado de carga

  constructor(
    private apiService: ApiService,
    private keycloakService: KeycloakService
  ) {}

  ngOnInit() {
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

    this.cargando = true; // Comienza a cargar
    this.resultadosBusqueda = []; // Limpiar resultados anteriores

    switch (this.tipoBusqueda) {
      case 'isbn':
        this.apiService.buscarLibroPorIsbn(this.criterioBusqueda).subscribe((resultados) => {
          this.resultadosBusqueda = resultados;
          this.cargando = false; // Termina de cargar
        }, error => {
          this.cargando = false; // Termina de cargar incluso si hay error
          console.error('Error al buscar libro por ISBN', error);
        });
        break;
      case 'titulo':
        this.apiService.buscarLibroPorTitulo(this.criterioBusqueda).subscribe((resultados) => {
          this.resultadosBusqueda = resultados;
          this.cargando = false;
        }, error => {
          this.cargando = false;
          console.error('Error al buscar libro por título', error);
        });
        break;
      case 'autor':
        this.apiService.buscarLibroPorAutor(this.criterioBusqueda).subscribe((resultados) => {
          this.resultadosBusqueda = resultados;
          this.cargando = false;
        }, error => {
          this.cargando = false;
          console.error('Error al buscar libro por autor', error);
        });
        break;
    }
  }

  seleccionarLibro(libro: LibroBffDto) {
    this.libroEnEdicion = {
      id: 0,
      titulo: libro.titulo,
      isbn: '',
      portada: libro.portada || '',
      resumen: libro.resumen || '',
      idApi: libro.idApi,
      archivado: false,
      sePuedeCompartir: true,
      nombreAutor: '',
    };
  }

  agregarLibro() {
    if (this.libroEnEdicion && this.usuarioId) {
      // Llamada al servicio con el responseType configurado para texto
      this.apiService.agregarLibro(this.usuarioId, this.libroEnEdicion).subscribe({
        next: (respuesta: string) => {
          // Verificamos si el backend ha respondido con el mensaje de éxito
          if (respuesta && respuesta.includes('Libro agregado correctamente')) {
            this.mensaje = 'Libro agregado exitosamente.';
            this.libroEnEdicion = null; // Limpiamos el libro en edición
          } else {
            // Si la respuesta no es la esperada, mostramos un error genérico
            this.mensaje = 'Ha ocurrido un error desconocido al intentar agregar el libro.';
            console.error('Respuesta inesperada:', respuesta);  // Log de depuración
          }
        },
        error: (error) => {
          console.error('Error al agregar el libro:', error);
          
          // Manejamos el error dependiendo de la respuesta del backend
          if (error.status === 400 && error.error) {
            if (error.error.includes('Este libro ya está en tu colección')) {
              this.mensaje = 'Este libro ya está en tu colección. No puedes agregarlo de nuevo.';
            } else if (error.error.includes('Este libro ya pertenece a otro usuario')) {
              this.mensaje = 'Este libro ya pertenece a otro usuario. No se puede agregar.';
            } else {
              this.mensaje = 'Ha ocurrido un error desconocido al intentar agregar el libro.';
            }
          } else {
            // Si el error no es 400, mostramos un error general
            this.mensaje = 'Ha ocurrido un error inesperado. Por favor, inténtalo nuevamente.';
            console.error(error);  // Log para depuración
          }
        }
      });
    } else {
      this.mensaje = 'Error: No se ha seleccionado un libro o el ID del usuario no está disponible.';
    }
  }
}  