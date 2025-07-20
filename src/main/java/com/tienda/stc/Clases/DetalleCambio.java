package com.tienda.stc.Clases;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCambio {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleCambio;

   

    @ManyToOne
    @JoinColumn(name = "producto_original_id")
    private Producto productoOriginal;

    @ManyToOne
    @JoinColumn(name = "producto_cambiado_id")
    private Producto productoCambiado;

    private Integer cantidadDevuelta;
    private Integer cantidad;
    private Double precioOriginal;
    private Double precioNuevo;
    
    @ManyToOne
    @JoinColumn(name = "id_cambio")
    @JsonBackReference
    private Cambio cambio;

    
    
	public Integer getCantidadDevuelta() {
		return cantidadDevuelta;
	}

	public void setCantidadDevuelta(Integer cantidadDevuelta) {
		this.cantidadDevuelta = cantidadDevuelta;
	}

	public Long getIdDetalleCambio() {
		return idDetalleCambio;
	}

	public void setIdDetalleCambio(Long idDetalleCambio) {
		this.idDetalleCambio = idDetalleCambio;
	}

	public Producto getProductoOriginal() {
		return productoOriginal;
	}

	public void setProductoOriginal(Producto productoOriginal) {
		this.productoOriginal = productoOriginal;
	}

	public Producto getProductoCambiado() {
		return productoCambiado;
	}

	public void setProductoCambiado(Producto productoCambiado) {
		this.productoCambiado = productoCambiado;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecioOriginal() {
		return precioOriginal;
	}

	public void setPrecioOriginal(Double precioOriginal) {
		this.precioOriginal = precioOriginal;
	}

	public Double getPrecioNuevo() {
		return precioNuevo;
	}

	public void setPrecioNuevo(Double precioNuevo) {
		this.precioNuevo = precioNuevo;
	}

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}
    
    
}


