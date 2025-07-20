package com.tienda.stc.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.stc.Clases.Cliente;
import com.tienda.stc.Clases.Compra;
import com.tienda.stc.Clases.DetalleVenta;
import com.tienda.stc.Clases.DetalleVentaDTO;
import com.tienda.stc.Clases.Empleado;
import com.tienda.stc.Clases.Producto;
import com.tienda.stc.Clases.Venta;
import com.tienda.stc.Clases.VentaDTO;
import com.tienda.stc.Repositorios.ClienteRepository;
import com.tienda.stc.Repositorios.DetalleVentaRepository;
import com.tienda.stc.Repositorios.EmpleadoRepository;
import com.tienda.stc.Repositorios.ProductoRepository;
import com.tienda.stc.Repositorios.VentaRepository;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    

    public List<Venta> listarVentas() {
       // return ventaRepository.findAll();
        return ventaRepository.findAllByOrderByFechaVentaDescIdDesc();
    }
    
    
    public String obtenerUltimoNumeroSerie() {
        // Obtener la última venta registrada
        return ventaRepository.findTopByOrderByIdDesc()
                .map(Venta::getNumeroSerie)
                .orElse("145789");  // Si no existe, asignamos un valor inicial
    }
    
    
    public Venta guardarVenta(Venta venta) {
    	  // Obtener el último número de serie y incrementarlo
    	 String numeroSerie = generarNumeroSerieVenta();
    	    venta.setNumeroSerie(numeroSerie);


    	    // Actualizar stock de productos vendidos
    	    if (venta.getDetallesVenta() != null) {
    	        for (DetalleVenta detalle : venta.getDetallesVenta()) {
    	            Producto producto = productoRepository.findById(detalle.getProducto().getId())
    	                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalle.getProducto().getId()));

    	            int nuevoStock = producto.getStock() - detalle.getCantidad();
    	            if (nuevoStock < 0) {
    	                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
    	            }

    	            producto.setStock(nuevoStock);
    	            productoRepository.save(producto);
    	        }
    	    }
        // Guardar la venta
        return ventaRepository.save(venta);
    }
    
    
    public String generarNumeroSerieVenta() {
        Optional<Venta> ultimaVenta = ventaRepository.findTopByOrderByIdDesc();

        long siguienteId;
        if (ultimaVenta.isPresent() && ultimaVenta.get().getId() != null) {
            siguienteId = ultimaVenta.get().getId() + 1;
        } else {
            siguienteId = 1L;
        }

        return String.format("VNT-2025-%06d", siguienteId);
    }

    
    public Venta actualizarVenta(Long id, Venta ventaActualizada) {
        Venta venta = ventaRepository.findById(id).orElseThrow();
        venta.setFechaVenta(ventaActualizada.getFechaVenta());
        venta.setCliente(ventaActualizada.getCliente());
        venta.setEmpleado(ventaActualizada.getEmpleado());
        venta.setEstado(ventaActualizada.getEstado());
        return ventaRepository.save(venta);
    }

    public void eliminarVenta(Long id) {
        ventaRepository.deleteById(id);
    }

    public Venta obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id).orElseThrow();
    }
    
    @Transactional
    public Venta crearVentaCompleta(VentaDTO ventaDTO) {
        // Crear la venta
        Venta venta = new Venta();
        venta.setFechaVenta(ventaDTO.getFechaVenta());
        venta.setNumeroSerie(ventaDTO.getNumeroSerie());
        venta.setEstado(ventaDTO.getEstado());
        
        Optional<Cliente> clienteOpt = clienteRepository.findById(ventaDTO.getClienteId().intValue());
        Cliente cliente = clienteOpt.orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

      //  Cliente cliente = clienteRepository.findById(ventaDTO.getClienteId())
      //      .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Optional<Empleado> empleadoOpt = empleadoRepository.findById(ventaDTO.getEmpleadoId().intValue());
        Empleado empleado = empleadoOpt.orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        venta.setCliente(cliente);
        venta.setEmpleado(empleado);
        
        // Calcular total de venta
        double totalVenta = 0.0;
        List<DetalleVenta> detalles = new ArrayList<>();
        
        // Guardar la venta primero para obtener su ID
        venta = ventaRepository.save(venta);
        
        // Procesar cada detalle
        for (DetalleVentaDTO detalleDTO : ventaDTO.getDetalles()) {
            DetalleVenta detalle = new DetalleVenta();
            Producto producto = productoRepository.findById(detalleDTO.getProductoId() )
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
            detalle.setSubtotal(detalleDTO.getPrecioUnitario() * detalleDTO.getCantidad());
            detalle.setVenta(venta);
            
            detalles.add(detalle);
            totalVenta += detalle.getSubtotal();
        }
        
        // Obtener el último número de serie y incrementarlo
        String ultimoNumeroSerie = obtenerUltimoNumeroSerie();
        Long nuevoNumeroSerie = Long.parseLong(ultimoNumeroSerie) + 1;//quitar esto

        // Asignar el nuevo número de serie
        venta.setNumeroSerie(String.format("%06d", nuevoNumeroSerie));  // Asegura que tenga 6 dígitos

        
        // Guardar los detalles
        detalleVentaRepository.saveAll(detalles);
        
        // Actualizar el total de la venta
        venta.setTotalVenta(totalVenta);
        venta.setDetallesVenta(detalles);
        return ventaRepository.save(venta);
    }

    public Venta findLastVenta() {
        return ventaRepository.findLastVenta();
    }

    public Venta cambiarEstado(Long idVenta) {
    	  Venta venta = ventaRepository.findById(idVenta)
    	            .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + idVenta));

    	    String estadoActual = venta.getEstado();

    	    if ("COMPLETADO".equalsIgnoreCase(estadoActual)) {
    	        // Cambiar a PENDIENTE y devolver stock
    	        venta.setEstado("PENDIENTE");

    	        for (DetalleVenta detalle : venta.getDetallesVenta()) {
    	            Producto producto = detalle.getProducto();
    	            producto.setStock(producto.getStock() + detalle.getCantidad());
    	            productoRepository.save(producto); // Actualiza el stock
    	        }

    	    } else if ("PENDIENTE".equalsIgnoreCase(estadoActual)) {
    	        // No se permite volver a COMPLETADO
    	        throw new RuntimeException("No se puede volver a completar una venta anulada. Registre una nueva venta.");
    	    }

    	    return ventaRepository.save(venta);
    }


    
}
