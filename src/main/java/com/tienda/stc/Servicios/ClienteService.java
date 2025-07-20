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
public class ClienteService {
	//ATRIBUTOS
		@Autowired
		private ClienteRepository clienteRepository;
		
		@Autowired
		private UsuarioRepository usuarioRepository;
		
		  @Autowired
		    private EmpleadoRepository empleadoRepository;

		    @Autowired
		    private ProveedorRepository proveedorRepository;
		    
		    public Map<String, Object> buscarPorCi(String ci) {
		        Map<String, Object> datos = new HashMap<>();

		        Optional<Cliente> cliente = clienteRepository.findByNit(ci);
		        Optional<Empleado> empleado = empleadoRepository.findByCi(ci);
		        Optional<Proveedor> proveedor = proveedorRepository.findByCi(ci);

		        if (cliente.isPresent()) {
		            Cliente c = cliente.get();
		            datos.put("nombre", c.getNombre());
		            datos.put("apellidos", c.getApellidos());
		            datos.put("sexo", c.getSexo());
		            datos.put("email", c.getEmail());
		            datos.put("fuente", "cliente");
		            datos.put("yaRegistrado", true); // 👈 Esto sirve para el frontend
		        } else if (empleado.isPresent()) {
		            Empleado e = empleado.get();
		            datos.put("nombre", e.getNombre());
		            datos.put("apellidos", e.getApellidos());
		            datos.put("sexo", e.getSexo());
		            datos.put("email", e.getEmail());
		            datos.put("fuente", "empleado");
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

		    /*
		    
		    public Map<String, Object> buscarPorCi(String ci) {
		        Map<String, Object> datos = new HashMap<>();

		        Optional<Cliente> cliente = clienteRepository.findByNit(ci);
		        Optional<Empleado> empleado = empleadoRepository.findByCi(ci); // <- Esto debe existir

		        if (cliente.isPresent()) {
		            Cliente c = cliente.get();
		            datos.put("nombre", c.getNombre());
		            datos.put("apellidos", c.getApellidos());
		            datos.put("fuente", "cliente");
		        } else if (empleado.isPresent()) {
		            Empleado e = empleado.get();
		            datos.put("nombre", e.getNombre());
		            datos.put("apellidos", e.getApellidos());
		            datos.put("fuente", "empleado");
		        }

		        return datos;
		    }*/
		
		//FUNCIONES
	/*	public List<Cliente> listarClientes(){
			return clienteRepository.findAll();
		}*/
		public List<Cliente> listarClientes() {
		    return clienteRepository.findAllByOrderByIdClienteDesc();
		}

		

	    public Cliente agregarClienteConUsuario(Cliente cliente) {
	        // Crear el objeto Usuario
	      //  Usuario usuario = new Usuario();
	     //   usuario.setNombre(nombreUsuario); // El nombre de usuario proviene del frontend
	      //  usuario.setPassword(passwordEncoder.encode(password)); // Encriptar la contraseña
	     //   usuario.setPassword(password);
	        // Guardar el Usuario en la base de datos
	    //    usuarioRepository.save(usuario);

	        // Asignar el Usuario al Empleado
	    //    cliente.setUsuario(usuario);

	        // Guardar el Empleado en la base de datos
	        return clienteRepository.save(cliente);
	    }
	    
	    public Optional<Cliente> obtenerCliente(String nit) {
	        return clienteRepository.findByNit(nit);
	    }
	    
	    public Optional<Cliente> obtenerEmpleado(int id) {
	        return clienteRepository.findById(id);
	    }
	    
	    public Cliente obtenerClientePorId(int id) {
	        return clienteRepository.findById(id).orElse(null);
	    }
	    
	    public Cliente save(Cliente cliente) {
	        return clienteRepository.save(cliente);
	    }
	    
	    public Cliente obtenerPorIdUsuario(Long idUsuario) {
	        return clienteRepository.findByUsuario_IdUsuario(idUsuario);
	    }
	    
	    public List<Cliente> buscarClientesPorNombreOApellido(String filtro) {
	        return clienteRepository.buscarPorNombreOApellido(filtro);
	    }

}
