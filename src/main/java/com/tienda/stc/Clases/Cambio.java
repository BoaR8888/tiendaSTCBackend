package com.tienda.stc.Clases;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cambio {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCambio;

    private LocalDate fechaCambio;

    @ManyToOne
    @JoinColumn(name = "id_venta")
    private Venta venta;

    private String motivo;

    private Double montoDiferencia; // puede ser negativo o positivo
    
    private int estado;

    @OneToMany(mappedBy = "cambio", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<DetalleCambio> detalles;

    
    
	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Long getIdCambio() {
		return idCambio;
	}

	public void setIdCambio(Long idCambio) {
		this.idCambio = idCambio;
	}

	public LocalDate getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(LocalDate fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Double getMontoDiferencia() {
		return montoDiferencia;
	}

	public void setMontoDiferencia(Double montoDiferencia) {
		this.montoDiferencia = montoDiferencia;
	}

	public List<DetalleCambio> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleCambio> detalles) {
		this.detalles = detalles;
	}
    
    
    
}

