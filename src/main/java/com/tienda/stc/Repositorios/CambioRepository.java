package com.tienda.stc.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tienda.stc.Clases.Cambio;

@Repository
public interface CambioRepository extends JpaRepository<Cambio, Long>{
	
	@Query("SELECT p FROM Cambio p ORDER BY p.id DESC")
	List<Cambio> findAllOrderByIdDesc();

}
