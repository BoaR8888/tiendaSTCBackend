package com.tienda.stc.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.stc.Clases.Usuario;
import com.tienda.stc.Servicios.UsuarioService;



@RestController
@CrossOrigin("*")
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	 @Autowired
	    private UsuarioService usuarioService;
	
	    
	    //Funciones
	    @GetMapping
	    public List<Usuario> listarUsuarios() {
	        return usuarioService.listarUsuarios();
	    }
}
