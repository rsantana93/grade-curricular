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

import com.rss.cliente.escola.gradecurricular.v1.dto.MateriaDto;
import com.rss.cliente.escola.gradecurricular.v1.model.Response;
import com.rss.cliente.escola.gradecurricular.v1.service.IMateriaService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class MateriaControllerUnitTest {

	private static String ENDERECO = "http://localhost:";

	// anotação responsavel por settar o valor da porta
	@LocalServerPort
	private int port;

	@MockBean
	private IMateriaService materiaService;
	// usamos para fazer a chamada do nosso serviço

	@Autowired
	private TestRestTemplate restTemplate;

	private static MateriaDto materiaDto;

	// Estamos inicional da nossa aplicacao.
	// Tudo que for chamado aqui sera executado antes dos testes
	@BeforeAll
	public static void init() {
		materiaDto = new MateriaDto();
		materiaDto.setId(1L);
		materiaDto.setCodigo("ILP123");
		materiaDto.setFrequencia(1);
		materiaDto.setHoras(64);
		materiaDto.setNome("Introducao à linguagem de programacao");
	}

	@Test
	public void testListarMaterias() {
		// Estamos mockando o retorno do metodo listar
		Mockito.when(materiaService.listar()).thenReturn(new ArrayList<MateriaDto>());
		// Estamos fazendo uma chamada do nosso serviço
		ResponseEntity<Response<List<MateriaDto>>> materia = restTemplate.withBasicAuth("rsantana", "msgradecurricular").exchange(ENDERECO + this.port + "/v1/materia/",
				HttpMethod.GET, null, new ParameterizedTypeReference<Response<List<MateriaDto>>>() {
				});
		// comparando os resultados
		assertNotNull(materia.getBody().getData());
		assertEquals(200, materia.getBody().getHttpStatus());
	}

	@Test
	public void testCadastrarMateria() {
		Mockito.when(this.materiaService.cadastrar(materiaDto)).thenReturn(Boolean.TRUE);
		HttpEntity<MateriaDto> request = new HttpEntity<>(materiaDto);
		ResponseEntity<Response<Boolean>> sucesso = restTemplate.withBasicAuth("rsantana", "msgradecurricular").exchange(ENDERECO + this.port + "/v1/materia/",
				HttpMethod.POST, request, new ParameterizedTypeReference<Response<Boolean>>() {
				});
		assertTrue(sucesso.getBody().getData());
		assertEquals(200, sucesso.getBody().getHttpStatus());
	}

	@Test
	public void testAtualizarMateria() {
		Mockito.when(this.materiaService.atualizar(materiaDto)).thenReturn(Boolean.TRUE);
		HttpEntity<MateriaDto> request = new HttpEntity<>(materiaDto);
		ResponseEntity<Response<Boolean>> sucesso = restTemplate.withBasicAuth("rsantana", "msgradecurricular").exchange(ENDERECO + this.port + "/v1/materia/",
				HttpMethod.PUT, request, new ParameterizedTypeReference<Response<Boolean>>() {
				});
		assertTrue(sucesso.getBody().getData());
		assertEquals(200, sucesso.getBody().getHttpStatus());
	}

	@Test
	public void testExcluirMateria() {
		Mockito.when(this.materiaService.excluir(1L)).thenReturn(Boolean.TRUE);
		HttpEntity<MateriaDto> request = new HttpEntity<>(materiaDto);
		ResponseEntity<Response<Boolean>> sucesso = restTemplate.withBasicAuth("rsantana", "msgradecurricular").exchange(ENDERECO + this.port + "/v1/materia/1",
				HttpMethod.DELETE, request, new ParameterizedTypeReference<Response<Boolean>>() {
				});
		assertTrue(sucesso.getBody().getData());
		assertEquals(200, sucesso.getBody().getHttpStatus());
	}

	@Test
	public void testConsultarMateriaById() {
		// Estamos mockando o retorno do metodo consultar por id
		Mockito.when(materiaService.consultar(1L)).thenReturn(new MateriaDto());
		// Estamos fazendo uma chamada do nosso serviço
		ResponseEntity<Response<MateriaDto>> materia = restTemplate.withBasicAuth("rsantana", "msgradecurricular").exchange(ENDERECO + this.port + "/v1/materia/1",
				HttpMethod.GET, null, new ParameterizedTypeReference<Response<MateriaDto>>() {
				});
		// comparando os resultados
		assertNotNull(materia);
		assertEquals(200, materia.getBody().getHttpStatus());
	}

	@Test
	public void testListarMateriaPorHoraMinima() {
		// Estamos mockando o retorno do metodo listar por hora minima
		Mockito.when(materiaService.listarMateriaPorFreqMinima(64)).thenReturn(new ArrayList<MateriaDto>());
		// Estamos fazendo uma chamada do nosso serviço
		ResponseEntity<Response<List<MateriaDto>>> materia = restTemplate.withBasicAuth("rsantana", "msgradecurricular").exchange(
				ENDERECO + this.port + "/v1/materia/frequencia-minima/64", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<MateriaDto>>>() {
				});
		// comparando os resultados
		assertNotNull(materia.getBody().getData());
		assertEquals(200, materia.getBody().getHttpStatus());
	}

	@Test
	public void testListarMateriaPorFreqMinima() {
		// Estamos mockando o retorno do metodo listar por frenquencia minima
		Mockito.when(materiaService.listarMateriaPorHoraMinima(1)).thenReturn(new ArrayList<MateriaDto>());
		// Estamos fazendo uma chamada do nosso serviço
		ResponseEntity<Response<List<MateriaDto>>> materia = restTemplate.withBasicAuth("rsantana", "msgradecurricular").exchange(
				ENDERECO + this.port + "/v1/materia/horario-minimo/1", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<MateriaDto>>>() {
				});
		// comparando os resultados
		assertNotNull(materia.getBody().getData());
		assertEquals(200, materia.getBody().getHttpStatus());
	}
}
