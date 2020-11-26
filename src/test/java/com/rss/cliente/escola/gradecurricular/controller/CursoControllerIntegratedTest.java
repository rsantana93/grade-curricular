package com.rss.cliente.escola.gradecurricular.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.rss.cliente.escola.gradecurricular.dto.CursoRequestDto;
import com.rss.cliente.escola.gradecurricular.dto.CursoResponseDto;
import com.rss.cliente.escola.gradecurricular.entity.CursoEntity;
import com.rss.cliente.escola.gradecurricular.model.Response;
import com.rss.cliente.escola.gradecurricular.repository.ICursoRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class CursoControllerIntegratedTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ICursoRepository repository;

	private static String ENDERECO = "http://localhost:";

	@BeforeEach
	public void init() {
		this.montaBaseDados();
	}

	@AfterEach
	public void finish() {
		this.repository.deleteAll();
	}

	private void montaBaseDados() {

		CursoEntity m1 = new CursoEntity();
		m1.setCodigo("SI01");
		m1.setNome("CURSO SI");

		CursoEntity m2 = new CursoEntity();
		m2.setCodigo("CS02");
		m2.setNome("CURSO ADS");

		CursoEntity m3 = new CursoEntity();
		m3.setCodigo("EC03");
		m3.setNome("CURSO EC");

		List<CursoEntity> lista = new ArrayList<>();
		lista.add(m1);
		lista.add(m2);
		lista.add(m3);
		this.repository.saveAll(lista);
	}

	@Test
	public void testListarCurso() {
		ResponseEntity<Response<List<CursoResponseDto>>> listaCurso = restTemplate
				.withBasicAuth("rsantana", "msgradecurricular").exchange(ENDERECO + this.port + "/curso/",
						HttpMethod.GET, null, new ParameterizedTypeReference<Response<List<CursoResponseDto>>>() {
						});

		assertNotNull(listaCurso.getBody().getData());
		assertEquals(3, listaCurso.getBody().getData().size());
		assertEquals(200, listaCurso.getBody().getHttpStatus());
	}

	@Test
	public void testConsultarCurso() {
		ResponseEntity<Response<CursoResponseDto>> curso = restTemplate.withBasicAuth("rsantana", "msgradecurricular")
				.exchange(ENDERECO + this.port + "/curso/SI01", HttpMethod.GET, null,
						new ParameterizedTypeReference<Response<CursoResponseDto>>() {
						});

		assertNotNull(curso.getBody().getData());
		assertEquals(200, curso.getBody().getHttpStatus());
	};

	@Test
	public void testCadastrarCurso() {
		CursoRequestDto curso = new CursoRequestDto();
		curso.setCodCurso("CURSO57");
		curso.setNome("ENG. DADOS");

		HttpEntity<CursoRequestDto> request = new HttpEntity<>(curso);

		ResponseEntity<Response<Boolean>> resultado = restTemplate.withBasicAuth("rsantana", "msgradecurricular")
				.exchange(ENDERECO + this.port + "/curso", HttpMethod.POST, request,
						new ParameterizedTypeReference<Response<Boolean>>() {
						});
		assertEquals(200, resultado.getBody().getHttpStatus());
		assertEquals(Boolean.TRUE, resultado.getBody().getData());
	};

	@Test
	public void testAtualizarCurso() {
		CursoRequestDto curso = new CursoRequestDto();

		List<CursoEntity> cursoList = this.repository.findAll();
		cursoList.get(0).getId();

		curso.setCodCurso(cursoList.get(0).getCodigo());
		curso.setId(cursoList.get(0).getId());
		curso.setNome("Nome Atualizado");

		HttpEntity<CursoRequestDto> request = new HttpEntity<>(curso);

		ResponseEntity<Response<Boolean>> resultado = restTemplate.withBasicAuth("rsantana", "msgradecurricular")
				.exchange(ENDERECO + this.port + "/curso", HttpMethod.PUT, request,
						new ParameterizedTypeReference<Response<Boolean>>() {
						});

		assertEquals(200, resultado.getBody().getHttpStatus());
		assertTrue(resultado.getBody().getData());

	};

	@Test
	public void testExcluirCurso() {
		List<CursoEntity> cursoList = this.repository.findAll();
		Long id = cursoList.get(0).getId();

		ResponseEntity<Response<Boolean>> materias = restTemplate.withBasicAuth("rsantana", "msgradecurricular")
				.exchange(ENDERECO + this.port + "/curso/" + id, HttpMethod.DELETE, null,
						new ParameterizedTypeReference<Response<Boolean>>() {
						});

		List<CursoEntity> listCursoAtualizado = this.repository.findAll();

		assertTrue(materias.getBody().getData());
		assertEquals(2, listCursoAtualizado.size());
		assertEquals(200, materias.getBody().getHttpStatus());
	};

}
