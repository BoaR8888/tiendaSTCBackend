package com.tienda.stc.Servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.stc.Clases.Cambio;
import com.tienda.stc.Clases.DetalleCambio;
import com.tienda.stc.Clases.Pago;
import com.tienda.stc.Clases.Producto;
import com.tienda.stc.Repositorios.CambioRepository;
import com.tienda.stc.Repositorios.DetalleCambioRepository;
import com.tienda.stc.Repositorios.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class CambioService {

	 @Autowired
	    private CambioRepository cambioRepository;

	    @Autowired
	    private DetalleCambioRepository detalleCambioRepository;

	    @Autowired
	    private ProductoRepository productoRepository;

	    @Transactional
	    public Cambio registrarCambio(Cambio cambio) {
	        // Guardamos el cambio base sin detalles todavía
	        Cambio cambioGuardado = cambioRepository.save(cambio);

	        for (DetalleCambio detalle : cambio.getDetalles()) {
	            detalle.setCambio(cambioGuardado);

	            // Obtener precio nuevo desde base de datos por producto cambiado
	            Producto cambiado = productoRepository.findById(detalle.getProductoCambiado().getId())
	                    .orElseThrow(() -> new RuntimeException("Producto cambiado no encontrado"));
	            double precioNuevo = cambiado.getPrecioVenta();

	            // Sobrescribir solo el precioNuevo con el precio de la BD
	            detalle.setPrecioNuevo(precioNuevo);

	            // Actualizar stock
	            Producto original = productoRepository.findById(detalle.getProductoOriginal().getId())
	                    .orElseThrow(() -> new RuntimeException("Producto original no encontrado"));
	            original.setStock(original.getStock() + detalle.getCantidadDevuelta());
	            cambiado.setStock(cambiado.getStock() - detalle.getCantidad());

	            productoRepository.save(original);
	            productoRepository.save(cambiado);

	            // Guardar el detalle con los datos tal cual vienen (menos precioNuevo actualizado)
	            detalleCambioRepository.save(detalle);
	        }

	        // Mantener el montoDiferencia tal cual viene desde el frontend
	        cambioGuardado.setMontoDiferencia(cambio.getMontoDiferencia());
	        return cambioRepository.save(cambioGuardado);
	    }

	    /*
	    public Cambio registrarCambio(Cambio cambio) {
	        double montoDiferencia = 0.0;

	        // Guardamos el cambio base sin detalles todavía
	        Cambio cambioGuardado = cambioRepository.save(cambio);
	        System.out.println("Precio monto diferencia: " + cambio.getMontoDiferencia());
	        for (DetalleCambio detalle : cambio.getDetalles()) {
	            System.out.println("Producto original ID: " + (detalle.getProductoOriginal() != null ? detalle.getProductoOriginal().getId() : "null"));
	            System.out.println("Producto cambiado ID: " + (detalle.getProductoCambiado() != null ? detalle.getProductoCambiado().getId() : "null"));
	            System.out.println("Cantidad: " + detalle.getCantidad());
	            System.out.println("Cantidad devuelta: " + detalle.getCantidadDevuelta());
	            System.out.println("Precio original enviado: " + detalle.getPrecioOriginal());
	            System.out.println("Precio nuevo enviado: " + detalle.getPrecioNuevo());
	           
	        }

	        for (DetalleCambio detalle : cambio.getDetalles()) {
	            detalle.setCambio(cambioGuardado);

	            // Precios actuales desde base de datos
	            Producto original = productoRepository.findById(detalle.getProductoOriginal().getId()).orElseThrow();
	            Producto cambiado = productoRepository.findById(detalle.getProductoCambiado().getId()).orElseThrow();

	            
	            
	            double precioOriginal = detalle.getPrecioOriginal();
	            double precioNuevo = cambiado.getPrecioVenta();

	            // Multiplicamos los precios por la cantidad
	            double precioTotalOriginal = precioOriginal * detalle.getCantidadDevuelta() ;  
	            double precioTotalNuevo = precioNuevo * detalle.getCantidad(); 
	            
	            detalle.setPrecioOriginal(precioTotalOriginal);
	     
	            detalle.setPrecioNuevo(precioTotalNuevo);

	            // Calcular diferencia y acumular
	            double diferencia = (precioTotalNuevo - precioTotalOriginal );
	            montoDiferencia += diferencia;

	            // Actualizar stock
	            original.setStock(original.getStock() + detalle.getCantidadDevuelta()); // se devuelve
	            cambiado.setStock(cambiado.getStock() - detalle.getCantidad()); // se entrega

	            productoRepository.save(original);
	            productoRepository.save(cambiado);

	            // Guardar el detalle
	            detalleCambioRepository.save(detalle);
	        }

	        
	        
	        // Guardar el monto total de diferencia
	        cambioGuardado.setMontoDiferencia(montoDiferencia);
	        return cambioRepository.save(cambioGuardado);
	    }
*/
	    public List<Cambio> listarCambios() {
	        return cambioRepository.findAllOrderByIdDesc();
	    }

	    public Cambio obtenerPorId(Long id) {
	        return cambioRepository.findById(id).orElse(null);
	    }
	    @Transactional
	    public Cambio cambiarEstado(Long idcambio) {
	        Cambio cambio = cambioRepository.findById(idcambio)
	                .orElseThrow(() -> new RuntimeException("Cambio no encontrado con ID: " + idcambio));

	        if (cambio.getEstado() == 1) {
	            // Cambiar a estado PROCESADO
	            cambio.setEstado(2);

	            // Ajustar stock de productos
	            for (DetalleCambio detalle : cambio.getDetalles()) {
	                Producto original = detalle.getProductoOriginal();
	                Producto cambiado = detalle.getProductoCambiado();

	                // Revertir stock
	                original.setStock(original.getStock() - detalle.getCantidadDevuelta());
	                cambiado.setStock(cambiado.getStock() + detalle.getCantidad());

	                // Guardar productos
	                productoRepository.save(original);
	                productoRepository.save(cambiado);
	            }

	        } else {
	            // Volver a estado PENDIENTE (opcional, según tu lógica)
	            cambio.setEstado(1);
	        }

	        return cambioRepository.save(cambio);
	    }

}
