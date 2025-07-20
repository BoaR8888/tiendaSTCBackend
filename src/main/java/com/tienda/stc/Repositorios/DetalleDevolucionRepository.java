package com.tienda.stc.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.stc.Clases.DetalleDevolucion;

@Repository
public interface DetalleDevolucionRepository extends JpaRepository<DetalleDevolucion, Long> {

    // Buscar todos los detalles de una devolución
    List<DetalleDevolucion> findByDevolucionIdDevolucion(Long idDevolucion);
}
