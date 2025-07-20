package com.tienda.stc.Servicios;

import com.tienda.stc.Clases.Compra;
import com.tienda.stc.Clases.DetalleCompra;
import com.tienda.stc.Clases.Empleado;
import com.tienda.stc.Clases.Producto;
import com.tienda.stc.Clases.Proveedor;
import com.tienda.stc.Repositorios.CompraRepository;
import com.tienda.stc.Repositorios.EmpleadoRepository;
import com.tienda.stc.Repositorios.ProductoRepository;
import com.tienda.stc.Repositorios.ProveedorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepositorio;
    
    @Autowired
    private ProveedorRepository proveedorRepository;
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ProductoRepository productoRepository;
    
    public Compra guardarCompra(Compra compra) {
    	
        // Actualizar stock de cada producto comprado
        if (compra.getProductosComprados() != null) {
            for (DetalleCompra detalle : compra.getProductosComprados()) {
                Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalle.getProducto().getId()));

                // Sumar cantidad comprada al stock actual
                int nuevoStock = producto.getStock() + detalle.getCantidadCompra();
                producto.setStock(nuevoStock);
                productoRepository.save(producto); // Guardar el producto con nuevo stock
            }
        }
        return compraRepositorio.save(compra);
    }
    
    public String generarReciboCompra() {
        Optional<Compra> ultimaCompra = compraRepository.findTopByOrderByIdCompraDesc();
        long siguienteId = ultimaCompra.map(compra -> compra.getIdCompra() + 1).orElse(1L);

        return String.format("COMP-2025-%06d", siguienteId);
    }

    public List<Compra> listarCompras() {
        return compraRepositorio.findAllByOrderByIdCompraDesc();
    }

    public Optional<Compra> obtenerCompraPorId(Long id) {
        return compraRepositorio.findById(id);
    }

    public Compra actualizarCompra(Compra compraActualizada) {
        return compraRepositorio.save(compraActualizada);
    }
    
   // @Transactional
    public void cambiarEstado(Long idCompra) {
        Compra compra = compraRepository.findById(idCompra)
            .orElseThrow(() -> new RuntimeException("Compra no encontrada con ID: " + idCompra));

        if (compra.getEstadoCompra() == 1) {
            // Cambiar a estado ANULADA
            compra.setEstadoCompra(2);

            // Por cada producto comprado, restar del stock
            for (DetalleCompra detalle : compra.getProductosComprados()) {
                Producto producto = detalle.getProducto();
                producto.setStock(producto.getStock() - detalle.getCantidadCompra());
                productoRepository.save(producto); // asegúrate de tener productoRepository
            }

        } else {
            throw new RuntimeException("La compra ya fue anulada anteriormente.");
        }

        compraRepository.save(compra);
    }

    public List<Compra> buscarCompras(String filtro, Integer estado) {
        // Validar filtro: si es null o vacío después de limpiar espacios, tratar como null
        if (filtro == null || filtro.trim().isEmpty()) {
            filtro = null;
        }

        return compraRepository.buscarPorFiltroYEstado(filtro, estado);
    }

/*    public List<Compra> buscarCompras(String filtro, Integer estado) {
        if (filtro != null && filtro.trim().isEmpty()) {
            filtro = null; // evitar filtro vacío que no sirve
        }
        return compraRepository.buscarPorFiltroYEstado(filtro, estado);
    }*/
    /*public List<Compra> buscarPorReciboCompra(String reciboCompra) {
        return compraRepository.findByReciboCompraContainingIgnoreCase(reciboCompra);
    }*/
}
