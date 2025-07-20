package com.tienda.stc.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.stc.Clases.Devolucion;

@Repository
public interface DevolucionRepository extends JpaRepository<Devolucion, Long> {

    // Buscar devoluciones por estado
    List<Devolucion> findByEstadoDevolucion(int estadoDevolucion);

    // Buscar devoluciones asociadas a una compra
    List<Devolucion> findByCompraIdCompra(Long idCompra);

    // Buscar devoluciones asociadas a una venta
    List<Devolucion> findByVentaId(Long idVenta);
    
    List<Devolucion> findAllByOrderByIdDevolucionDesc();

}