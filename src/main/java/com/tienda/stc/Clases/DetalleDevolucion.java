package com.tienda.stc.Clases;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DetalleDevolucion {
	
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long idDetalleDevolucion;

	    private String producto;

	    private int cantidadDevuelta;
	    
	    private Double precioUnitario;

	    private String motivoDetallado; // Opcional: para anotar razón específica o estado

	    @ManyToOne
	    @JoinColumn(name = "id_devolucion")
	    @JsonBackReference
	    private Devolucion devolucion;

	    @ManyToOne(cascade = CascadeType.MERGE)  // O CascadeType.ALL si quieres que todas las operaciones de persistencia sean propagadas
	    @JoinColumn(name = "id_detalle_compra", nullable = true)
	    private DetalleCompra detalleCompra;
	    

	    // Relación con venta (opcional)
	    @ManyToOne(cascade = CascadeType.MERGE)
	    @JoinColumn(name = "id_detalle_venta", nullable = true)
	    private DetalleVenta detalleVenta;
	    
	    
	    
		public DetalleVenta getDetalleVenta() {
			return detalleVenta;
		}

		public void setDetalleVenta(DetalleVenta detalleVenta) {
			this.detalleVenta = detalleVenta;
		}

		public DetalleCompra getDetalleCompra() {
			return detalleCompra;
		}

		public void setDetalleCompra(DetalleCompra detalleCompra) {
			this.detalleCompra = detalleCompra;
		}

		public Long getIdDetalleDevolucion() {
			return idDetalleDevolucion;
		}

		public void setIdDetalleDevolucion(Long idDetalleDevolucion) {
			this.idDetalleDevolucion = idDetalleDevolucion;
		}

		public String getProducto() {
			return producto;
		}

		public void setProducto(String producto) {
			this.producto = producto;
		}

		public int getCantidadDevuelta() {
			return cantidadDevuelta;
		}

		public void setCantidadDevuelta(int cantidadDevuelta) {
			this.cantidadDevuelta = cantidadDevuelta;
		}

		public Double getPrecioUnitario() {
			return precioUnitario;
		}

		public void setPrecioUnitario(Double precioUnitario) {
			this.precioUnitario = precioUnitario;
		}

		public String getMotivoDetallado() {
			return motivoDetallado;
		}

		public void setMotivoDetallado(String motivoDetallado) {
			this.motivoDetallado = motivoDetallado;
		}

		public Devolucion getDevolucion() {
			return devolucion;
		}

		public void setDevolucion(Devolucion devolucion) {
			this.devolucion = devolucion;
		}

}
