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

import com.rss.cliente.escola.gradecurricular.dto.CursoRequestDto;
import com.rss.cliente.escola.gradecurricular.dto.CursoResponseDto;
import com.rss.cliente.escola.gradecurricular.model.Response;
import com.rss.cliente.escola.gradecurricular.service.ICursoService;

@RestController
@RequestMapping("/curso")
public class CursoController {
	
	@Autowired
	private ICursoService cursoService;
	
	@GetMapping
	public ResponseEntity<Response<List<CursoResponseDto>>> listarCursos() {
		Response<List<CursoResponseDto>> response = new Response<>();
		response.setData(cursoService.listar());
		response.setHttpStatus(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).listarCursos())
				.withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping
	public ResponseEntity<Response<Boolean>> cadastrarCurso(@Valid @RequestBody CursoRequestDto curso) {
		Response<Boolean> response = new Response<>();
		response.setData(cursoService.cadastrar(curso));
		response.setHttpStatus(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).cadastrarCurso(curso)).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Response<CursoResponseDto>> consultaCursobyId(@PathVariable String codigo) {
		Response<CursoResponseDto> response = new Response<>();
		response.setData(cursoService.consultarPorCodigo(codigo));
		response.setHttpStatus(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).consultaCursobyId(codigo)).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Boolean>> excluirCurso(@PathVariable Long id) {
		Response<Boolean> response = new Response<>();
		response.setData(cursoService.excluir(id));
		response.setHttpStatus(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).excluirCurso(id)).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@PutMapping
	public ResponseEntity<Response<Boolean>> atualizarCurso(@Valid @RequestBody CursoRequestDto curso) {
		Response<Boolean> response = new Response<>();
		response.setData(cursoService.atualizar(curso));
		response.setHttpStatus(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CursoController.class).atualizarCurso(curso)).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

}
