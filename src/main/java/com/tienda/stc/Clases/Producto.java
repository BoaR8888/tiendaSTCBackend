package com.tienda.stc.Clases;

import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    @NotNull(message = "El nombre del producto no puede ser nulo")
    @Size(min = 1, max = 100, message = "El nombre del producto debe tener entre 1 y 100 caracteres")
    private String nombre;

    @Column(name = "marca", nullable = false)
    @NotNull(message = "La marca no puede ser nula")
    @Size(min = 1, max = 50, message = "La marca debe tener entre 1 y 50 caracteres")
    private String marca;
    
    @Column(name = "modelo")
    @Size(min = 1, max = 100, message = "La marca debe tener entre 1 y 50 caracteres")
    private String modelo;
    
    @Column(name = "calidad")
    @Size(min = 1, max = 50, message = "La marca debe tener entre 1 y 50 caracteres")
    private String calidad;
    

    @Column(name = "descripcion", nullable = false)
    @Size(min = 1, max = 300, message = "La descripción debe tener entre 1 y 300 caracteres")
    private String descripcion;

    @Column(name = "precio_unitario", nullable = false)
    @NotNull(message = "El precio unitario no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio unitario debe ser mayor que 0")
    private Double precioUnitario;

    @Column(name = "precio_venta", nullable = false)
    @NotNull(message = "El precio de venta no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de venta debe ser mayor que 0")
    private Double precioVenta;

    @Column(name = "stock", nullable = false)
    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock debe ser al menos 0")
    private Integer stock;

    @Column(name = "estado", nullable = false)
    @NotNull(message = "El estado no puede ser nulo")
    @Min(value = 1, message = "El estado mínimo es 1")
    @Max(value = 2, message = "El estado máximo es 2")
    private Integer estado = 1;

    @Column(name = "imagen", length = 500)
    private String imagen; // Puedes usar una URL o un campo Base64 para la imagen

    @Column(name = "codigo", nullable = false, unique = true)
    @NotNull(message = "El código del producto no puede ser nulo")
    @Size(min = 1, max = 50, message = "El código debe tener entre 1 y 50 caracteres")
    private String codigo;

    // Relación con Categoria
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;
    
    
    public String generarCodigoDeBarra() {
        return "P-" + UUID.randomUUID().toString().substring(0, 8); // Genera un código único de 8 caracteres
    }

    @PrePersist
    public void prePersist() {
        // Antes de guardar el producto, genera el código de barra único
        if (this.codigo == null) {
            this.codigo = generarCodigoDeBarra();
        }
    }

    // Getters y setters adicionales en caso de no querer utilizar Lombok
    
    
    public Long getId() {
        return id;
    }
    
    

    public String getModelo() {
		return modelo;
	}



	public void setModelo(String modelo) {
		this.modelo = modelo;
	}



	public String getCalidad() {
		return calidad;
	}



	public void setCalidad(String calidad) {
		this.calidad = calidad;
	}



	public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCodigo() {
        return codigo;
    }

  

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
