package com.tienda.stc.Clases;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clientes")
public class Cliente {
	
	//ATRIBUTOS
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "idCliente")
	    private int idCliente;
	  
	  @NotNull(message = "El NIT o CI no puede ser nulo")
	    @Pattern(regexp = "\\d{4,15}", message = "El NIT o CI debe tener entre 4 y 15 dígitos")
	    @Column(name = "nit", nullable = false, length = 15)
	    private String nit;
	  
	  @Column(name = "nombre", nullable = false)
	    @NotNull(message = "El nombre no puede ser nulo")
	    @Size(min = 3, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
	    private String nombre;

	    @Column(name = "apellidos", nullable = false)
	    @NotNull(message = "El apellido no puede ser nulo")
	    @Size(min = 3, max = 50, message = "El apellido debe tener entre 1 y 50 caracteres")
	    private String apellidos;

	    @Column(name = "email", nullable = true)
	    @Size(min = 5, max = 100, message = "El email debe tener entre 5 y 100 caracteres")
	    @Email(message = "El email debe tener un formato válido")
	    private String email;
	    
	    @Column(name = "sexo", nullable = false)
	    @NotNull(message = "El sexo no puede ser nulo")
	    @Pattern(regexp = "M|F", message = "El sexo debe ser 'M' para masculino o 'F' para femenino")
	    private String sexo;
	    
	    @Column(name = "estado", nullable = false)
		 @NotNull(message = "El estado no puede ser nulo")
		 @Min(value = 1, message = "El estado mínimo es 1")
		 @Max(value = 2, message = "El estado máximo es 2")
		 private Integer estado = 1;
	    
	    //CARDINALIDAD
	    @ManyToOne
		@JoinColumn(name = "idUsuario", nullable = true) // Define la columna como llave foránea
		//@NotNull(message = "El usuario no puede ser nulo")
		private Usuario usuario;
	    
	    //GET AND SET
		public int getIdCliente() {
			return idCliente;
		}

		public void setIdCliente(int idCliente) {
			this.idCliente = idCliente;
		}

		public String getNit() {
			return nit;
		}

		public void setNit(String nit) {
			this.nit = nit;
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

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getSexo() {
			return sexo;
		}

		public void setSexo(String sexo) {
			this.sexo = sexo;
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
