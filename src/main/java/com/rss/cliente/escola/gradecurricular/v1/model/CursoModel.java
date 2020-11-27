package com.rss.cliente.escola.gradecurricular.v1.model;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CursoModel extends RepresentationModel<CursoModel>{

	private Long id;
	private String nome;
	private String codCurso;
	private List<Long> materias;
}
