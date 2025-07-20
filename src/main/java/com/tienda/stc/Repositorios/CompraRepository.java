package com.tienda.stc.Repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tienda.stc.Clases.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {
	 Optional<Compra> findTopByOrderByIdCompraDesc();
	 
	 @Query("SELECT DISTINCT c FROM Compra c " +
		       "JOIN c.proveedor p " +
		       "LEFT JOIN DetalleCompra dc ON dc.compra.id = c.id " +
		       "LEFT JOIN dc.producto pr " +
		       "WHERE (:filtro IS NULL OR :filtro = '' OR " +
		       "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
		       "LOWER(pr.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
		       "LOWER(pr.modelo) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
		       "LOWER(pr.marca) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +  
		       "LOWER(c.reciboCompra) LIKE LOWER(CONCAT('%', :filtro, '%'))) " +
		       "AND (:estado IS NULL OR c.estadoCompra = :estado) " +
		       "ORDER BY c.reciboCompra DESC")
		List<Compra> buscarPorFiltroYEstado(@Param("filtro") String filtro, @Param("estado") Integer estado);

	/* 
	 @Query("SELECT DISTINCT c FROM Compra c " +
		       "JOIN c.proveedor p " +
		       "LEFT JOIN DetalleCompra dc ON dc.compra.id = c.id " +
		       "LEFT JOIN dc.producto pr " +
		       "WHERE (:filtro IS NULL OR " +
		       "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
		       "LOWER(pr.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
		       "LOWER(pr.modelo) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
		       "LOWER(pr.marca) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +  
		       "LOWER(c.reciboCompra) LIKE LOWER(CONCAT('%', :filtro, '%'))) " +
		       "AND (:estado IS NULL OR c.estadoCompra = :estado)" + 
		       "ORDER BY c.reciboCompra DESC")
		List<Compra> buscarPorFiltroYEstado(@Param("filtro") String filtro, @Param("estado") Integer estado);
*/
	 
	 
	 
	 
	 
	 
	 @Query("SELECT c FROM Compra c WHERE LOWER(c.reciboCompra) LIKE LOWER(CONCAT('%', :filtro, '%'))")
	 List<Compra> buscarSoloPorRecibo(@Param("filtro") String filtro);

	 List<Compra> findAllByOrderByIdCompraDesc();
	//List<Compra> findByReciboCompraContainingIgnoreCase(String reciboCompra);
}
