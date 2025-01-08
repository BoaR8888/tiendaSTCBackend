package com.tienda.stc.Servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.stc.Clases.*;
import com.tienda.stc.Repositorios.*;

@Service
public class RolesService {
	
	 @Autowired
	    private RolesRepository rolesRepository;

	 //FUNCIONES
	public List<Roles> listarRoles(){
		return rolesRepository.findAll();
	}
}
