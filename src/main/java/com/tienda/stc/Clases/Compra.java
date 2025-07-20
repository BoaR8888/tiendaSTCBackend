package com.tienda.stc.Clases;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
public class Compra {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long idCompra;

	    @ManyToOne
	    @JoinColumn(name = "id_proveedor")
	    private Proveedor proveedor;

	    @ManyToOne
	    @JoinColumn(name = "id_empleado")
	    private Empleado empleado;

	    private String reciboCompra;

	    private LocalDate fechaCompra;

	    private double montoTotal;

	    
	    private int estadoCompra;

	    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
	    @JsonManagedReference
	    private List<DetalleCompra> productosComprados;

		public Long getIdCompra() {
			return idCompra;
		}

		public void setIdCompra(Long idCompra) {
			this.idCompra = idCompra;
		}

		

		public Proveedor getProveedor() {
			return proveedor;
		}

		public void setProveedor(Proveedor proveedor) {
			this.proveedor = proveedor;
		}

		public Empleado getEmpleado() {
			return empleado;
		}

		public void setEmpleado(Empleado empleado) {
			this.empleado = empleado;
		}

		public String getReciboCompra() {
			return reciboCompra;
		}

		public void setReciboCompra(String reciboCompra) {
			this.reciboCompra = reciboCompra;
		}

		public LocalDate getFechaCompra() {
			return fechaCompra;
		}

		public void setFechaCompra(LocalDate fechaCompra) {
			this.fechaCompra = fechaCompra;
		}

		public double getMontoTotal() {
			return montoTotal;
		}

		public void setMontoTotal(double montoTotal) {
			this.montoTotal = montoTotal;
		}

		public int getEstadoCompra() {
			return estadoCompra;
		}

		public void setEstadoCompra(int estadoCompra) {
			this.estadoCompra = estadoCompra;
		}

		public List<DetalleCompra> getProductosComprados() {
			return productosComprados;
		}

		public void setProductosComprados(List<DetalleCompra> productosComprados) {
			this.productosComprados = productosComprados;
		}

	    // Getters y setters
	

}
