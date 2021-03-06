package com.rss.cliente.escola.gradecurricular.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.rss.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rss.cliente.escola.gradecurricular.repository.IMateriaRepository;
import com.rss.cliente.escola.gradecurricular.v1.constant.MensagensConstant;
import com.rss.cliente.escola.gradecurricular.v1.dto.MateriaDto;
import com.rss.cliente.escola.gradecurricular.v1.exception.MateriaException;
import com.rss.cliente.escola.gradecurricular.v1.service.MateriaService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class MateriaServiceUnitTest {

	@Mock
	private IMateriaRepository materiaRepository;

	@InjectMocks
	private MateriaService materiaService;

	private static MateriaEntity materiaEntity;

	@BeforeAll
	public static void init() {

		materiaEntity = new MateriaEntity();
		materiaEntity.setId(1L);
		materiaEntity.setCodigo("ILP");
		materiaEntity.setFrequencia(1);
		materiaEntity.setHoras(64);
		materiaEntity.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");

	}

	/*
	 * 
	 * CENARIOS DE SUCESSO
	 * 
	 */

	@Test
	public void testListarSucesso() {
		List<MateriaEntity> listMateria = new ArrayList<>();
		listMateria.add(materiaEntity);

		Mockito.when(this.materiaRepository.findAll()).thenReturn(listMateria);

		List<MateriaDto> listMateriaDto = this.materiaService.listar();

		assertNotNull(listMateriaDto);
		assertEquals("ILP", listMateriaDto.get(0).getCodigo());
		assertEquals("/materia/1", listMateriaDto.get(0).getLinks().getRequiredLink("self").getHref());
		assertEquals(1, listMateriaDto.size());

		Mockito.verify(this.materiaRepository, times(1)).findAll();

	}

	@Test
	public void testListarPorHorarioMinimoSucesso() {
		List<MateriaEntity> listMateria = new ArrayList<>();
		listMateria.add(materiaEntity);

		Mockito.when(this.materiaRepository.findByHoraMinima(64)).thenReturn(listMateria);

		List<MateriaDto> listMateriaDto = this.materiaService.listarMateriaPorHoraMinima(64);

		assertNotNull(listMateriaDto);
		assertEquals("ILP", listMateriaDto.get(0).getCodigo());
		//assertEquals(1, listMateriaDto.get(0).getId());
		assertEquals(1, listMateriaDto.size());

		Mockito.verify(this.materiaRepository, times(1)).findByHoraMinima(64);

	}

	@Test
	public void testListarPorFrequenciaSucesso() {
		List<MateriaEntity> listMateria = new ArrayList<>();
		listMateria.add(materiaEntity);

		Mockito.when(this.materiaRepository.findByFreqMinima(1)).thenReturn(listMateria);

		List<MateriaDto> listMateriaDto = this.materiaService.listarMateriaPorFreqMinima(1);

		assertNotNull(listMateriaDto);
		assertEquals("ILP", listMateriaDto.get(0).getCodigo());
		assertEquals(1, listMateriaDto.size());

		Mockito.verify(this.materiaRepository, times(1)).findByFreqMinima(1);

	}

	@Test
	public void testConsultarSucesso() {
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		MateriaDto materiaDto = this.materiaService.consultar(1L);

		assertNotNull(materiaDto);
		assertEquals("ILP", materiaDto.getCodigo());
		//assertEquals(1L, materiaDto.getId());
		assertEquals(1, materiaDto.getFrequencia());

		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
	}

	@Test
	public void testCadastrarSucesso() {

		MateriaDto materiaDto = new MateriaDto();
		materiaDto.setCodigo("ILP");
		materiaDto.setFrequencia(1);
		materiaDto.setHoras(64);
		materiaDto.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");

		materiaEntity.setId(null);

		Mockito.when(this.materiaRepository.save(materiaEntity)).thenReturn(materiaEntity);

		Boolean sucesso = this.materiaService.cadastrar(materiaDto);

		assertTrue(sucesso);

		Mockito.verify(this.materiaRepository, times(1)).findByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);

		materiaEntity.setId(1L);

	}
	
	@Test
	public void testAtualizarSucesso() {

		MateriaDto materiaDto = new MateriaDto();
		materiaDto.setId(1L);
		materiaDto.setCodigo("ILP");
		materiaDto.setFrequencia(1);
		materiaDto.setHoras(64);
		materiaDto.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");


		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Mockito.when(this.materiaRepository.save(materiaEntity)).thenReturn(materiaEntity);

		Boolean sucesso = this.materiaService.atualizar(materiaDto);

		assertTrue(sucesso);

		Mockito.verify(this.materiaRepository, times(0)).findByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);


	}
	
	@Test
	public void testExcluirSucesso() {
		
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Boolean sucesso = this.materiaService.excluir(1L);

		assertTrue(sucesso);

		Mockito.verify(this.materiaRepository, times(0)).findByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(1)).deleteById(1L);
		Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);
	}

	/*
	 * 
	 * CENARIOS DE THROW-MATERIA-EXCEPTION
	 * 
	 */
	@Test
	public void testAtualizarThrowMateriaException() {

		MateriaDto materiaDto = new MateriaDto();
		materiaDto.setId(1L);
		materiaDto.setCodigo("ILP");
		materiaDto.setFrequencia(1);
		materiaDto.setHoras(64);
		materiaDto.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");


		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.empty());

		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			 this.materiaService.atualizar(materiaDto);
		});
		
		assertEquals(HttpStatus.NOT_FOUND, materiaException.getHttpStatus());
		assertEquals(MensagensConstant.ERRO_CURSO_NAO_ENCONTRADO.getValor(), materiaException.getMessage());

		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);

	}
	
	@Test
	public void testExcluirThrowMateriaException() {


		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.empty());

		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			 this.materiaService.excluir(1L);
		});
		
		assertEquals(HttpStatus.NOT_FOUND, materiaException.getHttpStatus());
		assertEquals(MensagensConstant.ERRO_CURSO_NAO_ENCONTRADO.getValor(), materiaException.getMessage());

		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(0)).deleteById(1L);

	}
	
	@Test
	public void testCadastrarComIdThrowMateriaException() {

		MateriaDto materiaDto = new MateriaDto();
		materiaDto.setId(1L);
		materiaDto.setCodigo("ILP");
		materiaDto.setFrequencia(1);
		materiaDto.setHoras(64);
		materiaDto.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");

		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			 this.materiaService.cadastrar(materiaDto);
		});
		
		assertEquals(HttpStatus.BAD_REQUEST, materiaException.getHttpStatus());
		assertEquals(MensagensConstant.ERRO_ID_INFORMADO.getValor(), materiaException.getMessage());

		Mockito.verify(this.materiaRepository, times(0)).findByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);

	}
	
	@Test
	public void testCadastrarComCodigoExistenteThrowMateriaException() {

		MateriaDto materiaDto = new MateriaDto();
		materiaDto.setCodigo("ILP");
		materiaDto.setFrequencia(1);
		materiaDto.setHoras(64);
		materiaDto.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");
		
		Mockito.when(this.materiaRepository.findByCodigo("ILP")).thenReturn(materiaEntity);

		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			 this.materiaService.cadastrar(materiaDto);
		});
		
		assertEquals(HttpStatus.BAD_REQUEST, materiaException.getHttpStatus());
		assertEquals(MensagensConstant.ERRO_CURSO_NAO_ENCONTRADO.getValor(), materiaException.getMessage());

		Mockito.verify(this.materiaRepository, times(1)).findByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);

	}
	

	/*
	 * 
	 * CENARIOS DE THROW EXCEPTION
	 * 
	 */
	
	@Test
	public void testAtualizarThrowException() {

		MateriaDto materiaDto = new MateriaDto();
		materiaDto.setId(1L);
		materiaDto.setCodigo("ILP");
		materiaDto.setFrequencia(1);
		materiaDto.setHoras(64);
		materiaDto.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");


		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Mockito.when(this.materiaRepository.save(materiaEntity)).thenThrow(IllegalStateException.class);

		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			 this.materiaService.atualizar(materiaDto);
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(MensagensConstant.MENSAGEM_ERRO.getValor(), materiaException.getMessage());

		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);

	}
	
	@Test
	public void testCadastrarThrowException() {
		
		MateriaDto materiaDto = new MateriaDto();
		materiaDto.setCodigo("ILP");
		materiaDto.setFrequencia(1);
		materiaDto.setHoras(64);
		materiaDto.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");
		
		materiaEntity.setId(null);

		Mockito.when(this.materiaRepository.findByCodigo("ILP")).thenReturn(null);
		Mockito.when(this.materiaRepository.save(materiaEntity)).thenThrow(IllegalStateException.class);
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			 this.materiaService.cadastrar(materiaDto);
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(MensagensConstant.MENSAGEM_ERRO.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);
		
		materiaEntity.setId(1L);
		
	}
	
	@Test
	public void testConsultarThrowException() {
		
		Mockito.when(this.materiaRepository.findById(1L)).thenThrow(IllegalStateException.class);
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			 this.materiaService.consultar(1L);
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(MensagensConstant.MENSAGEM_ERRO.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		
	}
	
	@Test
	public void testListarThrowException() {
		
		Mockito.when(this.materiaRepository.findAll()).thenThrow(IllegalStateException.class);
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			 this.materiaService.listar();
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(MensagensConstant.MENSAGEM_ERRO.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findAll();
		
	}
	
	@Test
	public void testListarPorHorarioMinimoThrowException() {
		
		Mockito.when(this.materiaRepository.findByHoraMinima(64)).thenThrow(IllegalStateException.class);
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			 this.materiaService.listarMateriaPorHoraMinima(64);
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(MensagensConstant.MENSAGEM_ERRO.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findByHoraMinima(64);
		
	}
	
	@Test
	public void testListarPorFrequenciaThrowException() {
		
		Mockito.when(this.materiaRepository.findByFreqMinima(1)).thenThrow(IllegalStateException.class);
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			 this.materiaService.listarMateriaPorFreqMinima(1);
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(MensagensConstant.MENSAGEM_ERRO.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findByFreqMinima(1);
		
	}
	
	@Test
	public void testExcluirThrowException() {
		
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Mockito.doThrow(IllegalStateException.class).when(this.materiaRepository).deleteById(1L);
		
		MateriaException materiaException;
		
		materiaException = assertThrows(MateriaException.class, ()->{
			 this.materiaService.excluir(1L);
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(MensagensConstant.MENSAGEM_ERRO.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(1)).deleteById(1L);
		
	}



}