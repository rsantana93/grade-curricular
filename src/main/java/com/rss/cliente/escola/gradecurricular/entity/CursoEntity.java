package com.rss.cliente.escola.gradecurricular.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="tb_curso")
@Data
@NoArgsConstructor
public class CursoEntity implements Serializable{
	
	private static final long serialVersionUID = 7689852101530464467L;
	
	@JsonInclude(Include.NON_NULL)
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	@JsonInclude(Include.NON_EMPTY)
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "cod")
	@JsonInclude(Include.NON_EMPTY)
	private String codigo;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "materia_id")
	private List<MateriaEntity> materias;
}
