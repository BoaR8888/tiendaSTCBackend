package com.tienda.stc.Clases;


import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "servicios_tecnicos")
public class ServicioTecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicioTecnico")
    private Long idServicioTecnico;

	@Column(name = "fecha", nullable = false)
    @NotNull(message = "La fecha no puede ser nula")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "tipo_servicio", nullable = false)
    @NotNull(message = "El tipo de servicio no puede ser nulo")
    @Size(min = 1, max = 100, message = "El tipo de servicio debe tener entre 1 y 100 caracteres")
    private String tipoServicio;
    
    @Column(name = "nombre_repuesto", nullable = false)
  //  @NotNull(message = "El nombre del repuesto no puede ser nulo")
    @Size(min = 1, max = 100, message = "El tipo de servicio debe tener entre 1 y 100 caracteres")
    private String nombreRepuesto;
    
    @Column(name = "observacion", nullable = false)
    @NotNull(message = "La observacion no puede ser nula")
    @Size(min = 1, max = 500, message = "El tipo de servicio debe tener entre 1 y 100 caracteres")
    private String observacion;

    // Relación con Producto para obtener detalles del repuesto utilizado y su precio y nombre
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto", nullable = true)
    private Producto producto;

    @Column(name = "precio_repuesto", nullable = true)
   // @NotNull(message = "El precio del repuesto no puede ser nulo")
  //  @DecimalMin(value = "0.0", inclusive = false, message = "El precio del trabajo debe ser mayor que 0")
    private Double precioRepuesto;
    
    @Column(name = "precio_trabajo", nullable = false)
    @NotNull(message = "El precio del trabajo no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio del trabajo debe ser mayor que 0")
    private Double precioTrabajo;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    
    @Column(name = "total", nullable = false)
    private Double total;

    // Relación con Empleado que realizó el servicio
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    // Relación con Cliente para el cual se realizó el servicio
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "estado_reparacion", nullable = true)
    //@NotNull(message = "El estado de la reparación no puede ser nulo")
    @Size(min = 1, max = 50, message = "El estado de la reparación debe tener entre 1 y 50 caracteres")
    private String estadoReparacion;

    @Column(name = "estado_pago", nullable = true)
   // @NotNull(message = "El estado del pago no puede ser nulo")
    @Size(min = 1, max = 50, message = "El estado del pago debe tener entre 1 y 50 caracteres")
    private String estadoPago;

    // Métodos adicionales para calcular el total automáticamente si es necesario
    
    /*
    public void calcularTotal() {
    	System.out.println("El precio es:: "+repuesto.getId()+ " -- "+ repuesto.getMarca());
        if (repuesto != null && repuesto.getPrecioVenta() != null) {
            this.total = repuesto.getPrecioVenta() + this.precioTrabajo;
        } else {
            throw new IllegalStateException("El precio de venta del repuesto no puede ser null.");
        }
    }  

    // Getters personalizados para obtener nombre y precio del repuesto
    public String getNombreRepuesto() {
        return this.repuesto != null ? this.repuesto.getNombre() : null;
    }

    public Double getPrecioRepuesto() {
        return this.repuesto != null ? this.repuesto.getPrecioVenta() : null;
    }
    
    
    
    //get and set de las entidades
    public Producto getRepuesto() {
		return repuesto;
	}

	public void setRepuesto(Producto repuesto) {
		this.repuesto = repuesto;
	}
*/
    
    
	public Empleado getEmpleado() {
		return empleado;
	}

	public LocalDate getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(LocalDate fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Long getIdServicioTecnico() {
		return idServicioTecnico;
	}

	public void setIdServicioTecnico(Long idServicioTecnico) {
		this.idServicioTecnico = idServicioTecnico;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public String getNombreRepuesto() {
		return nombreRepuesto;
	}

	public Double getPrecioRepuesto() {
		return precioRepuesto;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	

 	public Date getFecha() {
 		return fecha;
 	}

 	public void setFecha(Date fecha) {
 		this.fecha = fecha;
 	}

 	public String getTipoServicio() {
 		return tipoServicio;
 	}

 	public void setTipoServicio(String tipoServicio) {
 		this.tipoServicio = tipoServicio;
 	}

 	public Double getPrecioTrabajo() {
 		return precioTrabajo;
 	}

 	public void setPrecioTrabajo(Double precioTrabajo) {
 		this.precioTrabajo = precioTrabajo;
 	}

 	public Double getTotal() {
 		return total;
 	}

 	public void setTotal(Double total) {
 		this.total = total;
 	}

 	public String getEstadoReparacion() {
 		return estadoReparacion;
 	}

 	public void setEstadoReparacion(String estadoReparacion) {
 		this.estadoReparacion = estadoReparacion;
 	}

 	public String getEstadoPago() {
 		return estadoPago;
 	}

 	public void setEstadoPago(String estadoPago) {
 		this.estadoPago = estadoPago;
 	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public void setNombreRepuesto(String nombreRepuesto) {
		this.nombreRepuesto = nombreRepuesto;
	}

	public void setPrecioRepuesto(Double precioRepuesto) {
		this.precioRepuesto = precioRepuesto;
	}
 	
 	
}

