package com.tienda.stc.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.stc.Clases.Cambio;
import com.tienda.stc.Clases.Pago;
import com.tienda.stc.Servicios.CambioService;

@RestController
@RequestMapping("/api/cambios")
@CrossOrigin(origins = "*") // Puedes restringir esto a tu frontend si lo deseas
public class CambioController {


    @Autowired
    private CambioService cambioService;

    @PostMapping
    public ResponseEntity<Cambio> registrarCambio(@RequestBody Cambio cambio) {
        Cambio cambioGuardado = cambioService.registrarCambio(cambio);
        return ResponseEntity.ok(cambioGuardado);
    }

    @GetMapping
    public ResponseEntity<List<Cambio>> listarCambios() {
        return ResponseEntity.ok(cambioService.listarCambios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cambio> obtenerCambioPorId(@PathVariable Long id) {
        Cambio cambio = cambioService.obtenerPorId(id);
        if (cambio != null) {
            return ResponseEntity.ok(cambio);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/cambiar-estado/{id}")
    public ResponseEntity<Cambio> cambiarEstado(@PathVariable Long id) {
        Cambio cambioActualizado = cambioService.cambiarEstado(id);
        return ResponseEntity.ok(cambioActualizado);
    }
}
