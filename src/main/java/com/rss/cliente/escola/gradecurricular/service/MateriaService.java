package com.rss.cliente.escola.gradecurricular.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rss.cliente.escola.gradecurricular.contante.MensagensConstant;
import com.rss.cliente.escola.gradecurricular.controller.MateriaController;
import com.rss.cliente.escola.gradecurricular.dto.MateriaDto;
import com.rss.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rss.cliente.escola.gradecurricular.exception.MateriaException;
import com.rss.cliente.escola.gradecurricular.repository.IMateriaRepository;

@Service
@CacheConfig(cacheNames = "materia")
public class MateriaService implements IMateriaService {
	
	private IMateriaRepository materiaRepository;
	private ModelMapper mapper;
	
	@Autowired
	public MateriaService(IMateriaRepository materiaRepository) {
		this.mapper = new ModelMapper();
		this.materiaRepository = materiaRepository;
	}

	@Override
	public Boolean atualizar(MateriaDto materia) {
		try {
			this.consultar(materia.getId());
			MateriaEntity materiaEntityAtualizada = this.mapper.map(materia,MateriaEntity.class);
			this.materiaRepository.save(materiaEntityAtualizada);
			
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
			materiaRepository.deleteById(id);
			return Boolean.TRUE;
		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Boolean cadastrar(MateriaDto materia) {
		try {
			MateriaEntity entity = this.mapper.map(materia, MateriaEntity.class);

			this.materiaRepository.save(entity);
			return Boolean.TRUE;
		} catch (Exception e) {
			throw new MateriaException(MensagensConstant.MENSAGEM_ERRO.getValor(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@CachePut(unless = "#result.size()<3")
	public List<MateriaDto> listar() {
		try {
			List<MateriaDto> materiaDto = this.mapper.map(this.materiaRepository.findAll(),
					new TypeToken<List<MateriaDto>>() {
					}.getType());

			materiaDto.forEach(materia -> {
				materia.add(WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).consultaMateria(materia.getId()))
						.withSelfRel());
			});

			return materiaDto;
		} catch (Exception e) {
			throw new MateriaException(MensagensConstant.MENSAGEM_ERRO.getValor(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	@CachePut(key = "#id")
	public MateriaDto consultar(Long id) {
		try {
			Optional<MateriaEntity> materiaOptional = this.materiaRepository.findById(id);
			if (materiaOptional.isPresent()) {
				return this.mapper.map(materiaOptional.get(),MateriaDto.class);
			}
			throw new MateriaException(MensagensConstant.MATERIA_NAO_ENCONTRADA.getValor(), HttpStatus.NOT_FOUND);
		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw new MateriaException(MensagensConstant.MENSAGEM_ERRO.getValor(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<MateriaDto> listarMateriaPorHoraMinima(int hora) {
		try {
			List<MateriaDto> materiaDto = this.mapper.map(this.materiaRepository.findByHoraMinima(hora),
					new TypeToken<List<MateriaDto>>() {
					}.getType());

			return materiaDto;
		} catch (Exception e) {
			throw new MateriaException(MensagensConstant.MENSAGEM_ERRO.getValor(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public List<MateriaDto> listarMateriaPorFreqMinima(int hora) {
		try {
			List<MateriaDto> materiaDto = this.mapper.map(this.materiaRepository.findByFreqMinima(hora),
					new TypeToken<List<MateriaDto>>() {
					}.getType());

			return materiaDto;
		} catch (Exception e) {
			throw new MateriaException(MensagensConstant.MENSAGEM_ERRO.getValor(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
