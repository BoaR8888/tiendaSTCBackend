package com.tienda.stc.Repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tienda.stc.Clases.Usuario;
import com.tienda.stc.Clases.UsuarioRoles;
import com.tienda.stc.Clases.UsuarioRoles_pk;


@Repository
public interface UsuarioRolesRepository extends JpaRepository<UsuarioRoles, UsuarioRoles_pk>{
	
	// Método para encontrar el rol de un usuario por su ID
   // Optional<UsuarioRoles> findByUsuarioId(Long idUsuario);
	  // Usar @Query para realizar una consulta personalizada
	 // Consulta para encontrar los roles de un usuario por su idUsuario
	

	//List<UsuarioRoles> findByUsuarioRoles_pk_IdUsuario(Long idUsuario);

	@Query("SELECT ur FROM UsuarioRoles ur WHERE ur.usuarioRoles_pk.idRol = :idRol")
	List<UsuarioRoles> findByRolId(@Param("idRol") int idRol);
	
	@Query("SELECT ur FROM UsuarioRoles ur WHERE ur.usuario.id = :idUsuario")
	Optional<UsuarioRoles> findByUsuarioId(@Param("idUsuario") Long idUsuario);

	List<UsuarioRoles> findAllByUsuarioIdUsuario(Long idUsuario);


	
	 @Query("SELECT ur.roles.nombre, COUNT(ur.usuario.id) FROM UsuarioRoles ur GROUP BY ur.roles.nombre")
	    List<Object[]> contarUsuariosPorRol();

	/*    
	    @Query("SELECT r.nombre, e.nombre FROM UsuarioRoles ur " +
	    	       "JOIN ur.roles r " +
	    	       "JOIN ur.usuario u " +
	    	       "JOIN Empleado e ON e.usuario.idUsuario = u.idUsuario")
	    	List<Object[]> obtenerEmpleadosAgrupadosPorRol();
*/
	  
/*
	    @Query("SELECT r.nombre, CONCAT(e.nombre, ' ', e.apellidos) FROM UsuarioRoles ur " +
	            "JOIN ur.roles r " +
	            "JOIN ur.usuario u " +
	            "JOIN Empleado e ON e.usuario.idUsuario = u.idUsuario")
	     List<Object[]> obtenerEmpleadosAgrupadosPorRol();
*/
	    @Query("SELECT r.nombre,  r.descripcion, CONCAT(e.nombre, ' ', e.apellidos), u.nombre " +
	    	       "FROM UsuarioRoles ur " +
	    	       "JOIN ur.roles r " +
	    	       "JOIN ur.usuario u " +
	    	       "JOIN Empleado e ON e.usuario.idUsuario = u.idUsuario")
	    	List<Object[]> obtenerEmpleadosAgrupadosPorRol();



}
