package com.tienda.stc.Clases;
import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.*;

@Embeddable
public class UsuarioRoles_pk implements Serializable {
    
    @Column(name = "idUsuario")
    private Long idUsuario;

    @Column(name = "idRol")
    private int idRol;

    // Constructor vacío requerido por JPA
    public UsuarioRoles_pk() {}

    public UsuarioRoles_pk(Long idUsuario, int idRol) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
    }

    // Getters y Setters
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    // Métodos hashCode y equals para clave primaria compuesta
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioRoles_pk that = (UsuarioRoles_pk) o;
        return Objects.equals(idUsuario, that.idUsuario) &&
               Objects.equals(idRol, that.idRol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idRol);
    }
}
