package com.tienda.stc.Repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tienda.stc.Clases.Cliente;
import com.tienda.stc.Clases.Empleado;



public interface EmpleadoRepository extends JpaRepository <Empleado, Integer>{
		   // Empleado findByIdUsuario(Long idUsuario);
		    Empleado findByUsuario_IdUsuario(Long idUsuario);
		    
		    List<Empleado> findAllByOrderByIdEmpleadoDesc();
		    
		    Optional<Empleado> findByCi(String ci);
		    

		    
		        @Query("SELECT e FROM Empleado e JOIN e.usuario u JOIN UsuarioRoles ur ON ur.usuario = u JOIN ur.roles r WHERE r.nombre = :rolNombre")
		        List<Empleado> findEmpleadosPorRol(@Param("rolNombre") String rolNombre);


}
