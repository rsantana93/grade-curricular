package com.rss.cliente.escola.gradecurricular.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rss.cliente.escola.gradecurricular.dto.MateriaDto;
import com.rss.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rss.cliente.escola.gradecurricular.exception.MateriaException;
import com.rss.cliente.escola.gradecurricular.repository.IMateriaRepository;

@Service
public class MateriaService implements IMateriaServices {
	
	private static final String MENSAGEM_ERRO = "Erro interno identificado. Contate o suporte";
	private static final String MATERIA_NAO_ENCONTRADA = "Matéria não encontrada";
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
			return true;
		} catch (Exception e) {
			throw new MateriaException(MENSAGEM_ERRO,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<MateriaDto> listar() {
		try {
			return this.mapper.map(this.materiaRepository.findAll(),new TypeToken<List<MateriaDto>>() {}.getType());
		} catch (Exception e) {
			throw new MateriaException(MENSAGEM_ERRO,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public MateriaDto consultar(Long id) {
		try {
			Optional<MateriaEntity> materiaOptional = this.materiaRepository.findById(id);
			if (materiaOptional.isPresent()) {
				return this.mapper.map(materiaOptional.get(),MateriaDto.class);
			}
			throw new MateriaException(MATERIA_NAO_ENCONTRADA, HttpStatus.NOT_FOUND);
		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw new MateriaException(MENSAGEM_ERRO,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
