package com.rss.cliente.escola.gradecurricular.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rss.cliente.escola.gradecurricular.dto.MateriaDto;
import com.rss.cliente.escola.gradecurricular.service.IMateriaServices;

@RestController
@RequestMapping("/materia")
public class MateriaController {

	@Autowired
	IMateriaServices materiaServices;

	@GetMapping
	public ResponseEntity<List<MateriaDto>> listarMaterias() {
		return ResponseEntity.status(HttpStatus.OK).body(this.materiaServices.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<MateriaDto> consultaMateria(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(this.materiaServices.consultar(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deletaMateria(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(this.materiaServices.excluir(id));

	}

	@PutMapping
	public ResponseEntity<Boolean> atualizarMateria(@Valid @RequestBody MateriaDto materia) {
		return ResponseEntity.status(HttpStatus.OK).body(this.materiaServices.atualizar(materia));

	}

	@PostMapping
	public ResponseEntity<Boolean> cadastrarMateria(@Valid @RequestBody MateriaDto materia) {
		return ResponseEntity.status(HttpStatus.OK).body(this.materiaServices.cadastrar(materia));
	}
}
