package com.rss.cliente.escola.gradecurricular.v1.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CursoRequestDto extends RepresentationModel<CursoRequestDto>{

	private Long id;
	
	@NotBlank(message = "Informe o nome do curso.")
	private String nome;
	
	@NotBlank(message = "Informe o código do curso.")
	private String codCurso;
	
	private List<Long> materias;
}
