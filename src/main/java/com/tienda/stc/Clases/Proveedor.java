package com.tienda.stc.Clases;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProveedor")
    private Long idProveedor;
    
    @Column(name = "ci", length = 15) // temporalmente sin @NotNull
    private String ci;

    @Column(name = "empresa", length = 255, nullable = true)
    private String empresa;
    
    @Column(name = "nombre", nullable = false, length = 255)
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 3, max = 70, message = "El nombre debe tener entre 3 y 70 caracteres")
    private String nombre;
    
    @Column(name = "apellidos", nullable = false, length = 255)
    @NotNull(message = "Los apellidos no pueden ser nulos")
    @Size(min = 3, max = 100, message = "Los apellidos debe tener entre 3 y 100 caracteres")
    private String apellidos;
    
    @Column(name = "estado", nullable = false)
    @NotNull(message = "El estado no puede ser nulo")
    @Min(value = 1, message = "El estado mínimo es 1")
    @Max(value = 2, message = "El estado máximo es 2")
    private Integer estado = 1;
        
    @Column(name = "telefono", nullable = false)
    @NotNull(message = "El teléfono no puede ser nulo")
    @Digits(integer = 15, fraction = 0, message = "El teléfono debe ser un número entero de hasta 15 dígitos")
    private Long telefono; // Se cambió a Long para evitar problemas de formato
    
    @Column(name = "ciudad", nullable = false, length = 255)
    @NotNull(message = "La ciudad no puede ser nula")
    @Size(min = 4, max = 20, message = "La ciudad debe tener entre 4 y 20 caracteres")
    private String ciudad;
    
    @Column(name = "direccion", nullable = false, length = 255)
    @NotNull(message = "La dirección no puede ser nula")
    @Size(min = 5, max = 255, message = "La dirección debe tener entre 5 y 255 caracteres")
    private String direccion;
    
    @Column(name = "detalles", nullable = false, length = 255)
    @NotNull(message = "Los detalles no pueden ser nulos")
    @Size(min = 5, max = 500, message = "Los detalles especifican que tipo de productos ofrece el proveedor")
    private String detalles;

    
  //GET AND SET
    
    
	public Long getIdProveedor() {
		return idProveedor;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Long getTelefono() {
		return telefono;
	}

	public void setTelefono(Long telefono) {
		this.telefono = telefono;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

    

    




}