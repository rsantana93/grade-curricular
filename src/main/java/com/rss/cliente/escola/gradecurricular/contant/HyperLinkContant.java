package com.rss.cliente.escola.gradecurricular.contant;

import lombok.Getter;

@Getter
public enum HyperLinkContant {
	DELETE("DELETE"),
	UPDATE("UPDATE"),
	LIST("GET_ALL");
	
	private String valor;
	
	private HyperLinkContant(String valor) {
		this.valor = valor;
	}

}
