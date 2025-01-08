package com.tienda.stc.Clases;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Roles {


	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "IdRol")
	 private int idRol;

	 	@Column(name = "nombre", nullable = false, length = 100)
	    @NotNull(message = "El nombre no puede ser nulo")
	    @Size(min = 3, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
	    private String nombre;
	 	
	 	 @Column(name = "descripcion", length = 255)
	     @Size(min= 10, max = 255, message = "La descripción del rol no puede tener más de 255 caracteres")
	     private String descripcion;
	 	 
	 	 @Column(name = "estado", nullable = false)
		 @NotNull(message = "El estado no puede ser nulo")
		 @Min(value = 1, message = "El estado mínimo es 1")
		 @Max(value = 2, message = "El estado máximo es 2")
		 private Integer estado = 1;

		public int getIdRol() {
			return idRol;
		}

		public void setIdRol(int idRol) {
			this.idRol = idRol;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public Integer getEstado() {
			return estado;
		}

		public void setEstado(Integer estado) {
			this.estado = estado;
		}
	 	 
	 	 

}
