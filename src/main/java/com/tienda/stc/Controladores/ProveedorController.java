package com.tienda.stc.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tienda.stc.Clases.Cliente;
import com.tienda.stc.Clases.Empleado;
import com.tienda.stc.Clases.Proveedor;
import com.tienda.stc.Repositorios.ProveedorRepository;
import com.tienda.stc.Servicios.ClienteService;
import com.tienda.stc.Servicios.EmpleadoService;
import com.tienda.stc.Servicios.ProveedorService;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;
    private ProveedorRepository proveedorRepository;
    
    @Autowired
	 private ClienteService clienteService;
	 
	 @Autowired
	 private EmpleadoService empleadoService;

    //devuelve la lista creada aqui
    @GetMapping("/listaProveedores")
    public List<Proveedor> getAllProveedores() {
    	
        return proveedorService.listarProveedor();
    }
    
    @PostMapping
    public Proveedor createProveedor(@RequestBody Proveedor proveedor) {
    	return proveedorService.save(proveedor);
    }
    
    @GetMapping("/buscar-por-ci/{ci}")
    public ResponseEntity<Map<String, Object>> buscarPorCi(@PathVariable String ci) {
        System.out.println("CI recibido en proveedor: " + ci);
        Map<String, Object> datos = proveedorService.buscarPorCi(ci);

        if (datos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(datos);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> updateProveedor(@PathVariable Long id, @RequestBody Proveedor proveedorFrontend) {
        Optional<Proveedor> optionalProveedor = proveedorService.obtenerProveedor(id);
        if (optionalProveedor.isPresent()) {
            Proveedor proveedor1 = optionalProveedor.get();

            // Guardar el CI anterior
            String ciAnterior = proveedor1.getCi();
            String nuevoCi = proveedorFrontend.getCi();

            // Actualizar datos del proveedor
            proveedor1.setNombre(proveedorFrontend.getNombre());
            proveedor1.setApellidos(proveedorFrontend.getApellidos());
            proveedor1.setCiudad(proveedorFrontend.getCiudad());
            proveedor1.setDireccion(proveedorFrontend.getDireccion()); 
            proveedor1.setTelefono(proveedorFrontend.getTelefono());
            proveedor1.setDetalles(proveedorFrontend.getDetalles());
            proveedor1.setEmpresa(proveedorFrontend.getEmpresa());
            proveedor1.setCi(nuevoCi);

            Proveedor updatedProveedor = proveedorService.save(proveedor1);

            // 🔁 Actualizar Empleado si tenía ese CI
            Optional<Empleado> empleadoOptional = empleadoService.obtenerEmpleado(ciAnterior);
            if (empleadoOptional.isPresent()) {
                Empleado empleado = empleadoOptional.get();
                empleado.setCi(nuevoCi);
                empleado.setNombre(proveedorFrontend.getNombre());
                empleado.setApellidos(proveedorFrontend.getApellidos());
                empleadoService.save(empleado);
            }

            // 🔁 Actualizar Cliente si tenía ese CI
            Optional<Cliente> clienteOptional = clienteService.obtenerCliente(ciAnterior);
            if (clienteOptional.isPresent()) {
                Cliente cliente = clienteOptional.get();
                cliente.setNit(nuevoCi);
                cliente.setNombre(proveedorFrontend.getNombre());
                cliente.setApellidos(proveedorFrontend.getApellidos());
                clienteService.save(cliente);
            }

            return ResponseEntity.ok(updatedProveedor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /*
    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> updateProveedor(@PathVariable Long id, @RequestBody Proveedor proveedorFrontend) {
        Optional<Proveedor> optionalProveedor = proveedorService.obtenerProveedor(id);
        if (optionalProveedor.isPresent()) {
            Proveedor proveedor1 = optionalProveedor.get();
            proveedor1.setNombre(proveedorFrontend.getNombre());
            proveedor1.setApellidos(proveedorFrontend.getApellidos());
            proveedor1.setCiudad(proveedorFrontend.getCiudad());
            proveedor1.setDireccion(proveedorFrontend.getDireccion()); 
            proveedor1.setTelefono(proveedorFrontend.getTelefono());
            proveedor1.setDetalles(proveedorFrontend.getDetalles());
            proveedor1.setEmpresa(proveedorFrontend.getEmpresa()); // ← Agregado
            proveedor1.setCi(proveedorFrontend.getCi());           // ← Agregado
            Proveedor updatedProveedor = proveedorService.save(proveedor1);
            return ResponseEntity.ok(updatedProveedor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
    
    @PutMapping("/cambiarEstado/{id}")
    public ResponseEntity<Proveedor> updateEstadoProveedor(@PathVariable Long id, @RequestBody Proveedor proveedorFrontend) {
        Optional<Proveedor> optionalProveedor = proveedorService.obtenerProveedor(id);
        Proveedor updatedProveedor;
        if (optionalProveedor.isPresent()) {
            Proveedor proveedor1 = optionalProveedor.get();
            if(proveedorFrontend.getEstado() == 1) {
            	proveedor1.setEstado(2);
            	updatedProveedor = proveedorService.save(proveedor1);
            }else {
            	proveedor1.setEstado(1);
            	updatedProveedor = proveedorService.save(proveedor1);
            }
  
          //  Proveedor updatedProveedor = proveedorService.save(proveedor1);
            return ResponseEntity.ok(updatedProveedor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        if (proveedorService.obtenerProveedor(id).isPresent()) {
        	proveedorService.eliminarProveedor(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/contar-nombres")
    public Map<String, Long> contarNombres() {
        return proveedorService.contarCiudad();
    }
    
    
    /*
    
    @GetMapping("/contar-nombres")
	public List<ReporteProveedor> contarNombres() {
		return proveedorService.contarNombres();
	}*/
    
    /*
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> buscarProveedor(@PathVariable Long id) {
        Optional<Proveedor> proveedor = proveedorRepository.findById(id);
        return proveedor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
*/
}
