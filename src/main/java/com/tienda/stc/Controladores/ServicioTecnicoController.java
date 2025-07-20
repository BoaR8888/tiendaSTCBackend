package com.tienda.stc.Controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tienda.stc.Clases.ServicioTecnico;
import com.tienda.stc.Repositorios.ServicioTecnicoRepository;
import com.tienda.stc.Servicios.ServicioTecnicoService;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/serviciosTecnicos")
public class ServicioTecnicoController {

    @Autowired
    private ServicioTecnicoService servicioTecnicoService;
    private ServicioTecnicoRepository servicioTecnicoRepository;

    // Endpoint para obtener todos los servicios técnicos
    @GetMapping
    public List<ServicioTecnico> getAllServiciosTecnicos() {
        return servicioTecnicoService.listarServiciosTecnicos();
    }

    // Endpoint para obtener un servicio técnico por ID
    @GetMapping("/{id}")
    public ResponseEntity<ServicioTecnico> obtenerPorId(@PathVariable Long id) {
        return servicioTecnicoService.obtenerPorId(id)
                .map(servicio -> ResponseEntity.ok(servicio))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para crear un nuevo servicio técnico
    @PostMapping("/guardar")
    public ServicioTecnico crearServicioTecnico(@RequestBody ServicioTecnico servicioTecnico) {
       System.out.println("LLEGO A GUARDAR SERVICIO TECNICO "+ servicioTecnico.getCliente().getIdCliente());
        return servicioTecnicoService.guardarServicioTecnico(servicioTecnico);
    }

    // Endpoint para actualizar un servicio técnico existente
    /*
    @PutMapping("/{id}")
    public ResponseEntity<ServicioTecnico> actualizar(@PathVariable Long id, @RequestBody ServicioTecnico detallesServicioTecnico) {
        return ResponseEntity.ok(servicioTecnicoService.actualizar(id, detallesServicioTecnico));
    }
*/
    @PutMapping("/{id}")
    public ResponseEntity<ServicioTecnico> actualizar(@PathVariable Long id, @RequestBody ServicioTecnico detallesServicioTecnico) {
        ServicioTecnico actualizado = servicioTecnicoService.actualizar(id, detallesServicioTecnico);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    // Endpoint para eliminar un servicio técnico por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicioTecnicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/anular-pago/{id}")
    public ResponseEntity<ServicioTecnico> anularEstadoPago(@PathVariable Long id) {
        try {
            ServicioTecnico actualizado = servicioTecnicoService.anularEstadoPago(id);
            return ResponseEntity.ok(actualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
