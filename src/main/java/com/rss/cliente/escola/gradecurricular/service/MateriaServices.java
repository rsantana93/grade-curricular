package com.rss.cliente.escola.gradecurricular.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rss.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rss.cliente.escola.gradecurricular.exception.MateriaException;
import com.rss.cliente.escola.gradecurricular.repository.IMateriaRepository;

@Service
public class MateriaServices implements IMateriaServices{
	
	@Autowired
	private IMateriaRepository materiaRepository;
	
	@Override
	public Boolean atualizar(MateriaEntity materia) {
		try {
		    MateriaEntity materiaEntityAtualizada = this.consultar(materia.getId());
			
			//Atualiza valores
			materiaEntityAtualizada.setFrequencia(materia.getFrequencia());
			materiaEntityAtualizada.setNome(materia.getNome());
			materiaEntityAtualizada.setHoras(materia.getHoras());
			materiaEntityAtualizada.setCodigo(materia.getCodigo());
			this.materiaRepository.save(materia);
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Boolean excluir(Long id) {
		try {
			this.consultar(id);
			materiaRepository.deleteById(id);
			return true;
		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Boolean cadastrar(MateriaEntity materia) {
		try {
			this.materiaRepository.save(materia);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<MateriaEntity> listar() {
		try {
			return this.materiaRepository.findAll();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	
	}

	@Override
	public MateriaEntity consultar(Long id) {
		try {
			Optional<MateriaEntity> materiaOptional = this.materiaRepository.findById(id);
			if(materiaOptional.isPresent()) {
				return materiaOptional.get();
			}
			throw new MateriaException("Matéria não encontrada", HttpStatus.NOT_FOUND);
		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw new MateriaException("Erro interno no servidor. Contate o suporte.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
