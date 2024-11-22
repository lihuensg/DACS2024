package utn.dacs.ms.conector.exceptions;

import org.springframework.http.HttpStatus;

 public interface IEnumError {
     /**
      * @return El numero de error.
      */
     Integer code();
     /**
      * @return El indice del mensaje almacenado en el archivo <b>messages.yml</b>.
      *
      * Por ejemplo: <i>rus.messages.error.ejemplo</i>
      */
     String message();

     /**
      * @return El objeto de Enum de estado de respuesta
      */
     HttpStatus status();
}
