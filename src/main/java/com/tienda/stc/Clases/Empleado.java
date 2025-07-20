package com.tienda.stc.Clases;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmpleado")
    private long idEmpleado;
    
    @NotNull(message = "El CI no puede ser nulo")
    @Pattern(regexp = "\\d{4,15}", message = " CI debe tener entre 4 y 15 dígitos")
    @Column(name = "ci", nullable = false, length = 15)
    private String ci;

    @Column(nullable = false)
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    private String nombre;

    @Column(nullable = false)
    @NotNull(message = "los apellidos no puede ser nulo")
    @Size(min = 1, max = 50, message = "los apellidos deben tener entre 3 y 50 caracteres")
    private String apellidos;
    
    @Column(nullable = false)
    @NotNull(message = "El celular no puede ser nulo")
    @Pattern(regexp = "^[0-9]{8}$", message = "El teléfono debe tener 8 dígitos")
    private String celular;

    @Column(nullable = false)
    @NotNull(message = "La edad no puede ser nula")
    @Min(value = 18, message = "La edad mínima es 18 años")
    @Max(value = 100, message = "La edad máxima es 100 años")
    private Integer edad;

    @Column(name = "sexo", nullable = false)
    @NotNull(message = "El sexo no puede ser nulo")
    @Pattern(regexp = "M|F", message = "El sexo debe ser 'M' para masculino o 'F' para femenino")
    private String sexo;

    @Column(name = "email", nullable = true)
    @Size(min = 5, max = 100, message = "El email debe tener entre 5 y 100 caracteres")
    @Email(message = "El email debe tener un formato válido")
    private String email;
    
    @Column(name = "direccion", nullable = false)
    @NotNull(message = "La direccion no puede ser nula")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 50 caracteres")
    private String direccion;

    @PastOrPresent(message = "La fecha de ingreso debe ser en el pasado o presente")
    private Date fechaIngreso;

    @Min(value = 0, message = "El salario debe ser un valor positivo")
    private double salario;

    @Column(name = "estado", nullable = false)
	 @NotNull(message = "El estado no puede ser nulo")
	 @Min(value = 1, message = "El estado mínimo es 1")
	 @Max(value = 2, message = "El estado máximo es 2")
	 private Integer estado = 1;
    
    //CARDINALIDAD
    // Relación uno a uno con Usuario
    @OneToOne 
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario") // Define la columna de la llave foránea
    private Usuario usuario;
    
   
    //GET AND SET
	public long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
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

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
    
 
    

   
  
   

    
    
    
}
