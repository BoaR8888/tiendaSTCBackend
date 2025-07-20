package com.tienda.stc.Controladores;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.stc.Clases.UsuarioRoles;
import com.tienda.stc.Clases.UsuarioRoles_pk;
import com.tienda.stc.Servicios.UsuarioRolesService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/usuarioroles")
public class UsuarioRolesController {
	 @Autowired
	 private UsuarioRolesService usuarioRolesService;
	
		 //FUNCIONES
		 
	    public UsuarioRolesController(UsuarioRolesService usuarioRolesService) {
	        this.usuarioRolesService = usuarioRolesService;
	    }

	    @PutMapping("/asignar")
	    public ResponseEntity<String> asignarRol(@RequestBody UsuarioRoles_pk usuarioRolesPk) {
	        boolean asignado = usuarioRolesService.asignarRol(usuarioRolesPk);
	        if (asignado) {
	            return ResponseEntity.ok("Rol asignado correctamente.");
	        }
	        return ResponseEntity.badRequest().body("Usuario o Rol no encontrado.");
	    }

	    @GetMapping("/listar")
	    public ResponseEntity<List<UsuarioRoles>> obtenerUsuariosRoles() {
	        List<UsuarioRoles> lista = usuarioRolesService.obtenerUsuariosRoles();
	        return ResponseEntity.ok(lista);
	    }
	 

	    @GetMapping
	    public List<UsuarioRoles> listarUsuarioRoles() {
	        return usuarioRolesService.listaUsuarioRoles();
	    }
	    
	    @GetMapping("/contarUsuariosPorRol")
	    public Map<String, Long> contarUsuariosPorRol() {
	        return usuarioRolesService.obtenerUsuariosPorRol();
	    }
	    
	    @GetMapping("/empleados-por-rol")
	    public ResponseEntity<List<Map<String, Object>>> getEmpleadosPorRolAgrupado() {
	        return ResponseEntity.ok(usuarioRolesService.obtenerEmpleadosPorRolAgrupado());
	    }
/*
	    @GetMapping("/usuarios-por-rol-detalle")
	    public List<Map<String, String>> getUsuariosPorRolDetalle() {
	        List<Object[]> resultados = usuarioRolesRepository.obtenerEmpleadosAgrupadosPorRol();
	        List<Map<String, String>> lista = new ArrayList<>();

	        for (Object[] fila : resultados) {
	            Map<String, String> map = new HashMap<>();
	            map.put("rol", (String) fila[0]);
	            map.put("nombreCompleto", (String) fila[1]);
	            map.put("nombreUsuario", (String) fila[2]);
	            lista.add(map);
	        }

	        return lista;
	    }
*/
	    /*
	    @GetMapping("/empleados-agrupados-por-rol")
	    public List<Map<String, String>> getEmpleadosAgrupadosPorRol() {
	        return usuarioRolesService.obtenerEmpleadosAgrupadosPorRol();
	    }
	    */
	    @GetMapping("/agrupados-por-rol")
	    public ResponseEntity<List<Object[]>> obtenerEmpleadosAgrupadosPorRol() {
	        List<Object[]> empleadosAgrupados = usuarioRolesService.obtenerEmpleadosAgrupadosPorRol();
	        return ResponseEntity.ok(empleadosAgrupados);
	    }
	    
}
