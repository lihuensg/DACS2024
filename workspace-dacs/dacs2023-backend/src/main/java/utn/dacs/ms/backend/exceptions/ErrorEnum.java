package utn.dacs.ms.backend.exceptions;

import org.springframework.http.HttpStatus;

public enum ErrorEnum implements IEnumError {
    ERROR_INESPERADO(1000,"dacs.messages.error.inesperado",HttpStatus.BAD_REQUEST),
    DATO_SIN_VALOR_SELECCIONADO(1001,"dacs.messages.error.dato.sin.valor.seleccionado",HttpStatus.BAD_REQUEST),
    DATO_SIN_VALOR_INGRESADO(1002,"dacs.messages.error.dato.sin.valor.ingresado",HttpStatus.BAD_REQUEST),
    DATO_TIPO_INCORRECTO(1003,"dacs.messages.error.dato.tipo.incorrecto",HttpStatus.BAD_REQUEST),
    DATO_VALOR_INCORRECTO(1004,"dacs.messages.error.dato.valor.incorrecto",HttpStatus.BAD_REQUEST),
    EJEMPLO_CON_PARAMETROS(1005,"dacs.messages.error.ejemplo.con.parametros",HttpStatus.BAD_REQUEST),
    ERROR_API(1006,"dacs.messages.error.api",HttpStatus.BAD_REQUEST);

    ErrorEnum(Integer code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    private Integer code;
    private String message;
    private HttpStatus status;

    @Override
    public Integer code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }

    @Override
    public HttpStatus status() {
        return this.status;
    }
}
