package com.tienda.stc.Controladores;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.stc.Clases.DetalleVenta;
import com.tienda.stc.Clases.DetallesVentaResponseDTO;
import com.tienda.stc.Clases.ReporteVentaDTO;
import com.tienda.stc.Servicios.DetalleVentaService;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/detalle-ventas")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService detalleVentaService;
    
    
    
    @GetMapping("/ventas")
    public ResponseEntity<List<ReporteVentaDTO>> getReporteVentas(
        @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
    	System.out.println("fecha inicio:: " + fechaInicio);
    	System.out.println("fecha Fin:: " + fechaFin);
        List<ReporteVentaDTO> reporte = detalleVentaService.obtenerReporteVentas(fechaInicio, fechaFin);
        if (reporte.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reporte);
    }
    /*
    
    @GetMapping("/ventas2")
    public ResponseEntity<List<ReporteVentaDTO>> getReporteVentas(
        @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
        @RequestParam(value = "idEmpleado", required = false) Long idEmpleado
    ) {
        List<ReporteVentaDTO> reporte = detalleVentaService.obtenerReporteVentas(fechaInicio, fechaFin, idEmpleado);
        if (reporte.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reporte);
    }
*/
    
    /*
    @GetMapping("/listaDetalleCompleto")
    public ResponseEntity<List<DetalleVenta>> obtenerTodosLosDetallesVenta() {
        
    }
*/
    
    @GetMapping("/listaDetalle")
    public ResponseEntity<List<DetalleVenta>> obtenerTodosLosDetalles() {
        List<DetalleVenta> detalles = detalleVentaService.listarTodosLosDetalles();
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/venta/{ventaId}")
    public List<DetalleVenta> listarDetallesPorVenta(@PathVariable Long ventaId) {
        return detalleVentaService.listarDetallesPorVenta(ventaId);
    }

    @PutMapping("/{id}")
    public DetalleVenta actualizarDetalle(@PathVariable Long id, @RequestBody DetalleVenta detalleActualizado) {
        return detalleVentaService.actualizarDetalle(id, detalleActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDetalle(@PathVariable Long id) {
        detalleVentaService.eliminarDetalle(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/listaDetalleCompleto")
    public ResponseEntity<List<DetallesVentaResponseDTO>> obtenerDetallesVenta() {
        return ResponseEntity.ok(detalleVentaService.obtenerDetallesVenta());
    }

}

