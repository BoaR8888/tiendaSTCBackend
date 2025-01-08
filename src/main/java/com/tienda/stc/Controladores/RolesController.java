package com.tienda.stc.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.stc.Clases.*;
import com.tienda.stc.Servicios.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/roles")
public class RolesController {
	 @Autowired
	    private RolesService rolesService;
	 
	 //FUNCIONES
	 @GetMapping
	 public List<Roles> listarRoles(){
		 return rolesService.listarRoles();
	 }

}
