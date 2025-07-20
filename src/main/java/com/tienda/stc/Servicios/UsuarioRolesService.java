package com.tienda.stc.Servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.stc.Clases.Roles;
import com.tienda.stc.Clases.Usuario;
import com.tienda.stc.Clases.UsuarioRoles;
import com.tienda.stc.Clases.UsuarioRoles_pk;
import com.tienda.stc.Repositorios.RolesRepository;
import com.tienda.stc.Repositorios.UsuarioRepository;
import com.tienda.stc.Repositorios.UsuarioRolesRepository;

@Service
public class UsuarioRolesService {
	
	 @Autowired
	 private UsuarioRolesRepository usuarioRolesRepository;

	 
	
	    private final UsuarioRepository usuarioRepository;
	    private final RolesRepository rolesRepository;

	    
		 
		 //FUNCIONES
		 public List<UsuarioRoles> listaUsuarioRoles(){
			 return usuarioRolesRepository.findAll();
		 }
		 
	    public UsuarioRolesService(UsuarioRolesRepository usuarioRolesRepository, UsuarioRepository usuarioRepository, RolesRepository rolesRepository) {
	        this.usuarioRolesRepository = usuarioRolesRepository;
	        this.usuarioRepository = usuarioRepository;
	        this.rolesRepository = rolesRepository;
	    }


	    public boolean asignarRol(UsuarioRoles_pk usuarioRolesPk) {
	        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioRolesPk.getIdUsuario());
	        Optional<Roles> rolOpt = rolesRepository.findById(usuarioRolesPk.getIdRol());

	        if (usuarioOpt.isPresent() && rolOpt.isPresent()) {
	            Usuario usuario = usuarioOpt.get();
	            Roles rol = rolOpt.get();

	            // ✅ Aquí inicializamos la clave primaria correctamente
	            UsuarioRoles usuarioRol = new UsuarioRoles(usuario, rol);
	            usuarioRolesRepository.save(usuarioRol);

	            return true;
	        }
	        return false;
	    }

	    public List<UsuarioRoles> obtenerUsuariosRoles() {
	        return usuarioRolesRepository.findAll();
	    }
	    
	    public Map<String, Long> obtenerUsuariosPorRol() {
	        List<Object[]> resultados = usuarioRolesRepository.contarUsuariosPorRol();
	        Map<String, Long> usuariosPorRol = new HashMap<>();

	        for (Object[] fila : resultados) {
	            String nombreRol = (String) fila[0];
	            Long cantidadUsuarios = (Long) fila[1];
	            usuariosPorRol.put(nombreRol, cantidadUsuarios);
	        }
	        return usuariosPorRol;
	    }
	    
	    public List<Map<String, Object>> obtenerEmpleadosPorRolAgrupado() {
	        List<Object[]> resultados = usuarioRolesRepository.obtenerEmpleadosAgrupadosPorRol();
	        Map<String, List<String>> agrupado = new LinkedHashMap<>();

	        for (Object[] fila : resultados) {
	            String nombreRol = (String) fila[0];
	            String nombreEmpleado = (String) fila[1];

	            agrupado.computeIfAbsent(nombreRol, k -> new ArrayList<>()).add(nombreEmpleado);
	        }

	        // Convertimos a lista de mapas para que sea más amigable en el frontend
	        List<Map<String, Object>> listaFinal = new ArrayList<>();
	        for (Map.Entry<String, List<String>> entry : agrupado.entrySet()) {
	            Map<String, Object> map = new HashMap<>();
	            map.put("rol", entry.getKey());
	            map.put("empleados", entry.getValue());
	            listaFinal.add(map);
	        }

	        return listaFinal;
	    }

	  /*  
	    public List<Map<String, String>> obtenerEmpleadosAgrupadosPorRol() {
	        List<Object[]> resultados = usuarioRolesRepository.obtenerEmpleadosAgrupadosPorRol();
	        List<Map<String, String>> lista = new ArrayList<>();

	        for (Object[] fila : resultados) {
	            Map<String, String> map = new HashMap<>();
	            map.put("rol", (String) fila[0]);
	            map.put("nombreEmpleado", (String) fila[1] + " " + fila[2]); // Concatenamos nombre y apellidos
	            lista.add(map);
	        }

	        return lista;
	    }
*/
	    
	    public List<Object[]> obtenerEmpleadosAgrupadosPorRol() {
	        return usuarioRolesRepository.obtenerEmpleadosAgrupadosPorRol();
	    }
	/* public Optional<UsuarioRoles> obtenerPorId(UsuarioRoles_pk pk) {
	        return Optional.empty();
	 }
	 */
}
