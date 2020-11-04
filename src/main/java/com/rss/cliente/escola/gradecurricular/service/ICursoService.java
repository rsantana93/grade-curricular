package com.rss.cliente.escola.gradecurricular.service;

import java.util.List;

import com.rss.cliente.escola.gradecurricular.dto.CursoRequestDto;
import com.rss.cliente.escola.gradecurricular.dto.CursoResponseDto;

public interface ICursoService {
	public Boolean atualizar(final CursoRequestDto curso);
	public Boolean excluir(final Long id);
	public Boolean cadastrar(final CursoRequestDto curso);
	public List<CursoResponseDto> listar();
	public CursoResponseDto consultar(final Long id);
	public CursoRequestDto consultarPorCodigo(String codCurso);

}
