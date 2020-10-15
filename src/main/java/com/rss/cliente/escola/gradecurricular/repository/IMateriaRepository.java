package com.rss.cliente.escola.gradecurricular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rss.cliente.escola.gradecurricular.entity.MateriaEntity;

@Repository
public interface IMateriaRepository extends JpaRepository<MateriaEntity, Long> {

}
