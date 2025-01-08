package com.tienda.stc.Clases;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "idUsuario")
     private Long idUsuario;
	 
	 @Column(name= "nombre", nullable = false, length = 50)
	 private String nombre;   
	 
	 @Column(name="password", nullable = false)
	 @NotBlank(message = "La contraseña no puede estar vacía") // No permite contraseñas nulas o vacías
	 @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres") // Longitud de la contraseña
	 @Pattern(
	        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
	        message = "La contraseña debe contener al menos una letra mayúscula, una minúscula, un número y un carácter especial"
			 ) // Valida la complejidad de la contraseña
	 private String password;
	 
	 @Column(name = "estado", nullable = false)
	 @NotNull(message = "El estado no puede ser nulo")
	 @Min(value = 1, message = "El estado mínimo es 1")
	 @Max(value = 2, message = "El estado máximo es 2")
	 private Integer estado = 1;

	 //GET AND SET
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	

}
