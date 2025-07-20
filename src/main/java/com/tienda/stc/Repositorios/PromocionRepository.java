package com.tienda.stc.Repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.stc.Clases.Promocion;


public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    Optional<Promocion> findByProductoIdAndEstadoTrue(Long productoId);
    
    List<Promocion> findByEstadoTrue();
   // Optional<Promocion> findByProductoIdAndActivaTrue(Long productoId);
    List<Promocion> findAllByOrderByIdPromocionDesc();

}