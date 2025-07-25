package com.tienda.stc.Clases;


import lombok.Data;

@Data
public class DetalleVentaDTO {
    private Long productoId;
    private Integer cantidad;
    private Double precioUnitario;
    
    
	public Long getProductoId() {
		return productoId;
	}
	public void setProductoId(Long productoId) {
		this.productoId = productoId;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Double getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
    
    
}
