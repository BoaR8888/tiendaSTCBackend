package com.tienda.stc.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tienda.stc.Clases.Compra;
import com.tienda.stc.Clases.DetalleCompra;
import com.tienda.stc.Clases.DetalleDevolucion;
import com.tienda.stc.Clases.DetalleVenta;
import com.tienda.stc.Clases.Devolucion;
import com.tienda.stc.Clases.Producto;
import com.tienda.stc.Repositorios.DetalleCompraRepository;
import com.tienda.stc.Repositorios.DetalleDevolucionRepository;
import com.tienda.stc.Repositorios.DetalleVentaRepository;
import com.tienda.stc.Repositorios.DevolucionRepository;
import com.tienda.stc.Repositorios.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class DevolucionService {

    @Autowired
    private DevolucionRepository devolucionRepository;
    @Autowired
    private ProductoService productoService; // Servicio para manejar el stock
  
    
    @Autowired
    private ProductoRepository productoRepository; // Servicio para manejar el stock
  
    @Autowired
    private DetalleDevolucionRepository detalleDevolucionRepository;
    
    @Autowired
    private DetalleCompraRepository detalleCompraRepository;
    
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    
    
    @Transactional
    public Devolucion crearDevolucion(Devolucion devolucion) {
        try {
            // 1. Vinculamos correctamente cada DetalleDevolucion con un DetalleCompra EXISTENTE
            for (DetalleDevolucion detalle : devolucion.getProductosDevueltos()) {
                DetalleCompra detalleCompraExistente = detalleCompraRepository.findById(detalle.getDetalleCompra().getIdDetalleCompra())
                    .orElseThrow(() -> new RuntimeException("DetalleCompra no encontrado con ID: " + detalle.getDetalleCompra().getIdDetalleCompra()));

                // Asegurar que se apunte al objeto persistido
                detalle.setDetalleCompra(detalleCompraExistente);
            }

            // 2. Guardamos la devolución una vez que todos los detalles están bien configurados
            Devolucion devolucionGuardada = devolucionRepository.save(devolucion);
            System.out.println("Devolución guardada: " + devolucionGuardada);

            // 3. Procesamos la lógica de stock y guardamos detalle de devolución
            for (DetalleDevolucion detalle : devolucionGuardada.getProductosDevueltos()) {
                DetalleCompra detalleCompraExistente = detalle.getDetalleCompra();

                Long idProducto = detalleCompraExistente.getProducto() != null
                    ? detalleCompraExistente.getProducto().getId()
                    : null;

                if (idProducto == null) {
                    System.err.println("Producto no encontrado en el detalleCompra con ID: " + detalleCompraExistente.getIdDetalleCompra());
                    continue;
                }

                Producto producto = productoService.buscarProductoPorId(idProducto);
                if (producto == null) {
                    System.err.println("Producto con ID " + idProducto + " no encontrado en la base de datos");
                    continue;
                }

                int nuevoStock = producto.getStock() - detalle.getCantidadDevuelta();
                producto.setStock(nuevoStock);
                productoService.guardarProducto(producto);

                System.out.println("Producto actualizado: " + producto.getNombre() + " | Nuevo stock: " + producto.getStock());

                detalleDevolucionRepository.save(detalle);
            }

            System.out.println("Devolución procesada exitosamente");
            return devolucionGuardada;

        } catch (Exception e) {
            System.err.println("Error al procesar la devolución: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar la devolución", e);
        }
    }


    
    
    @Transactional
    public Devolucion crearDevolucionVenta(Devolucion devolucion) {
        try {
            for (DetalleDevolucion detalle : devolucion.getProductosDevueltos()) {
                // Verificar que venga el ID del detalleVenta
                DetalleVenta detalleVenta = detalleVentaRepository.findById(detalle.getDetalleVenta().getId())
                    .orElseThrow(() -> new RuntimeException("DetalleVenta no encontrado con ID: " + detalle.getDetalleVenta().getId()));

                // Asociar el objeto persistido
                detalle.setDetalleVenta(detalleVenta);
            }

            // Guardar la devolución
            Devolucion devolucionGuardada = devolucionRepository.save(devolucion);
            System.out.println("Devolución de venta guardada: " + devolucionGuardada);

            // Procesar stock
            for (DetalleDevolucion detalle : devolucionGuardada.getProductosDevueltos()) {
                DetalleVenta detalleVenta = detalle.getDetalleVenta();
                Producto producto = detalleVenta.getProducto();

                if (producto == null) {
                    System.err.println("Producto no encontrado en el detalleVenta con ID: " + detalleVenta.getId());
                    continue;
                }

                int nuevoStock = producto.getStock() + detalle.getCantidadDevuelta(); // Se devuelve al stock
                producto.setStock(nuevoStock);
                productoService.guardarProducto(producto);

                detalleDevolucionRepository.save(detalle);
            }

            return devolucionGuardada;

        } catch (Exception e) {
            System.err.println("Error al procesar la devolución de venta: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar la devolución de venta", e);
        }
    }




    // Crear devolución
 /*   
    public Devolucion crearDevolucion(Devolucion devolucion) {
        return devolucionRepository.save(devolucion);
    }*/

    // Obtener devolución por ID
    public Devolucion obtenerDevolucionPorId(Long idDevolucion) {
        return devolucionRepository.findById(idDevolucion).orElse(null);
    }

    // Obtener devoluciones por compra
    public List<Devolucion> obtenerDevolucionesPorCompra(Long idCompra) {
        return devolucionRepository.findByCompraIdCompra(idCompra);
    }

    // Obtener devoluciones por venta
    public List<Devolucion> obtenerDevolucionesPorVenta(Long idVenta) {
        return devolucionRepository.findByVentaId(idVenta);
    }
    
    public List<Devolucion> listarDevoluciones() {
        return devolucionRepository.findAllByOrderByIdDevolucionDesc();
    }

    /* Actualizar devolución
    public Devolucion actualizarDevolucion(Long idDevolucion, Devolucion devolucion) {
        if (devolucionRepository.existsById(idDevolucion)) {
            devolucion.setIdDevolucion(idDevolucion);
            return devolucionRepository.save(devolucion);
        }
        return null; // Retornamos null si no se encuentra
    }*/

    
    
    
    public Devolucion actualizarDevolucion(Long idDevolucion, Devolucion devolucion) {
        Optional<Devolucion> devolucionExistenteOpt = devolucionRepository.findById(idDevolucion);

        if (devolucionExistenteOpt.isPresent()) {
            // Establecer el ID
            devolucion.setIdDevolucion(idDevolucion);

            // Verifica si debe aumentar el stock
            if (devolucion.getTipoCompensacion() == 2 && devolucion.getEstadoDevolucion() == 2) {
            	System.out.println("REPOSICION Y PROCESADO"+ devolucion.getTipoCompensacion() +" --- "+ devolucion.getEstadoDevolucion());
                if (devolucion.getProductosDevueltos() != null) {
                    for (DetalleDevolucion detalle : devolucion.getProductosDevueltos()) {
                        if (detalle.getDetalleCompra() != null &&
                            detalle.getDetalleCompra().getIdDetalleCompra() != null) {

                            DetalleCompra detalleCompraReal = detalleCompraRepository
                                .findById(detalle.getDetalleCompra().getIdDetalleCompra())
                                .orElseThrow(() -> new RuntimeException("DetalleCompra no encontrado"));

                            detalle.setDetalleCompra(detalleCompraReal);


                            
                            
                            Producto producto = detalleCompraReal.getProducto();
                            if (producto != null) {
                                int cantidadDevuelta = detalle.getCantidadDevuelta();
                            	System.out.println("stock antes"+ producto.getNombre()+" "+ producto.getModelo()  +" "+ producto.getMarca() +"STOCK = "+ producto.getStock());
                                producto.setStock(producto.getStock() + cantidadDevuelta);
                               // productoRepository.save(producto); // Actualiza el stock en DB
                                productoService.guardarProducto(producto);
                                System.out.println("stock antes"+ producto.getNombre()+" "+ producto.getModelo()  +" "+ producto.getMarca() +"STOCK = "+ producto.getStock());
                            }
                        }

                        detalle.setDevolucion(devolucion); // Mantener relación bidireccional
                    }
                }
            } else {
                // Si no es reposición o no está procesado, igual actualizar relaciones
                if (devolucion.getProductosDevueltos() != null) {
                    for (DetalleDevolucion detalle : devolucion.getProductosDevueltos()) {
                        if (detalle.getDetalleCompra() != null &&
                            detalle.getDetalleCompra().getIdDetalleCompra() != null) {
                        		
                        
                            DetalleCompra detalleCompraReal = detalleCompraRepository
                                .findById(detalle.getDetalleCompra().getIdDetalleCompra())
                                .orElseThrow(() -> new RuntimeException("DetalleCompra no encontrado"));

                            detalle.setDetalleCompra(detalleCompraReal);
                        }
                        detalle.setDevolucion(devolucion);
                    }
                }
            }

         // Convertir detalleCompensacion a mayúsculas si no es null
            if (devolucion.getDetalleCompensacion() != null) {
                devolucion.setDetalleCompensacion(devolucion.getDetalleCompensacion().toUpperCase());
            }

            // Finalmente, guarda y devuelve la devolución actualizada
            return devolucionRepository.save(devolucion);
        }

        return null; // o lanza una excepción si prefieres
    }

    
    
    
    
    /*
public Devolucion actualizarDevolucion(Long idDevolucion, Devolucion devolucion) {
    Optional<Devolucion> devolucionExistenteOpt = devolucionRepository.findById(idDevolucion);

    if (devolucionExistenteOpt.isPresent()) {
        // Establecer el ID de la devolución existente
        devolucion.setIdDevolucion(idDevolucion);

        // Procesar la lista de productos devueltos
        if (devolucion.getProductosDevueltos() != null) {
            for (DetalleDevolucion detalle : devolucion.getProductosDevueltos()) {

                // Reconectar el detalleCompra por ID si existe
                if (detalle.getDetalleCompra() != null &&
                    detalle.getDetalleCompra().getIdDetalleCompra() != null) {

                    DetalleCompra detalleCompraReal = detalleCompraRepository
                        .findById(detalle.getDetalleCompra().getIdDetalleCompra())
                        .orElseThrow(() -> new RuntimeException("DetalleCompra no encontrado"));

                    detalle.setDetalleCompra(detalleCompraReal);
                }

                // Establecer la relación bidireccional correcta
                detalle.setDevolucion(devolucion);
            }
        }

        // Guardar y devolver la devolución actualizada
        return devolucionRepository.save(devolucion);
    }

    return null; // o lanzar una excepción si prefieres
}*/

    public Devolucion cancelarDevolucion(Long id) {
        Devolucion devolucion = devolucionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Devolución no encontrada con ID: " + id));

        int tipoCompensacion = devolucion.getTipoCompensacion();
        int estado = devolucion.getEstadoDevolucion();

        // Caso 1: Tipo compensación 1 o 2 y estado pendiente
        if ((tipoCompensacion == 1 || tipoCompensacion == 2) && estado == 1) {
            // Aumentar stock por cada producto devuelto
            if (devolucion.getProductosDevueltos() != null) {
                for (DetalleDevolucion detalle : devolucion.getProductosDevueltos()) {
                    DetalleCompra detalleCompra = detalle.getDetalleCompra();

                    if (detalleCompra != null && detalleCompra.getProducto() != null) {
                        Producto producto = detalleCompra.getProducto();
                        producto.setStock(producto.getStock() + detalle.getCantidadDevuelta());
                        productoRepository.save(producto);
                    }
                }
            }

            // Cambiar estado a cancelado (3)
            devolucion.setEstadoDevolucion(3);
            return devolucionRepository.save(devolucion);
        }

        // Caso 2: Tipo compensación 1 o 2 y estado procesado
        if ((tipoCompensacion == 1 || tipoCompensacion == 2) && estado == 2) {
            devolucion.setEstadoDevolucion(3);
            return devolucionRepository.save(devolucion);
        }

        throw new RuntimeException("La devolución no puede ser cancelada. Solo se pueden cancelar devoluciones pendientes o procesadas con tipo de compensación válido.");
    }


    // Eliminar devolución
    public boolean eliminarDevolucion(Long idDevolucion) {
        if (devolucionRepository.existsById(idDevolucion)) {
            devolucionRepository.deleteById(idDevolucion);
            return true;
        }
        return false; // Retornamos false si no se encuentra
    }
}