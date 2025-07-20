package com.tienda.stc.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.stc.Clases.DetalleCompra;
import com.tienda.stc.Servicios.DetalleCompraService;

@RestController
@RequestMapping("/api/detalle-compras")
@CrossOrigin("*")
public class DetalleCompraController {
	
	 @Autowired
	    private DetalleCompraService detalleCompraServicio;

	    @PostMapping
	    public DetalleCompra crearDetalle(@RequestBody DetalleCompra detalle) {
	        return detalleCompraServicio.guardarDetalle(detalle);
	    }

	    @GetMapping
	    public List<DetalleCompra> listarDetalles() {
	        return detalleCompraServicio.listarDetalles();
	    }

	    @PutMapping("/{id}")
	    public DetalleCompra actualizarDetalle(@PathVariable Long id, @RequestBody DetalleCompra detalleActualizado) {
	        detalleActualizado.setIdDetalleCompra(id);
	        return detalleCompraServicio.actualizarDetalle(detalleActualizado);
	    }
}
