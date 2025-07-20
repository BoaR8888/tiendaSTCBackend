package com.tienda.stc.Repositorios;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tienda.stc.Clases.Cliente;
import com.tienda.stc.Clases.Proveedor;



@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
	 // Método para contar nombres de proveedores
	/*
    @Query("SELECT new om.STC.Tesis.Reportes.ReporteProveedor(p.nombre, COUNT(p)) " +
           "FROM Proveedor p " +
           "GROUP BY p.nombre")
    List<ReporteProveedor> contarNombres();
    
    */
	  @Query("SELECT p.ciudad, COUNT(p) FROM Proveedor p GROUP BY p.ciudad")
	    List<Object[]> contarNombres();
	    
	    
	    List<Proveedor> findAllByOrderByIdProveedorDesc();
		  Optional<Proveedor> findByCi(String ci);
}
