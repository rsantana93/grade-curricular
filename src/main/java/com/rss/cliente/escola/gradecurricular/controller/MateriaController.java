package com.rss.cliente.escola.gradecurricular.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

import com.rss.cliente.escola.gradecurricular.contant.HyperLinkContant;
import com.rss.cliente.escola.gradecurricular.dto.MateriaDto;
import com.rss.cliente.escola.gradecurricular.model.Response;
import com.rss.cliente.escola.gradecurricular.service.IMateriaService;

@RestController
@RequestMapping("/materia")
public class MateriaController {

	@Autowired
	IMateriaService materiaService;

	@GetMapping
	public ResponseEntity<Response<List<MateriaDto>>> listarMaterias() {
		Response<List<MateriaDto>> response = new Response<>();
		
		response.setData(this.materiaService.listar());
		response.setHttpStatus(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
				.withSelfRel());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response<MateriaDto>> consultaMateria(@PathVariable Long id) {
		Response<MateriaDto> response = new Response<>();
		MateriaDto materia = this.materiaService.consultar(id);
		response.setData(materia);
		response.setHttpStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).consultaMateria(id))
				.withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).excluirMateria(id))
				.withRel(HyperLinkContant.DELETE.getValor()));
		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).atualizarMateria(materia)).withRel(HyperLinkContant.UPDATE.getValor()));
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Boolean>> excluirMateria(@PathVariable Long id) {
		Response<Boolean> response = new Response<>();
		response.setData(this.materiaService.excluir(id));
		response.setHttpStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).excluirMateria(id))
				.withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
				.withRel(HyperLinkContant.LIST.getValor()));
		
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@PutMapping
	public ResponseEntity<Response<Boolean>> atualizarMateria(@Valid @RequestBody MateriaDto materia) {
		Response<Boolean> response = new Response<>();
		response.setData(this.materiaService.atualizar(materia));
		response.setHttpStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).atualizarMateria(materia))
				.withSelfRel());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@PostMapping
	public ResponseEntity<Response<Boolean>> cadastrarMateria(@Valid @RequestBody MateriaDto materia) {
		Response<Boolean> response = new Response<>();
		response.setData(this.materiaService.cadastrar(materia));
		response.setHttpStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).cadastrarMateria(materia)).withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).atualizarMateria(materia))
				.withRel(HyperLinkContant.UPDATE.getValor()));
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
				.withRel(HyperLinkContant.LIST.getValor()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/horario-minimo/{hora}")
	public ResponseEntity<Response<List<MateriaDto>>> consultarMateriaPorHoraMinima(@PathVariable int hora) {
		Response<List<MateriaDto>> response = new Response<>();
		response.setData(this.materiaService.listarMateriaPorHoraMinima(hora));
		response.setHttpStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).consultarMateriaPorHoraMinima(hora))
				.withSelfRel());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/frequencia-minima/{freq}")
	public ResponseEntity<Response<List<MateriaDto>>> consultarMateriaPorFreqMinima(@PathVariable int freq) {
		Response<List<MateriaDto>> response = new Response<>();
		response.setData(this.materiaService.listarMateriaPorFreqMinima(freq));
		response.setHttpStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).consultarMateriaPorFreqMinima(freq))
				.withSelfRel());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
