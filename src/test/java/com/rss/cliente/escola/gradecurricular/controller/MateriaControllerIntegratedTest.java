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

import com.rss.cliente.escola.gradecurricular.dto.MateriaDto;
import com.rss.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rss.cliente.escola.gradecurricular.model.Response;
import com.rss.cliente.escola.gradecurricular.repository.IMateriaRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class MateriaControllerIntegratedTest {

	// anotação responsavel por settar o valor da porta
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private IMateriaRepository materiaRepository;

	private static String ENDERECO = "http://localhost:";

	private static String PATH = "/materia/";

	// Estamos inicional da nossa aplicacao.
	// Tudo que for chamado aqui sera executado antes dos testes
	@BeforeEach
	public void init() {
		this.montaBaseDados();
	}

	@AfterEach
	public void finish() {
		this.materiaRepository.deleteAll();
	}

	private void montaBaseDados() {

		MateriaEntity m1 = new MateriaEntity();
		m1.setCodigo("ILP12");
		m1.setFrequencia(81);
		m1.setHoras(1);
		m1.setNome("Sistemas");

		MateriaEntity m2 = new MateriaEntity();
		m2.setCodigo("ILM2");
		m2.setFrequencia(82);
		m2.setHoras(2);
		m2.setNome("Sistemas m2");

		MateriaEntity m3 = new MateriaEntity();
		m3.setCodigo("ILPM3");
		m3.setFrequencia(83);
		m3.setHoras(3);
		m3.setNome("Sistemas M3");

		List<MateriaEntity> lista = new ArrayList<MateriaEntity>();
		lista.add(m1);
		lista.add(m2);
		lista.add(m3);

		this.materiaRepository.saveAll(lista);
	}

	@Test
	public void testListarMaterias() {
		// Estamos fazendo uma chamada do nosso serviço
		ResponseEntity<Response<List<MateriaDto>>> materia = restTemplate.withBasicAuth("rsantana", "msgradecurricular").exchange(ENDERECO + this.port + PATH,
				HttpMethod.GET, null, new ParameterizedTypeReference<Response<List<MateriaDto>>>() {
				});
		// comparando os resultados
		assertNotNull(materia.getBody().getData());
		assertEquals(3, materia.getBody().getData().size());
		assertEquals(200, materia.getBody().getHttpStatus());
	}

	@Test
	public void testListarMateriaPorHoraMinima() {
		// Estamos fazendo uma chamada do nosso serviço
		ResponseEntity<Response<List<MateriaDto>>> materia = restTemplate.withBasicAuth("rsantana", "msgradecurricular").exchange(
				ENDERECO + this.port + PATH + "frequencia-minima/82", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<MateriaDto>>>() {
				});
		// comparando os resultados
		assertNotNull(materia.getBody().getData());
		assertEquals(2, materia.getBody().getData().size());
		assertEquals(200, materia.getBody().getHttpStatus());
	}

	@Test
	public void testListarMateriaPorFreqMinima() {
		// Estamos fazendo uma chamada do nosso serviço
		ResponseEntity<Response<List<MateriaDto>>> materia = restTemplate.withBasicAuth("rsantana", "msgradecurricular").exchange(
				ENDERECO + this.port + PATH + "horario-minimo/3", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<MateriaDto>>>() {
				});
		// comparando os resultados
		assertNotNull(materia);
		assertEquals(1, materia.getBody().getData().size());
		assertEquals(200, materia.getBody().getHttpStatus());
	}

	@Test
	public void testConsultarMateriaPorId() {

		List<MateriaEntity> materiasList = this.materiaRepository.findAll();
		Long id = materiasList.get(0).getId();

		ResponseEntity<Response<MateriaDto>> materias = restTemplate.withBasicAuth("rsantana", "msgradecurricular").exchange(ENDERECO + this.port + PATH + id,
				HttpMethod.GET, null, new ParameterizedTypeReference<Response<MateriaDto>>() {
				});
		assertNotNull(materias.getBody().getData());
		assertEquals(id, materias.getBody().getData().getId());
		assertEquals(1, materias.getBody().getData().getHoras());
		assertEquals("ILP12", materias.getBody().getData().getCodigo());
		assertEquals(200, materias.getBody().getHttpStatus());
	}

	@Test
	public void testCadastrarMateria() {

		MateriaEntity m4 = new MateriaEntity();
		m4.setCodigo("CALC1");
		m4.setFrequencia(2);
		m4.setHoras(102);
		m4.setNome("CALCULO I");

		HttpEntity<MateriaEntity> request = new HttpEntity<>(m4);

		ResponseEntity<Response<Boolean>> materias = restTemplate.withBasicAuth("rsantana", "msgradecurricular").exchange(ENDERECO + this.port + "/materia/",
				HttpMethod.POST, request, new ParameterizedTypeReference<Response<Boolean>>() {
				});
		List<MateriaEntity> listMateriaAtualizada = this.materiaRepository.findAll();

		assertTrue(materias.getBody().getData());
		assertEquals(4, listMateriaAtualizada.size());
		assertEquals(200, materias.getBody().getHttpStatus());
	}
	
	@Test
	public void testAtualizarMateria() {

		MateriaEntity m4 = new MateriaEntity();
		m4.setId(1L);
		m4.setCodigo("ILPM3");
		m4.setFrequencia(2);
		m4.setHoras(102);
		m4.setNome("CALCULO I");

		HttpEntity<MateriaEntity> request = new HttpEntity<>(m4);

		ResponseEntity<Response<Boolean>> materias = restTemplate.withBasicAuth("rsantana", "msgradecurricular").exchange(ENDERECO + this.port + "/materia/",
				HttpMethod.PUT, request, new ParameterizedTypeReference<Response<Boolean>>() {
				});

		assertTrue(materias.getBody().getData());
		assertEquals(200, materias.getBody().getHttpStatus());
	}

	@Test
	public void testExcluirMateriaPorId() {

		List<MateriaEntity> materiasList = this.materiaRepository.findAll();
		Long id = materiasList.get(0).getId();

		ResponseEntity<Response<Boolean>> materias = restTemplate.withBasicAuth("rsantana", "msgradecurricular").exchange(ENDERECO + this.port + "/materia/" + id,
				HttpMethod.DELETE, null, new ParameterizedTypeReference<Response<Boolean>>() {
				});

		List<MateriaEntity> listMateriaAtualizada = this.materiaRepository.findAll();

		assertTrue(materias.getBody().getData());
		assertEquals(2, listMateriaAtualizada.size());
		assertEquals(200, materias.getBody().getHttpStatus());
	}
}
