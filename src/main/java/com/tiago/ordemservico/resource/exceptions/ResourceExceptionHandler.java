package com.tiago.ordemservico.resource.exceptions;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tiago.ordemservico.services.exceptions.DataIntegratyViolationException;
import com.tiago.ordemservico.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private HttpStatus statusNotFound = HttpStatus.NOT_FOUND;
	private HttpStatus statusBadRequest = HttpStatus.BAD_REQUEST;

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException e) {
		StandardError error = new StandardError(System.currentTimeMillis(), statusNotFound.value(),
				e.getMessage());
		return ResponseEntity.status(statusNotFound).body(error);
	}
	
	@ExceptionHandler(DataIntegratyViolationException.class)
	public ResponseEntity<StandardError> dataIntegratyViolationException(DataIntegratyViolationException e) {
		StandardError error = new StandardError(System.currentTimeMillis(), statusBadRequest.value(),
				e.getMessage());
		return ResponseEntity.status(statusBadRequest).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException e) {
		ValidationError error = new ValidationError(System.currentTimeMillis(), statusBadRequest.value(),
				"Erro na validação dos campos!");
		
		for(FieldError fe : e.getBindingResult().getFieldErrors()) {
			error.addError(fe.getField(), fe.getDefaultMessage());
		}
				
		return ResponseEntity.status(statusBadRequest).body(error);
	}

}
