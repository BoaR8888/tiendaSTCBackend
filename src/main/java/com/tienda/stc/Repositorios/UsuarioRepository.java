package com.tienda.stc.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.stc.Clases.*;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


	Usuario findByNombre(String nombreUsuario);
	
	
	

}
