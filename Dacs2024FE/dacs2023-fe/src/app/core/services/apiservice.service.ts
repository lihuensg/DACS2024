import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClientModule } from '@angular/common/http';
import { IRequestTest } from '../models/request.interface';
import { LibroDto } from '../models/libroDto.model'; 
import { LibroBffDto } from '../models/libroBffDto.model';
import { IResponse, ITestResponse } from '../models/response.interface';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ApiService {

    constructor(private http: HttpClient) {}
   
    //mis-libros
    getLibrosDeUsuario(usuarioId: string) {
        const url = `${environment.backendForFrontendUrl}/backend/libros/usuario/${usuarioId}`;
        return this.http.get<LibroDto[]>(url);
    }
   
    updateLibro(id: number, libro: LibroDto) {
        const url = `${environment.backendForFrontendUrl}/backend/libros/${id}`;
        return this.http.put<LibroDto>(url, libro);
      }
      
    deleteLibro(id: number) {
        const url = `${environment.backendForFrontendUrl}/backend/libros/${id}`;
        return this.http.delete<void>(url);
    } 

    //libros-compartibles
    getLibrosCompartibles() {
        const url = `${environment.backendForFrontendUrl}/backend/libros/compartibles`;
        return this.http.get<LibroDto[]>(url);
    }
      
    prestarLibro(libroId: number, usuarioId: string): Observable<string> {
        const url = `${environment.backendForFrontendUrl}/backend/libros/${libroId}/prestar/${usuarioId}`;
        // Configuramos 'responseType' para que la respuesta sea tratada como texto
        return this.http.post<string>(url, null, { responseType: 'text' as 'json' });
    }

  //buscar-libros 
    buscarLibroPorIsbn(isbn: string): Observable<LibroBffDto[]> {
        const url = `${environment.backendForFrontendUrl}/libros/isbn/${isbn}`;
        return this.http.get<LibroBffDto[]>(url);
    }

    buscarLibroPorTitulo(titulo: string): Observable<LibroBffDto[]> {
        const url = `${environment.backendForFrontendUrl}/libros/titulo/${titulo}`;
        return this.http.get<LibroBffDto[]>(url);
    }

    buscarLibroPorAutor(autor: string): Observable<LibroBffDto[]> {
     const url = `${environment.backendForFrontendUrl}/libros/autor/${autor}`;
     return this.http.get<LibroBffDto[]>(url);
    }

    agregarLibro(usuarioId: string, libro: LibroDto): Observable<void> {
        const url = `${environment.backendForFrontendUrl}/backend/libros/agregar/${usuarioId}`;
        return this.http.post<void>(url, libro);
    }

    //No tener en cuenta  
    getPing() {
        const url = `${environment.backendForFrontendUrl}/ping`;
        return this.http
            .get(url, { responseType: 'text' })
            .pipe();
    }

    getConectorPing() {
        const url = `${environment.backendForFrontendUrl}/conector/ping`;
        return this.http
            .get(url, { responseType: 'text' })
            .pipe();
    }
    
    getBackendPing() {
        const url = `${environment.backendForFrontendUrl}/backend/ping`;
        return this.http
            .get(url, { responseType: 'text' })
            .pipe();
    }

    getAlumnos() {
        const url = `${environment.backendForFrontendUrl}/backend/alumno`;
        return this.http
            .get<any[]>(url)
            .pipe();
    }

    getTest() {
        const url ='assets/json/test.json';
        return this.http
            .get<ITestResponse>(url, this.headers)
            .pipe();
    }

    postTest(param: IRequestTest) { 
        const url = `${environment.backendForFrontendUrl}/test`;
        return this.http.post<any[]>(url, param, this.headers);
    }

    get headers() {
        return {
            headers: {
                'Content-Type': 'application/json',
            },
        };
    }

}



