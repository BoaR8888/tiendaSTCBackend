package com.tienda.stc.Controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tienda.stc.Clases.Venta;
import com.tienda.stc.Clases.VentaDTO;
import com.tienda.stc.Servicios.VentaService;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/ventas")
public class VentaController {

@Autowired
    private VentaService ventaService;
/* 
    @PostMapping("/crear")
    public Venta crearVenta(@RequestBody Venta venta, @RequestBody List<DetalleVenta> detalles) {
        return ventaService.crearVenta(venta, detalles);
    }
        */
  

    @GetMapping("/{id}")
    public Venta obtenerVenta(@PathVariable Long id) {
        return ventaService.obtenerVentaPorId(id);
    }

    @PutMapping("/{id}")
    public Venta actualizarVenta(@PathVariable Long id, @RequestBody Venta ventaActualizada) {
        return ventaService.actualizarVenta(id, ventaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar")
    public List<Venta> listarVentas() {
        return ventaService.listarVentas();
    }
    
    @PostMapping("/crear")
    public ResponseEntity<Venta> crearVenta(@RequestBody Venta venta) {
        // Crear la venta y asignar el número de serie
        Venta nuevaVenta = ventaService.guardarVenta(venta);
        return ResponseEntity.ok(nuevaVenta);
    }
    
    @GetMapping("/ultimo-recibo")
    public ResponseEntity<String> obtenerUltimoRecibo() {
        String nuevoRecibo = ventaService.generarNumeroSerieVenta();
        return ResponseEntity.ok(nuevoRecibo);
    }
    
     @PostMapping
    public ResponseEntity<Venta> crearVenta(@RequestBody VentaDTO ventaDTO) {
        Venta nuevaVenta = ventaService.crearVentaCompleta(ventaDTO);
        return ResponseEntity.ok(nuevaVenta);
    }
    
    @GetMapping("/ultima")
    public ResponseEntity<Venta> obtenerUltimaVenta() {
        Venta ultimaVenta = ventaService.findLastVenta();
        return ResponseEntity.ok(ultimaVenta);
    }
    
    @GetMapping("/obtener-ultimo-numero-serie")
    public ResponseEntity<String> obtenerUltimoNumeroSerie() {
        String numeroSerie = ventaService.obtenerUltimoNumeroSerie();
        return ResponseEntity.ok(numeroSerie);
    }

    @PutMapping("/ventas/cambiar-estado/{id}")
    public ResponseEntity<Venta> cambiarEstado(@PathVariable Long id) {
        Venta ventaActualizada = ventaService.cambiarEstado(id);
        return ResponseEntity.ok(ventaActualizada);
    }

    
}
