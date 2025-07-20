package com.tienda.stc.Servicios;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tienda.stc.Clases.Producto;
import com.tienda.stc.Clases.Promocion;
import com.tienda.stc.Repositorios.ProductoRepository;
import com.tienda.stc.Repositorios.PromocionRepository;

@Service
public class PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Scheduled(cron = "0 0 0 * * ?") // Cada día a las 00:00
    public void desactivarPromocionesVencidas() {
        List<Promocion> promociones = promocionRepository.findAll();
        LocalDateTime ahora = LocalDateTime.now();

        for (Promocion promo : promociones) {
            if (promo.getFechaFin().isBefore(ahora) && promo.isEstado()) {
                promo.setEstado(false);
                promocionRepository.save(promo);
                System.out.println("✔️ Promoción desactivada automáticamente: ID " + promo.getIdPromocion());
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * ?") // Cada día a las 00:00
    public void activarPromocionesVigentes() {
        List<Promocion> promociones = promocionRepository.findAll();
        LocalDateTime ahora = LocalDateTime.now();

        for (Promocion promo : promociones) {
            // Si la fecha inicio ya llegó o pasó, la fecha fin no pasó, y está inactiva
            if (!promo.isEstado() && 
                (promo.getFechaInicio().atStartOfDay().isBefore(ahora) || promo.getFechaInicio().atStartOfDay().isEqual(ahora)) && 
                (promo.getFechaFin().isAfter(ahora) || promo.getFechaFin().isEqual(ahora))) {

                promo.setEstado(true);
                promocionRepository.save(promo);
                System.out.println("✔️ Promoción activada automáticamente: ID " + promo.getIdPromocion());
            }
        }
    }
    
    public Promocion guardarPromocion(Promocion promocion) {
        LocalDateTime hoy = LocalDateTime.now(); // Fecha actual

        if (promocion.getFechaInicio().atStartOfDay().isAfter(hoy)) {
            // Si la fecha de inicio es en el futuro → inactiva
            promocion.setEstado(false);
        } else if ((promocion.getFechaInicio().atStartOfDay().isBefore(hoy) || promocion.getFechaInicio().atStartOfDay().isEqual(hoy)) &&
                   (promocion.getFechaFin().isAfter(hoy) || promocion.getFechaFin().isEqual(hoy))) {
            // Si hoy está entre la fecha de inicio y fin → activa
            promocion.setEstado(true);
        } else {
            // Si la fecha fin ya pasó → inactiva
            promocion.setEstado(false);
        }

        return promocionRepository.save(promocion);
    }


    public List<Promocion> listarPromociones() {
       // return promocionRepository.findAll();
        return promocionRepository.findAllByOrderByIdPromocionDesc();
    }
/*
    public Promocion guardarPromocion(Promocion promocion) {
        return promocionRepository.save(promocion);
    }
*/
    public Optional<Promocion> obtenerPromocionPorId(Long id) {
        return promocionRepository.findById(id);
    }

    public Optional<Promocion> obtenerPromocionActivaPorProducto(Long productoId) {
        return promocionRepository.findByProductoIdAndEstadoTrue(productoId);
    }

    public void eliminarPromocion(Long id) {
        promocionRepository.deleteById(id);
    }
    


    public Optional<Double> obtenerPrecioConPromocion(Long productoId) {
        Optional<Producto> productoOpt = productoRepository.findById(productoId);
        if (productoOpt.isEmpty()) {
            return Optional.empty();
        }

        Producto producto = productoOpt.get();

        // Asegúrate de tener este método en tu repositorio
        Optional<Promocion> promoOpt = promocionRepository.findByProductoIdAndEstadoTrue(productoId);

        if (promoOpt.isPresent()) {
            Promocion promocion = promoOpt.get();
            double descuento = producto.getPrecioVenta() * (promocion.getDescuento() / 100.0);
            double precioConDescuento = producto.getPrecioVenta() - descuento;
            return Optional.of(precioConDescuento);
        }

        return Optional.empty(); // No hay promoción activa
    }
    
    // Método para actualizar la promoción
    public Promocion actualizarPromocion(Promocion promocion) {
        Optional<Promocion> promocionExistente = promocionRepository.findById(promocion.getIdPromocion());
        
        if (promocionExistente.isPresent()) {
            Promocion promocionGuardada = promocionExistente.get();
            // Actualizamos los campos de la promoción con los valores del objeto promocion recibido
            promocionGuardada.setNombrePromo(promocion.getNombrePromo());
            promocionGuardada.setPrecioOriginal(promocion.getPrecioOriginal());
            promocionGuardada.setDescuento(promocion.getDescuento());
            promocionGuardada.setPrecioPromo(promocion.getPrecioPromo());
            promocionGuardada.setFechaInicio(promocion.getFechaInicio());
            promocionGuardada.setFechaFin(promocion.getFechaFin());
            
            // Guardamos los cambios en la base de datos
            return promocionRepository.save(promocionGuardada);
        } else {
            throw new RuntimeException("Promoción no encontrada con ID: " + promocion.getIdPromocion());
        }
    }
    
    public List<Promocion> listarPromocionesActivas() {
        return promocionRepository.findByEstadoTrue();
    }
    
    

    public void cambiarEstado(Long id) {
        Promocion promocion = promocionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Promoción no encontrada"));

        // Solo cambiar si el estado es true (activo)
        if (promocion.isEstado()) {
            promocion.setEstado(false); // Dar de baja
            promocionRepository.save(promocion);
        }
        // Si ya está en false (inactivo), no se hace nada
    }


    // Modificar promoción existente
    public Promocion modificarPromocion(Long id, Promocion nuevaPromocion) {
        Promocion promocion = promocionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Promoción no encontrada"));

        promocion.setNombrePromo(nuevaPromocion.getNombrePromo());
        promocion.setPrecioOriginal(nuevaPromocion.getPrecioOriginal());
        promocion.setDescuento(nuevaPromocion.getDescuento());
        promocion.setPrecioPromo(nuevaPromocion.getPrecioPromo());
        promocion.setFechaInicio(nuevaPromocion.getFechaInicio());
        promocion.setFechaFin(nuevaPromocion.getFechaFin());
        promocion.setEstado(nuevaPromocion.isEstado());
        promocion.setProducto(nuevaPromocion.getProducto());

        return promocionRepository.save(promocion);
    }
}