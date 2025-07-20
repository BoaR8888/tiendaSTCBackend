package com.tienda.stc.Clases;

import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategoria")
    private Long idCategoria;

    @Column(name = "nombreCategoria", nullable = false)
    @NotNull(message = "El nombre de la categoría no puede ser nulo")
    private String nombreCategoria;

    @Column(name = "descripcion", nullable = false)
    @NotNull(message = "La descripción no puede ser nula")
    @Size(min = 1, max = 200, message = "La descripción debe tener entre 1 y 200 caracteres")
    private String descripcion;
    
    @Column(name = "estado", nullable = false)
    @NotNull(message = "El estado no puede ser nulo")
    @Min(value = 1, message = "El estado mínimo es 1")
    @Max(value = 2, message = "El estado máximo es 2")
    private Integer estado = 1;

    
    /*
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Subcategoria> subcategorias;
*/
    
    
    public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
/*
	public List<Subcategoria> getSubcategorias() {
		return subcategorias;
	}

	public void setSubcategorias(List<Subcategoria> subcategorias) {
		this.subcategorias = subcategorias;
	}  */
    
    
}