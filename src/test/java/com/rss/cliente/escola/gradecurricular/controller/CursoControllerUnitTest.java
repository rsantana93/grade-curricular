package com.rss.cliente.escola.gradecurricular.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.rss.cliente.escola.gradecurricular.dto.CursoRequestDto;
import com.rss.cliente.escola.gradecurricular.dto.CursoResponseDto;
import com.rss.cliente.escola.gradecurricular.dto.MateriaDto;
import com.rss.cliente.escola.gradecurricular.entity.CursoEntity;
import com.rss.cliente.escola.gradecurricular.model.Response;
import com.rss.cliente.escola.gradecurricular.service.ICursoService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class CursoControllerUnitTest {

	@LocalServerPort
	private int port;

	@MockBean
	private ICursoService cursoService;

	@Autowired
	private TestRestTemplate restTemplate;

	private static CursoRequestDto cursoDto;

	@BeforeAll
	public static void init() {

		cursoDto = new CursoRequestDto();
		cursoDto.setId(1L);
		cursoDto.setCodCurso("ENGCP");
		cursoDto.setNome("ENGENHARIA DA COMPUTAÇÃO");

	}
	
	private String montaUri(String urn) {
		return "http://localhost:" + this.port + "/curso/"+urn;
	}

	@Test
	public void testListarCursos() {
		Mockito.when(this.cursoService.listar()).thenReturn(new ArrayList<CursoResponseDto>());

		ResponseEntity<Response<List<CursoEntity>>> cursos = restTemplate.exchange(
				this.montaUri(""), HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<CursoEntity>>>() {
				});
		assertNotNull(cursos.getBody().getData());
		assertEquals(200, cursos.getBody().getHttpStatus());
	}

	@Test
	public void testConsultarCurso() {
		
		CursoResponseDto curso = new CursoResponseDto();
		curso.setId(1L);
		curso.setCodigo("ENGCOMP");
		curso.setNome("ENGENHARIA DA COMPUTACAO");
		curso.setMaterias(new ArrayList<MateriaDto>());
		Mockito.when(this.cursoService.consultarPorCodigo("ENGCOMP")).thenReturn(curso);

		ResponseEntity<Response<CursoResponseDto>> cursoResponse = restTemplate.exchange(
				this.montaUri("ENGCOMP"), HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<CursoResponseDto>>() {
				});
		assertNotNull(cursoResponse.getBody().getData());
		assertEquals(200, cursoResponse.getBody().getHttpStatus());
	}

	@Test
	public void testCadastrarCurso() {
		Mockito.when(this.cursoService.cadastrar(cursoDto)).thenReturn(Boolean.TRUE);

		cursoDto.setId(null);
		
		HttpEntity<CursoRequestDto> request = new HttpEntity<>(cursoDto);

		ResponseEntity<Response<Boolean>> curso = restTemplate.exchange(
				this.montaUri(""), HttpMethod.POST, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		assertTrue(curso.getBody().getData());
		assertEquals(200, curso.getBody().getHttpStatus());
		cursoDto.setId(1L);
	}

	@Test
	public void testAtualizarCurso() {
		Mockito.when(this.cursoService.atualizar(cursoDto)).thenReturn(Boolean.TRUE);

		HttpEntity<CursoRequestDto> request = new HttpEntity<>(cursoDto);

		ResponseEntity<Response<Boolean>> curso = restTemplate.exchange(
				this.montaUri(""), HttpMethod.PUT, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		assertTrue(curso.getBody().getData());
		assertEquals(200, curso.getBody().getHttpStatus());
	}

	@Test
	public void testExcluirCurso() {
		Mockito.when(this.cursoService.excluir(1L)).thenReturn(Boolean.TRUE);

		ResponseEntity<Response<Boolean>> curso = restTemplate.exchange(
				this.montaUri("1"), HttpMethod.DELETE, null,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		assertTrue(curso.getBody().getData());
		assertEquals(200, curso.getBody().getHttpStatus());
	}
}
