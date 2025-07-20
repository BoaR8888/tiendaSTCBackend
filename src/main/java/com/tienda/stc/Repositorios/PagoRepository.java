package com.tienda.stc.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.stc.Clases.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long>{
	List<Pago> findAllByOrderByIdPagoDesc();

}
