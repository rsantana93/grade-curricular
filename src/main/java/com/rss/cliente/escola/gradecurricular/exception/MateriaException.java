package com.rss.cliente.escola.gradecurricular.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class MateriaException extends RuntimeException {

	private static final long serialVersionUID = 5841408798659530900L;
	private final HttpStatus httpStatus;
	
	public MateriaException(final String msg, final HttpStatus httpStatus) {
		super(msg);
		this.httpStatus = httpStatus;
	}
	
	

}
