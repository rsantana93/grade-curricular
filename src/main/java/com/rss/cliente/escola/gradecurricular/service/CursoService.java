package com.rss.cliente.escola.gradecurricular.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rss.cliente.escola.gradecurricular.contante.MensagensConstant;
import com.rss.cliente.escola.gradecurricular.dto.CursoRequestDto;
import com.rss.cliente.escola.gradecurricular.dto.CursoResponseDto;
import com.rss.cliente.escola.gradecurricular.entity.CursoEntity;
import com.rss.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rss.cliente.escola.gradecurricular.exception.CursoException;
import com.rss.cliente.escola.gradecurricular.exception.MateriaException;
import com.rss.cliente.escola.gradecurricular.repository.ICursoRepository;
import com.rss.cliente.escola.gradecurricular.repository.IMateriaRepository;

@Service
@CacheConfig(cacheNames = "curso")
public class CursoService implements ICursoService {
	
	private ICursoRepository cursoRepository;
	private IMateriaRepository materiaRepository;
	private ModelMapper mapper;
	
	@Autowired
	public CursoService(ICursoRepository cursoRepository,IMateriaRepository materiaRepository) {
		this.mapper = new ModelMapper();
		this.cursoRepository = cursoRepository;
		this.materiaRepository = materiaRepository;
	}

	@Override
	public Boolean atualizar(CursoRequestDto curso) {
		try {
			this.consultar(curso.getId());
			CursoEntity cursoEntityAtualizada = this.mapper.map(curso,CursoEntity.class);
			this.cursoRepository.save(cursoEntityAtualizada);
			
			return Boolean.TRUE;
		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public Boolean excluir(Long id) {
		try {
			this.consultar(id);
			cursoRepository.deleteById(id);
			return Boolean.TRUE;
		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Boolean cadastrar(CursoRequestDto curso) {
		try {
			
			/*
			 * O id não pode ser informado no cadastro
			 */

			if (curso.getId() != null) {
				throw new CursoException(MensagensConstant.ERRO_ID_INFORMADO.getValor(), HttpStatus.BAD_REQUEST);
			}
			
			/*
			 * Não permite fazer cadastro de cursos com mesmos códigos.
			 */
			if (this.cursoRepository.findCursoByCodigo(curso.getCodCurso()) != null) {
				throw new CursoException(MensagensConstant.ERRO_CURSO_CADASTRADO_ANTERIORMENTE.getValor(), HttpStatus.BAD_REQUEST);
			}
			
			List<MateriaEntity> listMateriaEntity = new ArrayList<>();
			
			if(!curso.getMaterias().isEmpty()) {
				
				curso.getMaterias().forEach(materia->{
					if(this.materiaRepository.findById(materia).isPresent())
						listMateriaEntity.add(this.materiaRepository.findById(materia).get());
				});
			}
			
			CursoEntity cursoEntity = new CursoEntity();

			cursoEntity.setCodigo(curso.getCodCurso());
			cursoEntity.setNome(curso.getNome());
			cursoEntity.setMaterias(listMateriaEntity);
			
			this.cursoRepository.save(cursoEntity);
			
			return Boolean.TRUE;
		}catch (CursoException c) {
			throw c;
		}
		catch (Exception e) {
			throw new CursoException(MensagensConstant.MENSAGEM_ERRO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@CachePut(unless = "#result.size()<3")
	public List<CursoResponseDto> listar() {
		try {
			List<CursoResponseDto> materiaDto = this.mapper.map(this.cursoRepository.findAll(),
					new TypeToken<List<CursoResponseDto>>() {
					}.getType());

			return materiaDto;
		} catch (Exception e) {
			throw new MateriaException(MensagensConstant.MENSAGEM_ERRO.getValor(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	@CachePut(key = "#id")
	public CursoResponseDto consultar(Long id) {
		try {
			Optional<CursoEntity> cursoOtional = this.cursoRepository.findById(id);
			if (cursoOtional.isPresent()) {
				return this.mapper.map(cursoOtional.get(),CursoResponseDto.class);
			}
			throw new MateriaException(MensagensConstant.CURSO_NAO_ENCONTRADA.getValor(), HttpStatus.NOT_FOUND);
		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw new MateriaException(MensagensConstant.MENSAGEM_ERRO.getValor(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CachePut(key = "#codCurso")
	@Override
	public CursoRequestDto consultarPorCodigo(String codCurso) {

		try {
			CursoRequestDto curso = this.mapper.map(this.cursoRepository.findCursoByCodigo(codCurso),CursoRequestDto.class);

			if (curso == null) {
				throw new CursoException(MensagensConstant.ERRO_CURSO_NAO_ENCONTRADO.getValor(), HttpStatus.NOT_FOUND);
			}
			return curso;

		} catch (CursoException c) {
			throw c;
		} catch (Exception e) {
			throw new CursoException(MensagensConstant.MENSAGEM_ERRO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
