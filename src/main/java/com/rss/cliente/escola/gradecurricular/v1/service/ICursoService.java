package com.rss.cliente.escola.gradecurricular.v1.service;

import java.util.List;

import com.rss.cliente.escola.gradecurricular.v1.dto.CursoRequestDto;
import com.rss.cliente.escola.gradecurricular.v1.dto.CursoResponseDto;

public interface ICursoService {
	public Boolean atualizar(final CursoRequestDto curso);
	public Boolean excluir(final Long id);
	public Boolean cadastrar(final CursoRequestDto curso);
	public List<CursoResponseDto> listar();
	public CursoResponseDto consultar(final Long id);
	public CursoResponseDto consultarPorCodigo(String codCurso);

}
