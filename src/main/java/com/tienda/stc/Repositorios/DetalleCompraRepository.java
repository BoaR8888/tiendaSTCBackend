package com.tienda.stc.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.stc.Clases.DetalleCompra;

public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long> {
	
}
