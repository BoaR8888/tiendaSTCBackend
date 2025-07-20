package com.tienda.stc.Clases;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario_roles")
public class UsuarioRoles {

    @EmbeddedId
    private UsuarioRoles_pk usuarioRoles_pk;

    @ManyToOne
    @MapsId("idRol")  // Vincula con el campo idRol en UsuarioRoles_pk
    @JoinColumn(name = "idRol", referencedColumnName = "idRol")
    private Roles roles;

    @ManyToOne
    @MapsId("idUsuario")  // Vincula con el campo idUsuario en UsuarioRoles_pk
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    private Usuario usuario;

    // Constructor vacío requerido por JPA
    public UsuarioRoles() {}
    

    public UsuarioRoles(Usuario usuario, Roles roles) {
        this.usuarioRoles_pk = new UsuarioRoles_pk(usuario.getIdUsuario(), roles.getIdRol());
        this.usuario = usuario;
        this.roles = roles;
    }


    // Getters y Setters
    public UsuarioRoles_pk getUsuarioRoles_pk() {
        return usuarioRoles_pk;
    }

    public void setUsuarioRoles_pk(UsuarioRoles_pk usuarioRoles_pk) {
        this.usuarioRoles_pk = usuarioRoles_pk;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
