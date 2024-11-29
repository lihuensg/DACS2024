import { Libro } from './libro.model';

export interface Feedback {
  id: number;
  libro: Libro; // El libro está anidado dentro de feedback
  nota: number;
  comentario: string;
}