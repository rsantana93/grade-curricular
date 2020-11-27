package com.rss.cliente.escola.gradecurricular.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CursoResponseDto extends RepresentationModel<CursoResponseDto>{

	private Long id;
	
	@NotBlank(message = "Informe o nome do curso.")
	private String nome;
	
	@NotBlank(message = "Informe o código do curso.")
	private String codigo;
	
}
