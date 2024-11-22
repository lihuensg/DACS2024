export interface LibroDto {         
    id: number;
    titulo: string;
    isbn: string;
    portada: string;
    resumen: string;
    idApi: number; 
    archivado: boolean;
    sePuedeCompartir: boolean;
    nombreAutor: string; 
}