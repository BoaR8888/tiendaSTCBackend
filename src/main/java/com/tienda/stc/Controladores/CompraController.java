package com.tienda.stc.Controladores;

import java.util.List;
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

import com.tienda.stc.Clases.Compra;
import com.tienda.stc.Clases.DetalleCompra;
import com.tienda.stc.Servicios.CompraService;
import com.tienda.stc.Servicios.DetalleCompraService;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin("*")
public class CompraController {
	
	
	 @Autowired
	    private CompraService compraServicio;

	    @PostMapping
	    public Compra crearCompra(@RequestBody Compra compra) {
	    	
	        return compraServicio.guardarCompra(compra);
	    }
	    

	    @GetMapping("/ultimo-recibo")
	    public ResponseEntity<String> obtenerUltimoRecibo() {
	        String nuevoRecibo = compraServicio.generarReciboCompra();
	        return ResponseEntity.ok(nuevoRecibo);
	    }

	    @GetMapping
	    public List<Compra> obtenerTodasLasCompras() {
	        return compraServicio.listarCompras();
	    }

	    @GetMapping("/{id}")
	    public Optional<Compra> obtenerCompraPorId(@PathVariable Long id) {
	        return compraServicio.obtenerCompraPorId(id);
	    }

	    @PutMapping("/{id}")
	    public Compra actualizarCompra(@PathVariable Long id, @RequestBody Compra compraActualizada) {
	        compraActualizada.setIdCompra(id);
	        return compraServicio.actualizarCompra(compraActualizada);
	    }
	    
	    @PutMapping("/cambiar-estado/{id}")
	    public ResponseEntity<?> cambiarEstadoCompra(@PathVariable Long id) {
	        compraServicio.cambiarEstado(id);
	        return ResponseEntity.ok().build();
	    }
	    
	    @GetMapping("/buscar")
	    public ResponseEntity<List<Compra>> buscarCompras(
	            @RequestParam(required = false) String filtro,
	            @RequestParam(required = false) Integer estado) {
	        
	    	System.out.println(filtro +" " + estado);
	        List<Compra> compras = compraServicio.buscarCompras(filtro, estado);
	        if (compras.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.ok(compras);
	    }

   /*
	    @GetMapping("/buscar")
	    public ResponseEntity<List<Compra>> buscarPorReciboCompra(@RequestParam String reciboCompra) {
	        List<Compra> compras = compraServicio.buscarPorReciboCompra(reciboCompra);
	        return ResponseEntity.ok(compras);
	    }*/

}
