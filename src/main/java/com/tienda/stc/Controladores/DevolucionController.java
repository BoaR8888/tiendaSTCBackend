package com.tienda.stc.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.stc.Clases.Compra;
import com.tienda.stc.Clases.Devolucion;
import com.tienda.stc.Servicios.DevolucionService;

@RestController
@RequestMapping("/api/devoluciones")
@CrossOrigin("*")
public class DevolucionController {

    @Autowired
    private DevolucionService devolucionService;

    // Crear devolución
    @PostMapping
    public Devolucion crearDevolucion(@RequestBody Devolucion devolucion) {
        return devolucionService.crearDevolucion(devolucion);
    }
    
    @PostMapping("/devoluciones/venta")
    public Devolucion crearDevolucionVenta(@RequestBody Devolucion devolucion) {
        return devolucionService.crearDevolucionVenta(devolucion);
    }

    
    @GetMapping
    public List<Devolucion> obtenerTodasLasCompras() {
        return devolucionService.listarDevoluciones();
    }

    // Obtener devolución por ID
    @GetMapping("/{id}")
    public Devolucion obtenerDevolucionPorId(@PathVariable Long id) {
        return devolucionService.obtenerDevolucionPorId(id);
    }

    // Obtener devoluciones por compra
    @GetMapping("/compra/{idCompra}")
    public List<Devolucion> obtenerDevolucionesPorCompra(@PathVariable Long idCompra) {
        return devolucionService.obtenerDevolucionesPorCompra(idCompra);
    }

    // Obtener devoluciones por venta
    @GetMapping("/venta/{idVenta}")
    public List<Devolucion> obtenerDevolucionesPorVenta(@PathVariable Long idVenta) {
        return devolucionService.obtenerDevolucionesPorVenta(idVenta);
    }

    // Actualizar devolución
    @PutMapping("/{id}")
    public Devolucion actualizarDevolucion(@PathVariable Long id, @RequestBody Devolucion devolucion) {
        return devolucionService.actualizarDevolucion(id, devolucion);
    }
    
    
    @PutMapping("/cancelar/{id}")
    public Devolucion cancelarDevolucion(@PathVariable Long id) {
        return devolucionService.cancelarDevolucion(id);
    }

    

    // Eliminar devolución
    @DeleteMapping("/{id}")
    public boolean eliminarDevolucion(@PathVariable Long id) {
        return devolucionService.eliminarDevolucion(id);
    }
}