package com.rss.cliente.escola.gradecurricular.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class MateriaDto extends RepresentationModel<MateriaDto>{

	private Long id;
	@NotBlank(message = "Informe o nome da matéria.")
	private String nome;
	
	@Min(value = 34, message = "Permitido o mínimo de 34 horas aula.")
	@Max(value = 102, message = "Permitido o máximo de 102 horas aula.")
	private int horas;
	
	@NotBlank(message = "Informe o código da matéria.")
	private String codigo;
	
	@Min(value = 1, message = "A frequência mínima é 1.")
	@Max(value = 2, message = "A frequência máxima é 2.")
	private int frequencia;
}
