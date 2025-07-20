package com.tienda.stc.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.stc.Clases.Pago;
import com.tienda.stc.Servicios.PagoService;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin("*") // Puedes ajustar según tu frontend
public class PagoController {
	  @Autowired
	    private PagoService pagoService;

	    @GetMapping
	    public List<Pago> listarTodos() {
	        return pagoService.listarTodos();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Pago> obtenerPorId(@PathVariable Long id) {
	        return pagoService.obtenerPorId(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }

	    @PostMapping
	    public ResponseEntity<Pago> crear(@RequestBody Pago pago) {
	        Pago nuevoPago = pagoService.guardar(pago);
	        return ResponseEntity.ok(nuevoPago);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Pago> actualizar(@PathVariable Long id, @RequestBody Pago pagoActualizado) {
	        return pagoService.obtenerPorId(id).map(pagoExistente -> {
	            pagoActualizado.setIdPago(id);
	            return ResponseEntity.ok(pagoService.guardar(pagoActualizado));
	        }).orElse(ResponseEntity.notFound().build());
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
	        if (pagoService.obtenerPorId(id).isPresent()) {
	            pagoService.eliminar(id);
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.notFound().build();
	    }
	    
	    @PutMapping("/cambiar-estado/{id}")
	    public ResponseEntity<Pago> cambiarEstado(@PathVariable Long id) {
	        Pago pagoActualizado = pagoService.cambiarEstado(id);
	        return ResponseEntity.ok(pagoActualizado);
	    }
}
