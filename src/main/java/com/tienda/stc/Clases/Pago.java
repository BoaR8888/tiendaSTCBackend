package com.tienda.stc.Clases;

import java.time.LocalDate;

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
public class Pago {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long idPago;

	    private String tipoPago; // Ej: "SERVICIO", "OPERACIONAL", "ALIMENTACION"

	    private String detallePago;

	    private Double monto;

	    private LocalDate fecha;
	    
	    
	    private int estadoPago; // 1 = activa, 0 = anulada

	    @ManyToOne
	    @JoinColumn(name = "id_empleado_pagado")
	    private Empleado empleadoPagado; // Puede ser null

	    @ManyToOne(optional = false)
	    @JoinColumn(name = "id_empleado_pagador")
	    private Empleado empleadoPagador; // Obligatorio

		public Long getIdPago() {
			return idPago;
		}

		public void setIdPago(Long idPago) {
			this.idPago = idPago;
		}

		public String getTipoPago() {
			return tipoPago;
		}

		public void setTipoPago(String tipoPago) {
			this.tipoPago = tipoPago;
		}

		public String getDetallePago() {
			return detallePago;
		}

		public void setDetallePago(String detallePago) {
			this.detallePago = detallePago;
		}

		public Double getMonto() {
			return monto;
		}

		public void setMonto(Double monto) {
			this.monto = monto;
		}

		public LocalDate getFecha() {
			return fecha;
		}

		public void setFecha(LocalDate fecha) {
			this.fecha = fecha;
		}

		public Empleado getEmpleadoPagado() {
			return empleadoPagado;
		}

		public void setEmpleadoPagado(Empleado empleadoPagado) {
			this.empleadoPagado = empleadoPagado;
		}

		public Empleado getEmpleadoPagador() {
			return empleadoPagador;
		}

		public void setEmpleadoPagador(Empleado empleadoPagador) {
			this.empleadoPagador = empleadoPagador;
		}

		public int getEstadoPago() {
			return estadoPago;
		}

		public void setEstadoPago(int estadoPago) {
			this.estadoPago = estadoPago;
		}

		
	    

}
