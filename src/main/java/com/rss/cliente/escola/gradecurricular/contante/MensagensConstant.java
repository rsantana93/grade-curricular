package com.rss.cliente.escola.gradecurricular.contante;

import lombok.Getter;

@Getter
public enum MensagensConstant {
	MENSAGEM_ERRO("Erro interno identificado. Contate o suporte"),
    CURSO_NAO_ENCONTRADA("curso não encontrada"),
	MATERIA_NAO_ENCONTRADA("Matéria não encontrada"),
	ERRO_CURSO_CADASTRADO_ANTERIORMENTE("Curso já cadastrado antetiormente"),
	ERRO_ID_INFORMADO("Id não informado"),
	ERRO_CURSO_NAO_ENCONTRADO("Curso não encontrado");
	
	private String valor;
	
	private MensagensConstant(String valor) {
		this.valor = valor;
	}

}
