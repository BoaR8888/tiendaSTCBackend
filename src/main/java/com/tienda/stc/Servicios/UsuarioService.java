package com.tienda.stc.Servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.stc.Clases.*;
import com.tienda.stc.Repositorios.*;

@Service
public class UsuarioService {
	//Atributos
	 @Autowired
	    private UsuarioRepository usuarioRepository;
	 
	 //Funciones
	  // Obtener todos los usuarios
	    public List<Usuario> listarUsuarios() {
	        return usuarioRepository.findAll();
	    }

}
