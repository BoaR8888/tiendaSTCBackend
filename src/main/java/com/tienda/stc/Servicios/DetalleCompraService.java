package com.tienda.stc.Servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.stc.Clases.DetalleCompra;
import com.tienda.stc.Repositorios.DetalleCompraRepository;

@Service
public class DetalleCompraService {
	
	 @Autowired
	    private DetalleCompraRepository detalleCompraRepositorio;

	    public DetalleCompra guardarDetalle(DetalleCompra detalle) {
	        return detalleCompraRepositorio.save(detalle);
	    }

	    public List<DetalleCompra> listarDetalles() {
	        return detalleCompraRepositorio.findAll();
	    }

	    public DetalleCompra actualizarDetalle(DetalleCompra detalleActualizado) {
	        return detalleCompraRepositorio.save(detalleActualizado);
	    }

}
