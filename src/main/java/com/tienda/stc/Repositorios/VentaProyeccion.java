package com.tienda.stc.Repositorios;

import java.sql.Date;

public interface VentaProyeccion {
    String getNumeroSerie();
    Date getFechaVenta();
    String getEstado();

    String getNombreCliente();
    String getNombreEmpleado();

    String getNombreProducto();
    String getMarca();
    String getDescripcion();
    Integer getCantidad();
    Double getPrecioUnitario();
}
