package utn.dacs.ms.bff.exceptions.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import utn.dacs.ms.bff.exceptions.ExceptionResponse;
import utn.dacs.ms.bff.exceptions.GenericException;

import java.util.Locale;

@ControllerAdvice
public class DacsControllerAdvice {

	@Autowired
	protected MessageSource messageSource;

	@Value("${dacs.exceptions.code-prefix}")
	protected String codePrefix;

	@ExceptionHandler(GenericException.class)
	public ResponseEntity<ExceptionResponse> handleBusinessException(GenericException ex,
																	 WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getError().status().value(),
				this.codePrefix + "_" + ex.getError().code(), this.getMessage(ex.getError().message(), ex.getParams()));

		return ResponseEntity.status(ex.getError().status()).body(exceptionResponse);
	}

	@ExceptionHandler(value = { MissingPathVariableException.class, NoHandlerFoundException.class })
	public ResponseEntity<ExceptionResponse> handleNotFoundException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				HttpStatus.NOT_FOUND.value(),
				this.codePrefix + "_" + HttpStatus.NOT_FOUND.value(), ex.getMessage());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				this.codePrefix + "_" + HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
	}

	protected String getMessage(String exceptionMessage, Object... params) {
		return this.messageSource.getMessage(exceptionMessage, params, new Locale("es", "AR"));
	}
}
