package com.tienda.stc.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.stc.Clases.DetalleCambio;

@Repository
public interface DetalleCambioRepository extends JpaRepository<DetalleCambio, Long> {

}
