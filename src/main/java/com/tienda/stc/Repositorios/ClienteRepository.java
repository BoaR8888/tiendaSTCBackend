package com.tienda.stc.Repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tienda.stc.Clases.Cliente;
import com.tienda.stc.Clases.Venta;

public interface ClienteRepository extends JpaRepository <Cliente, Integer>{
	Cliente findByUsuario_IdUsuario(Long idUsuario);
	
	  @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR LOWER(c.apellidos) LIKE LOWER(CONCAT('%', :filtro, '%'))")
	    List<Cliente> buscarPorNombreOApellido(@Param("filtro") String filtro);
	
	  List<Cliente> findAllByOrderByIdClienteDesc();
	  
	  Optional<Cliente> findByNit(String nit);


}
