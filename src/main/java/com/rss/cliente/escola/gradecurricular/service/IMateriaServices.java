package com.rss.cliente.escola.gradecurricular.service;

import java.util.List;

import com.rss.cliente.escola.gradecurricular.entity.MateriaEntity;

public interface IMateriaServices {
	public Boolean atualizar(final MateriaEntity materia);
	public Boolean excluir(final Long id);
	public Boolean cadastrar(final MateriaEntity materia);
	public List<MateriaEntity> listar();
	public MateriaEntity consultar(final Long id);
}
