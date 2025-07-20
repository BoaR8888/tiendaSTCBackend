package com.tienda.stc.Clases;

public class ReporteVentaDTO {

    private String nombreEmpleado;
    private String apellidoEmpleado;
    private String codigoProducto;
    private String nombreProducto;
    private String modelo;
    private String marca;
    private Double precioUnitario;
    private Double precioVenta;
    private Integer cantidad;
    private Double descuento;
    private Double ganancia;

    public ReporteVentaDTO(String nombreEmpleado, String apellidoEmpleado, String codigoProducto, 
                           String nombreProducto, String modelo, String marca, 
                           Double precioUnitario, Double precioVenta, Integer cantidad, 
                           Double descuento, Double ganancia) {
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.modelo = modelo;
        this.marca = marca;
        this.precioUnitario = precioUnitario;
        this.precioVenta = precioVenta;
        this.cantidad = cantidad;
        this.descuento = descuento;
        this.ganancia = ganancia;
    }

	public String getNombreEmpleado() {
		return nombreEmpleado;
	}

	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}

	public String getApellidoEmpleado() {
		return apellidoEmpleado;
	}

	public void setApellidoEmpleado(String apellidoEmpleado) {
		this.apellidoEmpleado = apellidoEmpleado;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public Double getGanancia() {
		return ganancia;
	}

	public void setGanancia(Double ganancia) {
		this.ganancia = ganancia;
	}

    // getters y setters aquí si los necesitas
    
}
