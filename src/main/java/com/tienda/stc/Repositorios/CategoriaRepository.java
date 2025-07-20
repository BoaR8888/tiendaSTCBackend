package com.tienda.stc.Repositorios;

import org.springframework.stereotype.Repository;

import com.tienda.stc.Clases.Categoria;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	/*
	  @Query("SELECT p.ciudad, COUNT(p) FROM Proveedor p GROUP BY p.ciudad")
	    List<Object[]> contarNombres();
	    */
	List<Categoria> findAllByOrderByIdCategoriaDesc();
}