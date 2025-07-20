package com.tienda.stc.Clases;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DetalleCompra {
	
	
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long idDetalleCompra;

	  @ManyToOne
	    @JoinColumn(name = "id_producto")
	    private Producto producto;  // Relación con Producto
	  
	    private String nombreProductos;
	    private String descripcionCompra;
	    private int cantidadCompra;
	    private double precioUnitario;
	    private double subtotalCompra;

	    @ManyToOne
	    @JoinColumn(name = "id_compra")
	    @JsonBackReference
	    private Compra compra;
	    
	    

		public Producto getProducto() {
			return producto;
		}

		public void setProducto(Producto producto) {
			this.producto = producto;
		}

		public Long getIdDetalleCompra() {
			return idDetalleCompra;
		}

		public void setIdDetalleCompra(Long idDetalleCompra) {
			this.idDetalleCompra = idDetalleCompra;
		}

		public String getNombreProductos() {
			return nombreProductos;
		}

		public void setNombreProductos(String nombreProductos) {
			this.nombreProductos = nombreProductos;
		}

		public String getDescripcionCompra() {
			return descripcionCompra;
		}

		public void setDescripcionCompra(String descripcionCompra) {
			this.descripcionCompra = descripcionCompra;
		}

		public int getCantidadCompra() {
			return cantidadCompra;
		}

		public void setCantidadCompra(int cantidadCompra) {
			this.cantidadCompra = cantidadCompra;
		}

		public double getPrecioUnitario() {
			return precioUnitario;
		}

		public void setPrecioUnitario(double precioUnitario) {
			this.precioUnitario = precioUnitario;
		}

		public double getSubtotalCompra() {
			return subtotalCompra;
		}

		public void setSubtotalCompra(double subtotalCompra) {
			this.subtotalCompra = subtotalCompra;
		}

		public Compra getCompra() {
			return compra;
		}

		public void setCompra(Compra compra) {
			this.compra = compra;
		}
	    
	    
	    
	    
}
