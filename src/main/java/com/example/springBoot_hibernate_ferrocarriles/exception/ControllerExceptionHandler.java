package com.example.springBoot_hibernate_ferrocarriles.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.springBoot_hibernate_ferrocarriles.model.EquipoDeTraccion;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(value = {ResourceNotFoundException.class})
	public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request){
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(),ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}	
		
	@ExceptionHandler(value = {NoDataException.class})
	public ResponseEntity<ErrorMessage>noDataFoundException(NoDataException ex, WebRequest request){
		ErrorMessage message = new ErrorMessage(HttpStatus.NO_CONTENT.value(), LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NO_CONTENT);
	}
	/*
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}	
	*/
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex){
		List<FieldError> errors = ex.getBindingResult().getFieldErrors();
		String message = "";
		 for (FieldError error:errors) {
			 message = " " + error.getDefaultMessage();
		 }
		ErrorMessage message2 = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(), ex.getMessage(), message);
		return new ResponseEntity<ErrorMessage>(message2, HttpStatus.BAD_REQUEST);
	}	
	
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request){
		ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
