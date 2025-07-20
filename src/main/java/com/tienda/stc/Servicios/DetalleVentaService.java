package com.tienda.stc.Servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.stc.Clases.DetalleVenta;
import com.tienda.stc.Clases.DetallesVentaResponseDTO;
import com.tienda.stc.Clases.ReporteVentaDTO;
import com.tienda.stc.Repositorios.DetalleVentaRepository;
import com.tienda.stc.Repositorios.VentaProyeccion;


@Service
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    
    
    //reporte 1
    public List<ReporteVentaDTO> obtenerReporteVentas(LocalDate fechaInicio, LocalDate fechaFin) {
        return detalleVentaRepository.obtenerReporteVentas(fechaInicio, fechaFin);
    }
    
    //reporte 2
    /*
    public List<ReporteVentaDTO> obtenerReporteVentas(LocalDate fechaInicio, LocalDate fechaFin, Long idEmpleado) {
        return detalleVentaRepository.obtenerReporteVentasFiltrado(fechaInicio, fechaFin, idEmpleado);
    }*/

    
    
    
    public List<DetalleVenta> listarTodosLosDetalles() {
        return detalleVentaRepository.findAll();
    }

    public List<DetalleVenta> listarDetallesPorVenta(Long ventaId) {
        return detalleVentaRepository.findByVentaId(ventaId);
    }

    public DetalleVenta guardarDetalle(DetalleVenta detalle) {
        return detalleVentaRepository.save(detalle);
    }
    
    public void eliminarDetalle(Long id) {
        detalleVentaRepository.deleteById(id);
    }

    public DetalleVenta actualizarDetalle(Long id, DetalleVenta detalleActualizado) {
        DetalleVenta detalle = detalleVentaRepository.findById(id).orElseThrow();
        detalle.setCantidad(detalleActualizado.getCantidad());
        detalle.setPrecioUnitario(detalleActualizado.getPrecioUnitario());
        detalle.setSubtotal(detalleActualizado.getCantidad() * detalle.getPrecioUnitario());
        return detalleVentaRepository.save(detalle);
    }
    
    public List<DetallesVentaResponseDTO> obtenerDetallesVenta() {
        List<DetalleVenta> detalles = detalleVentaRepository.findAll();
        List<DetallesVentaResponseDTO> respuesta = new ArrayList<>();

        for (DetalleVenta detalle : detalles) {
            DetallesVentaResponseDTO dto = new DetallesVentaResponseDTO();

            dto.setNumeroSerie(detalle.getVenta().getNumeroSerie());
            dto.setFechaVenta(detalle.getVenta().getFechaVenta());
            dto.setEstadoVenta(detalle.getVenta().getEstado());

            dto.setNombreCliente(detalle.getVenta().getCliente().getNombre());
            dto.setApellidosCliente(detalle.getVenta().getCliente().getApellidos());
            dto.setNombreEmpleado(detalle.getVenta().getEmpleado().getNombre());
            dto.setApellidosEmpleado(detalle.getVenta().getEmpleado().getApellidos());

            dto.setNombreProducto(detalle.getProducto().getNombre());
            dto.setMarcaProducto(detalle.getProducto().getMarca());
            dto.setPrecioUnitario(detalle.getPrecioUnitario());
            dto.setCantidad(detalle.getCantidad());
            dto.setTotal(detalle.getSubtotal());
         

            respuesta.add(dto);
        }

        return respuesta;
    }

    /*
    public List<VentaProyeccion> listarVentas() {
        return ventaRepository.listarVentasProyeccion();
    }
*/
    
    
    
    
}
