package com.rss.cliente.escola.gradecurricular.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rss.cliente.escola.gradecurricular.exception.MateriaException;
import com.rss.cliente.escola.gradecurricular.model.ErrorMapResponse;
import com.rss.cliente.escola.gradecurricular.model.ErrorMapResponse.ErrorMapResponseBuilder;
import com.rss.cliente.escola.gradecurricular.model.ErrorResponse;
import com.rss.cliente.escola.gradecurricular.model.ErrorResponse.ErrorResponseBuilder;

@ControllerAdvice
public class ResourceHandler {
	
	@ExceptionHandler(MateriaException.class)
	public ResponseEntity<ErrorResponse> handlerMateriaException(MateriaException m){
		ErrorResponseBuilder erro = ErrorResponse.builder();
		erro.httpStatus(m.getHttpStatus().value());
		erro.mensagem(m.getMessage());
		erro.timeStamp(System.currentTimeMillis());
		return ResponseEntity.status(m.getHttpStatus()).body(erro.build());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMapResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException m){
		Map<String, String> erros= new HashMap<>();
		m.getBindingResult().getAllErrors().forEach(erro ->{
			String campo = ((FieldError)erro).getField();
			String msg = erro.getDefaultMessage();
			erros.put(campo, msg);
		});
		
		ErrorMapResponseBuilder errorMap = ErrorMapResponse.builder();
		errorMap.erros(erros)
				.httpStatus(HttpStatus.BAD_REQUEST.value())
				.timeStamp(System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap.build());
		
	}
}
