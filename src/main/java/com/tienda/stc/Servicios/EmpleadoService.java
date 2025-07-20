package com.tienda.stc.Servicios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.stc.Clases.Cliente;
import com.tienda.stc.Clases.Empleado;
import com.tienda.stc.Clases.Proveedor;
import com.tienda.stc.Clases.Usuario;
import com.tienda.stc.Repositorios.ClienteRepository;
import com.tienda.stc.Repositorios.EmpleadoRepository;
import com.tienda.stc.Repositorios.ProveedorRepository;
import com.tienda.stc.Repositorios.UsuarioRepository;

@Service
public class EmpleadoService {
	//ATRIBUTOS
			@Autowired
			private EmpleadoRepository empleadoRepository;
			@Autowired
			private UsuarioRepository usuarioRepository;
			
			@Autowired
			private ClienteRepository clienteRepository;
			
			    @Autowired
			    private ProveedorRepository proveedorRepository;
			// @Autowired
		 //   private BCryptPasswordEncoder passwordEncoder;  // Para encriptar la contraseña
			
			
			//FUNCIONES
			public List<Empleado> listarEmpleados(){
				//return empleadoRepository.findAll();
				return empleadoRepository.findAllByOrderByIdEmpleadoDesc();
			}
			
			  public Map<String, Object> buscarPorCi(String ci) {
			        Map<String, Object> datos = new HashMap<>();

			        Optional<Empleado> empleado = empleadoRepository.findByCi(ci);
			        Optional<Cliente> cliente = clienteRepository.findByNit(ci);
			        Optional<Proveedor> proveedor = proveedorRepository.findByCi(ci);

			        if (empleado.isPresent()) {
			            Empleado e = empleado.get();
			            datos.put("nombre", e.getNombre());
			            datos.put("apellidos", e.getApellidos());
			            datos.put("sexo", e.getSexo());
			            datos.put("email", e.getEmail());
			            datos.put("fuente", "empleado");
			            datos.put("yaRegistrado", true);
			        } else if (cliente.isPresent()) {
			            Cliente c = cliente.get();
			            datos.put("nombre", c.getNombre());
			            datos.put("apellidos", c.getApellidos());
			            datos.put("sexo", c.getSexo());
			            datos.put("email", c.getEmail());
			            datos.put("fuente", "cliente");
			            datos.put("yaRegistrado", false);
			        } else if (proveedor.isPresent()) {
			            Proveedor p = proveedor.get();
			            datos.put("nombre", p.getNombre());
			            datos.put("apellidos", p.getApellidos());
			            datos.put("fuente", "proveedor");
			            datos.put("yaRegistrado", false);
			        } else {
			            datos.put("fuente", "ninguna");
			            datos.put("yaRegistrado", false);
			        }

			        return datos;
			    }
			 

		    public Empleado agregarEmpleadoConUsuario(Empleado empleado, String nombreUsuario, String password) {
		        // Crear el objeto Usuario
		        Usuario usuario = new Usuario();
		        usuario.setNombre(nombreUsuario); // El nombre de usuario proviene del frontend
		      //  usuario.setPassword(passwordEncoder.encode(password)); // Encriptar la contraseña
		        usuario.setPassword(password);
		        // Guardar el Usuario en la base de datos
		        usuarioRepository.save(usuario);

		        // Asignar el Usuario al Empleado
		        empleado.setUsuario(usuario);

		        // Guardar el Empleado en la base de datos
		        return empleadoRepository.save(empleado);
		    }
		    
		    public Optional<Empleado> obtenerEmpleado(int id) {
		        return empleadoRepository.findById(id);
		    }
		    
		    public Optional<Empleado> obtenerEmpleado(String ci) {
		        return empleadoRepository.findByCi(ci);
		    }
		    
		    public Empleado save(Empleado empleado) {
		        return empleadoRepository.save(empleado);
		    }
		    

		     

		        public Empleado obtenerPorIdUsuario(Long idUsuario) {
		            return empleadoRepository.findByUsuario_IdUsuario(idUsuario);
		        }
		  
			/*
		    public Empleado agregarEmpleadoConUsuario(Empleado empleado, String nombreUsuario, String password) {
		        // Crear usuario con los datos recibidos
		        Usuario usuario = new Usuario();
		        usuario.setNombre(nombreUsuario);
		        usuario.setPassword(password); // Se recomienda encriptar antes de guardar
		        
		        usuarioRepository.save(usuario);

		        // Asignar usuario al empleado
		        empleado.setUsuario(usuario);
		        
		        // Guardar el empleado con su usuario
		        return empleadoRepository.save(empleado);
		    }
		    */
		        
		        public List<Empleado> obtenerEmpleadosTecnicos() {
		            return empleadoRepository.findEmpleadosPorRol("TECNICO");
		        }

}
