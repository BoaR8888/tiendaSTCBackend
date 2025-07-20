package com.tienda.stc.Repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tienda.stc.Clases.Producto;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
	
	@Query("SELECT p FROM Producto p ORDER BY p.id DESC")
	List<Producto> findAllProductosOrdenados();

	
	   List<Producto> findByNombreContainingIgnoreCase(String nombre);
	   // Método para buscar un producto por su ID
	   Optional<Producto> findById(Long id);
	   
	   List<Producto> findByStockLessThanEqual(int cantidad);


	
}

