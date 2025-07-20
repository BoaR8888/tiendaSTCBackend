package com.tienda.stc.Clases;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.time.LocalDate;

import lombok.Data;

//@Data
public class VentaDTO {
	
	
    private LocalDate fechaVenta;
    private String numeroSerie;
    private String estado;
    private Long clienteId;
    private Long empleadoId;
    private List<DetalleVentaDTO> detalles;

   
    
    public LocalDate getFechaVenta() {
		return fechaVenta;
	}
	public void setFechaVenta(LocalDate fechaVenta) {
		this.fechaVenta = fechaVenta;
	}
	public String getNumeroSerie() {
        return numeroSerie;
    }
    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Long getClienteId() {
        return clienteId;
    }
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    public Long getEmpleadoId() {
        return empleadoId;
    }
    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }
    public List<DetalleVentaDTO> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetalleVentaDTO> detalles) {
        this.detalles = detalles;
    }
    public VentaDTO(LocalDate fechaVenta, String numeroSerie, String estado, Long clienteId, Long empleadoId,
            List<DetalleVentaDTO> detalles) {
        this.fechaVenta = fechaVenta;
        this.numeroSerie = numeroSerie;
        this.estado = estado;
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
        this.detalles = detalles;
    }

    
}