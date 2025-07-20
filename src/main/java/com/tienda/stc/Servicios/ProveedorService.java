package com.tienda.stc.Servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.stc.Clases.Cliente;
import com.tienda.stc.Clases.Empleado;
import com.tienda.stc.Clases.Proveedor;
import com.tienda.stc.Repositorios.ClienteRepository;
import com.tienda.stc.Repositorios.EmpleadoRepository;
import com.tienda.stc.Repositorios.ProveedorRepository;



@Service
public class ProveedorService {
	
	@Autowired
	private ProveedorRepository proveedorRepository;
	
	 @Autowired
	    private ClienteRepository clienteRepository;

	    @Autowired
	    private EmpleadoRepository empleadoRepository;

	    public Map<String, Object> buscarPorCi(String ci) {
	        Map<String, Object> datos = new HashMap<>();

	        Optional<Proveedor> proveedor = proveedorRepository.findByCi(ci);
	        Optional<Cliente> cliente = clienteRepository.findByNit(ci);
	        Optional<Empleado> empleado = empleadoRepository.findByCi(ci);

	        if (proveedor.isPresent()) {
	            Proveedor p = proveedor.get();
	            datos.put("nombre", p.getNombre());
	            datos.put("apellidos", p.getApellidos());
	            datos.put("fuente", "proveedor");
	            datos.put("yaRegistrado", true);
	        } else if (cliente.isPresent()) {
	            Cliente c = cliente.get();
	            datos.put("nombre", c.getNombre());
	            datos.put("apellidos", c.getApellidos());
	            datos.put("fuente", "cliente");
	            datos.put("yaRegistrado", false);
	        } else if (empleado.isPresent()) {
	            Empleado e = empleado.get();
	            datos.put("nombre", e.getNombre());
	            datos.put("apellidos", e.getApellidos());
	            datos.put("fuente", "empleado");
	            datos.put("yaRegistrado", false);
	        } else {
	            datos.put("fuente", "ninguna");
	            datos.put("yaRegistrado", false);
	        }

	        return datos;
	    }

	
	//Funcion listar GET
	public List<Proveedor> listarProveedor(){
		//System.out.print(proveedorRepository.findAll());
		return proveedorRepository.findAllByOrderByIdProveedorDesc();
	}
	
	public Proveedor save(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }
	
	 public Optional<Proveedor> obtenerProveedor(Long id) {
	        return proveedorRepository.findById(id);
	    }
	 
	 public void eliminarProveedor(long id) {
		 proveedorRepository.deleteById(id);
	 }
	 
	 public Optional<Proveedor> obtenerProveedor(String ci) {
	        return proveedorRepository.findByCi(ci);
	    }
	/* Nuevo método para contar nombres de proveedores
		public List<ReporteProveedor> contarNombres() {
			return proveedorRepository.contarNombres();
		}
		*/
	 
	  public Map<String, Long> contarCiudad() {
	        List<Object[]> results = proveedorRepository.contarNombres();
	        Map<String, Long> nombreContador = new HashMap<>();

	        for (Object[] result : results) {
	            String ciudad = (String) result[0];
	            Long cantidad = (Long) result[1];
	            nombreContador.put(ciudad, cantidad);
	        }

	        return nombreContador;
	    }
}
