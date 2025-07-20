package com.tienda.stc.Clases;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "promociones")
public class Promocion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPromocion;

    private String nombrePromo;

    private double precioOriginal;

    private double descuento;

    private double precioPromo;

    private LocalDate fechaInicio;

    private LocalDateTime fechaFin;

    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

	public Long getIdPromocion() {
		return idPromocion;
	}

	public void setIdPromocion(Long idPromocion) {
		this.idPromocion = idPromocion;
	}

	public String getNombrePromo() {
		return nombrePromo;
	}

	public void setNombrePromo(String nombrePromo) {
		this.nombrePromo = nombrePromo;
	}

	public double getPrecioOriginal() {
		return precioOriginal;
	}

	public void setPrecioOriginal(double precioOriginal) {
		this.precioOriginal = precioOriginal;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public double getPrecioPromo() {
		return precioPromo;
	}

	public void setPrecioPromo(double precioPromo) {
		this.precioPromo = precioPromo;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}



	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
    
    
    
}

