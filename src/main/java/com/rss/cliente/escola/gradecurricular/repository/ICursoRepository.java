package com.rss.cliente.escola.gradecurricular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rss.cliente.escola.gradecurricular.entity.CursoEntity;

@Repository
public interface ICursoRepository extends JpaRepository<CursoEntity, Long>{
	@Query("select m from CursoEntity m where m.codigo = :codigo")
	public CursoEntity findCursoByCodigo(@Param("codigo") String codigo);
}
