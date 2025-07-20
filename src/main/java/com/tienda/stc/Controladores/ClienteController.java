package com.tienda.stc.Controladores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.stc.Clases.Cliente;
import com.tienda.stc.Clases.Empleado;
import com.tienda.stc.Clases.Proveedor;
import com.tienda.stc.Servicios.ClienteService;
import com.tienda.stc.Servicios.EmpleadoService;
import com.tienda.stc.Servicios.ProveedorService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/clientes")
public class ClienteController {
	
	 @Autowired
	 private ClienteService clienteService;
	 
	 @Autowired
	 private EmpleadoService empleadoService;
	 
	 @Autowired
	 private ProveedorService proveedorService;
	 

	 @GetMapping("/buscar-por-ci/{ci}")
	    public ResponseEntity<Map<String, Object>> buscarPorCi(@PathVariable String ci) {
		 System.out.println("el si es:: "+  ci);
	        Map<String, Object> datos = clienteService.buscarPorCi(ci);
	        if (datos.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.ok(datos);
	    }
	 
	 //FUNCIONES
	 @GetMapping
	 public List<Cliente> listarClientes(){
		 return clienteService.listarClientes();
	 }
	 
	    @PostMapping("/agregar")
	    public Cliente agregarEmpleado(@RequestBody Map<String, Object> request) throws ParseException {
	    	          
	        // Obtener los datos del empleado y del usuario
	    //    String nombreUsuario = (String) request.get("nombreUsuario");
	    //    String password = (String) request.get("password");

	        // Crear el empleado con los datos
	        Cliente cliente = new Cliente();
	        cliente.setNit((String) request.get("nit"));
	        cliente.setNombre((String) request.get("nombre"));
	        cliente.setApellidos((String) request.get("apellidos"));
	        cliente.setSexo((String) request.get("sexo"));
	        cliente.setEmail((String) request.get("email"));
	        cliente.setEstado((Integer) request.get("estado"));

	        // Llamar al servicio para agregar el empleado con su usuario
	        return clienteService.agregarClienteConUsuario(cliente);
	    }
	    
	    
	    
	    @PutMapping("/{id}")
	    public ResponseEntity<Cliente> updateEmpleado(@PathVariable int id, @RequestBody Cliente clienteDetails) {
	        Optional<Cliente> optionalCliente = clienteService.obtenerEmpleado(id);
	        if (optionalCliente.isPresent()) {
	            Cliente cliente = optionalCliente.get();

	            // Guardar el NIT anterior
	            String nitAnterior = cliente.getNit();
	            String nuevoNit = clienteDetails.getNit();

	            // Actualizar campos del cliente
	            cliente.setNit(nuevoNit);
	            cliente.setNombre(clienteDetails.getNombre());
	            cliente.setApellidos(clienteDetails.getApellidos());
	            cliente.setSexo(clienteDetails.getSexo());
	            cliente.setEmail(clienteDetails.getEmail());
	            cliente.setEstado(clienteDetails.getEstado());

	            // Guardar cliente actualizado
	            Cliente updatedCliente = clienteService.save(cliente);

	         // Buscar un empleado con el NIT anterior y actualizar su CI
	            Optional<Empleado> empleadoOptional = empleadoService.obtenerEmpleado(nitAnterior);
	            if (empleadoOptional.isPresent()) {
	                Empleado empleado = empleadoOptional.get();
	                empleado.setCi(nuevoNit);
	                empleado.setNombre(clienteDetails.getNombre());
	                empleado.setApellidos(clienteDetails.getApellidos());
	                empleadoService.save(empleado);
	            }

	            // Buscar un proveedor con el NIT anterior y actualizar su CI
	            Optional<Proveedor> proveedorOptional = proveedorService.obtenerProveedor(nitAnterior);
	            if (proveedorOptional.isPresent()) {
	                Proveedor proveedor = proveedorOptional.get();
	                proveedor.setCi(nuevoNit);
	                proveedor.setNombre(clienteDetails.getNombre());
	                proveedor.setApellidos(clienteDetails.getApellidos());
	                proveedorService.save(proveedor);
	            }


	            return ResponseEntity.ok(updatedCliente);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    
	    /*
	       
	    @PutMapping("/{id}")
	    public ResponseEntity<Cliente> updateEmpleado(@PathVariable int id, @RequestBody Cliente clienteDetails) {
	        Optional<Cliente> optionalCliente = clienteService.obtenerEmpleado(id);
	        if (optionalCliente.isPresent()) {
	        	Cliente cliente = optionalCliente.get();
	        	cliente.setNit(clienteDetails.getNit());
	        	cliente.setNombre(clienteDetails.getNombre());
	        	cliente.setApellidos(clienteDetails.getApellidos());
	        	cliente.setSexo(clienteDetails.getSexo());
	        	cliente.setEmail(clienteDetails.getEmail());
	        	cliente.setEstado(clienteDetails.getEstado());
	            
	            Cliente updatedEmpleado = clienteService.save(cliente);
	            return ResponseEntity.ok(updatedEmpleado);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }*/
	    
	    @PutMapping("/cambiarEstado/{id}")
	    public ResponseEntity<Cliente> updateEstadoProveedor(@PathVariable int id, @RequestBody Cliente clienteFrontend) {
	        Optional<Cliente> optionalCliente = clienteService.obtenerEmpleado(id);
	        Cliente updatedCliente;
	        if (optionalCliente.isPresent()) {
	        	Cliente cliente1 = optionalCliente.get();
	            if(clienteFrontend.getEstado() == 1) {
	            	cliente1.setEstado(2);
	            	updatedCliente = clienteService.save(cliente1);
	            }else {
	            	cliente1.setEstado(1);
	            	updatedCliente = clienteService.save(cliente1);
	            }
	  
	          //  Proveedor updatedProveedor = proveedorService.save(proveedor1);
	            return ResponseEntity.ok(updatedCliente);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    

	    @GetMapping("/buscar")
	    public ResponseEntity<List<Cliente>> buscarClientes(@RequestParam("filtro") String filtro) {
	        List<Cliente> clientes = clienteService.buscarClientesPorNombreOApellido(filtro);
	        return ResponseEntity.ok(clientes);
	    }
	 
	    @GetMapping("/{id}")
	    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable int id) {
	        Cliente cliente = clienteService.obtenerClientePorId(id);
	        if (cliente != null) {
	            return ResponseEntity.ok(cliente);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	    
}
