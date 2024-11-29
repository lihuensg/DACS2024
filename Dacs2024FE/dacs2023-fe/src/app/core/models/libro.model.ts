export interface Libro {
    id: number;
    titulo: string;
    isbn: string;
    portada: string;
    resumen: string;
    idApi: number;
    archivado: boolean;
    sepuedecompartir: boolean;
    nombreautor: string;  // Esta propiedad debe coincidir con el nombre que devuelve la API
  }