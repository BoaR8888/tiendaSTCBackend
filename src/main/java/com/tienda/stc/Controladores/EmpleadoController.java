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
import org.springframework.web.bind.annotation.RestController;

import com.tienda.stc.Clases.Cliente;
import com.tienda.stc.Clases.Empleado;
import com.tienda.stc.Clases.Proveedor;
import com.tienda.stc.Servicios.ClienteService;
import com.tienda.stc.Servicios.EmpleadoService;
import com.tienda.stc.Servicios.ProveedorService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/empleados")
public class EmpleadoController {
	
	 @Autowired
	 private EmpleadoService empleadoService;
	 
	 @Autowired
	 private ClienteService clienteService;
	 
	 @Autowired
	 private ProveedorService proveedorService;
	 
	 //FUNCIONES
	 @GetMapping
	 public List<Empleado> listarClientes(){
		 return empleadoService.listarEmpleados();	
	}
	  @GetMapping("/buscar-por-ci/{ci}")
	    public ResponseEntity<Map<String, Object>> buscarPorCi(@PathVariable String ci) {
	        System.out.println("CI recibido en empleado: " + ci);
	        Map<String, Object> datos = empleadoService.buscarPorCi(ci);

	        if (datos.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }

	        return ResponseEntity.ok(datos);
	    }

	    @PostMapping("/agregar")
	    public Empleado agregarEmpleado(@RequestBody Map<String, Object> request) throws ParseException {
	    	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	          Date fechaConvertida = formatter.parse((String) request.get("fechaIngreso"));
	          
	        // Obtener los datos del empleado y del usuario
	        String nombreUsuario = (String) request.get("nombreUsuario");
	        String password = (String) request.get("password");

	        // Crear el empleado con los datos
	        Empleado empleado = new Empleado();
	        empleado.setCi((String) request.get("ci"));
	        empleado.setNombre((String) request.get("nombre"));
	        empleado.setApellidos((String) request.get("apellidos"));
	        empleado.setCelular((String) request.get("celular"));
	        empleado.setEdad((Integer) request.get("edad"));
	        empleado.setSexo((String) request.get("sexo"));
	        empleado.setDireccion((String) request.get("direccion"));
	        empleado.setEmail((String) request.get("email"));
	        empleado.setFechaIngreso((fechaConvertida));
	        // Obtener el salario como String (puede ser 1500, 1500.00, etc.)
	        String salarioStr = (String) request.get("salario");
	        
	        // Asegurarse de que el salario esté formateado correctamente con dos decimales
	        Double salario = 0.0;
	        try {
	            salario = Double.parseDouble(salarioStr);  // Convierte el salario a Double
	        } catch (NumberFormatException e) {
	            // Si no se puede convertir, puedes manejar el error aquí
	            System.out.println("Error al convertir el salario a Double: " + e.getMessage());
	        }
	        
	        // Asignar el salario al empleado
	        empleado.setSalario(salario);

	       // empleado.setSalario((Double) request.get("salario"));
	        empleado.setEstado((Integer) request.get("estado"));

	        // Llamar al servicio para agregar el empleado con su usuario
	        return empleadoService.agregarEmpleadoConUsuario(empleado, nombreUsuario, password);
	    }
	    
	    
	    
	    @PutMapping("/{id}")
	    public ResponseEntity<Empleado> updateEmpleado(@PathVariable int id, @RequestBody Empleado empleadoDetails) {
	        Optional<Empleado> optionalEmpleado = empleadoService.obtenerEmpleado(id);
	        if (optionalEmpleado.isPresent()) {
	            Empleado empleado = optionalEmpleado.get();

	            // Guardar CI anterior
	            String ciAnterior = empleado.getCi();
	            String nuevoCi = empleadoDetails.getCi();

	            // Actualizar campos del empleado
	            empleado.setCi(nuevoCi);
	            empleado.setNombre(empleadoDetails.getNombre());
	            empleado.setApellidos(empleadoDetails.getApellidos());
	            empleado.setCelular(empleadoDetails.getCelular());
	            empleado.setEdad(empleadoDetails.getEdad());
	            empleado.setSexo(empleadoDetails.getSexo());
	            empleado.setEmail(empleadoDetails.getEmail());
	            empleado.setDireccion(empleadoDetails.getDireccion());
	            empleado.setFechaIngreso(empleadoDetails.getFechaIngreso());
	            empleado.setSalario(empleadoDetails.getSalario());

	            // Guardar empleado actualizado
	            Empleado updatedEmpleado = empleadoService.save(empleado);

	            // Buscar cliente con NIT igual al CI anterior y actualizarlo
	            Optional<Cliente> clienteOptional = clienteService.obtenerCliente(ciAnterior);
	            if (clienteOptional.isPresent()) {
	                Cliente cliente = clienteOptional.get();
	                cliente.setNit(nuevoCi);
	                cliente.setNombre(empleadoDetails.getNombre());
	                cliente.setApellidos(empleadoDetails.getApellidos());
	                clienteService.save(cliente);
	            }

	            // Buscar proveedor con CI igual al CI anterior y actualizarlo
	            Optional<Proveedor> proveedorOptional = proveedorService.obtenerProveedor(ciAnterior);
	            if (proveedorOptional.isPresent()) {
	                Proveedor proveedor = proveedorOptional.get();
	                proveedor.setCi(nuevoCi);
	                proveedor.setNombre(empleadoDetails.getNombre());
	                proveedor.setApellidos(empleadoDetails.getApellidos());
	                proveedorService.save(proveedor);
	            }

	            return ResponseEntity.ok(updatedEmpleado);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	 /*   @PutMapping("/{id}")
	    public ResponseEntity<Empleado> updateEmpleado(@PathVariable int id, @RequestBody Empleado empleadoDetails) {
	        Optional<Empleado> optionalEmpleado = empleadoService.obtenerEmpleado(id);
	        if (optionalEmpleado.isPresent()) {
	            Empleado empleado = optionalEmpleado.get();
	            empleado.setCi(empleadoDetails.getCi());
	            empleado.setNombre(empleadoDetails.getNombre());
	            empleado.setApellidos(empleadoDetails.getApellidos());
	            empleado.setCelular(empleadoDetails.getCelular());
	            empleado.setEdad(empleadoDetails.getEdad());
	            empleado.setSexo(empleadoDetails.getSexo());
	            empleado.setEmail(empleadoDetails.getEmail());
	            empleado.setDireccion(empleadoDetails.getDireccion());
	            empleado.setFechaIngreso(empleadoDetails.getFechaIngreso());
	            empleado.setSalario(empleadoDetails.getSalario());
	           
	            Empleado updatedEmpleado = empleadoService.save(empleado);
	            return ResponseEntity.ok(updatedEmpleado);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }*/
	    
	    @PutMapping("/cambiarEstado/{id}")
	    public ResponseEntity<Empleado> updateEstadoProveedor(@PathVariable int id, @RequestBody Empleado empleadoFrontend) {
	        Optional<Empleado> optionalProveedor = empleadoService.obtenerEmpleado(id);
	        Empleado updatedProveedor;
	        if (optionalProveedor.isPresent()) {
	        	Empleado empleado1 = optionalProveedor.get();
	            if(empleadoFrontend.getEstado() == 1) {
	            	empleado1.setEstado(2);
	            	updatedProveedor = empleadoService.save(empleado1);
	            }else {
	            	empleado1.setEstado(1);
	            	updatedProveedor = empleadoService.save(empleado1);
	            }
	  
	          //  Proveedor updatedProveedor = proveedorService.save(proveedor1);
	            return ResponseEntity.ok(updatedProveedor);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    

	    
	    // Endpoint para obtener empleados con rol tecnico
	    @GetMapping("/tecnicos")
	    public ResponseEntity<List<Empleado>> listarEmpleadosTecnicos() {
	        List<Empleado> tecnicos = empleadoService.obtenerEmpleadosTecnicos();
	        if (tecnicos.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.ok(tecnicos);
	    }
	    
	    
	 /*
	 @PostMapping("/agregar")
	 public Empleado agregarEmpleado(@RequestBody Empleado empleado) {
	     return empleadoService.agregarEmpleadoConUsuario(empleado, null, null)
	 }
*/

}
