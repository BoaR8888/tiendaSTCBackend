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
public class Devolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDevolucion;

    private LocalDate fechaDevolucion;

    private String motivo;

    private int tipoCompensacion; 
    // 1 = Reembolso, 2 = Nota de crédito, 3 = Reposición

    private int estadoDevolucion; 
    // 1 = Pendiente, 2 = Procesado, 3 = Cancelado

    private Double montoCompensacion;

    private String detalleCompensacion;

    private LocalDate fechaCompensacion;

    @ManyToOne
    @JoinColumn(name = "id_compra", nullable = true)
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "id", nullable = true)
    private Venta venta;
    

    @OneToMany(mappedBy = "devolucion", cascade = CascadeType.ALL)
    @JsonManagedReference//esto quiero serializado
    private List<DetalleDevolucion> productosDevueltos;
    
    
    
    
    // Getters y Setters...
    
	public List<DetalleDevolucion> getProductosDevueltos() {
		return productosDevueltos;
	}

	public void setProductosDevueltos(List<DetalleDevolucion> productosDevueltos) {
		this.productosDevueltos = productosDevueltos;
	}

   

	public Long getIdDevolucion() {
		return idDevolucion;
	}

	public void setIdDevolucion(Long idDevolucion) {
		this.idDevolucion = idDevolucion;
	}

	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(LocalDate fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public int getTipoCompensacion() {
		return tipoCompensacion;
	}

	public void setTipoCompensacion(int tipoCompensacion) {
		this.tipoCompensacion = tipoCompensacion;
	}

	public int getEstadoDevolucion() {
		return estadoDevolucion;
	}

	public void setEstadoDevolucion(int estadoDevolucion) {
		this.estadoDevolucion = estadoDevolucion;
	}

	public Double getMontoCompensacion() {
		return montoCompensacion;
	}

	public void setMontoCompensacion(Double montoCompensacion) {
		this.montoCompensacion = montoCompensacion;
	}

	public String getDetalleCompensacion() {
		return detalleCompensacion;
	}

	public void setDetalleCompensacion(String detalleCompensacion) {
		this.detalleCompensacion = detalleCompensacion;
	}

	public LocalDate getFechaCompensacion() {
		return fechaCompensacion;
	}

	public void setFechaCompensacion(LocalDate fechaCompensacion) {
		this.fechaCompensacion = fechaCompensacion;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}


    
    
}
