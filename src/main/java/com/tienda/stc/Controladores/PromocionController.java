package com.tienda.stc.Controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.tienda.stc.Clases.Promocion;
import com.tienda.stc.Servicios.PromocionService;

@RestController
@RequestMapping("/api/promociones")
@CrossOrigin(origins = "*")
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    @GetMapping
    public List<Promocion> listarPromociones() {
        return promocionService.listarPromociones();
    }

    @PostMapping
    public Promocion crearPromocion(@RequestBody Promocion promocion) {
        return promocionService.guardarPromocion(promocion);
    }

    @GetMapping("/{id}")
    public Optional<Promocion> obtenerPorId(@PathVariable Long id) {
        return promocionService.obtenerPromocionPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarPromocion(@PathVariable Long id) {
        promocionService.eliminarPromocion(id);
    }

    @GetMapping("/producto/{productoId}")
    public Optional<Promocion> obtenerPromocionActivaPorProducto(@PathVariable Long productoId) {
        return promocionService.obtenerPromocionActivaPorProducto(productoId);
    }
    
    @GetMapping("/precio/{productoId}")
    public ResponseEntity<Double> obtenerPrecioConPromocion(@PathVariable Long productoId) {
        Optional<Double> precioConPromocion = promocionService.obtenerPrecioConPromocion(productoId);

        // Si hay promoción, se devuelve el precio con descuento, si no, se devuelve null
        return ResponseEntity.ok(precioConPromocion.orElse(null));
    }
    
 // Ruta PUT para actualizar una promoción
    /*
    @PutMapping("/{id}")
    public ResponseEntity<Promocion> actualizarPromocion(@PathVariable("id") Long id, @RequestBody Promocion promocion) {
        // Asignamos el id recibido en la URL al objeto promocion
        promocion.setIdPromocion(id);

        try {
            Promocion promocionActualizada = promocionService.actualizarPromocion(promocion);
            return new ResponseEntity<>(promocionActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Si no se encuentra la promoción
        }
    }*/
    
    @GetMapping("/activas")
    public List<Promocion> obtenerPromocionesActivas() {
        return promocionService.listarPromocionesActivas();
    }
    
    @PutMapping("/estado/{id}")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id) {
        try {
            promocionService.cambiarEstado(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promocion> modificarPromocion(@PathVariable Long id, @RequestBody Promocion promocion) {
        try {
            Promocion promoModificada = promocionService.modificarPromocion(id, promocion);
            return ResponseEntity.ok(promoModificada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
