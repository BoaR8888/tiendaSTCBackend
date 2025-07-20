package com.tienda.stc.Repositorios;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tienda.stc.Clases.DetalleVenta;
import com.tienda.stc.Clases.ReporteVentaDTO;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    List<DetalleVenta> findByVentaId(Long ventaId);
    
    
    @Query("""
    	    SELECT new com.tienda.stc.Clases.ReporteVentaDTO(
    	        e.nombre,
    	        e.apellidos,
    	        p.codigo,
    	        p.nombre,
    	        p.modelo,
    	        p.marca,
    	        p.precioUnitario,
    	        dv.precioUnitario,
    	        dv.cantidad,
    	        (
    	            SELECT COALESCE(pr.descuento, 0)
    	            FROM Promocion pr
    	            WHERE pr.producto = p
    	              AND pr.estado = true
    	              AND v.fechaVenta BETWEEN pr.fechaInicio AND pr.fechaFin
    	            ORDER BY pr.fechaInicio DESC
    	            LIMIT 1
    	        ),
    	        ((dv.precioUnitario - p.precioUnitario) * dv.cantidad)
    	    )
    	    FROM DetalleVenta dv
    	    JOIN dv.producto p
    	    JOIN dv.venta v
    	    JOIN v.empleado e
    	    WHERE v.Estado = 'COMPLETADO'
    	      AND v.fechaVenta BETWEEN :fechaInicio AND :fechaFin
    	""")
    	List<ReporteVentaDTO> obtenerReporteVentas(LocalDate fechaInicio, LocalDate fechaFin);


    
    /*
     * 
     * 
     *     
    @Query("SELECT new com.tienda.stc.dto.ReporteVentaDTO( " +
 	       "e.nombre, e.apellidos, p.codigo, p.nombre, p.modelo, p.marca, " +
 	       "p.precioUnitario, dv.precioUnitario, dv.cantidad, " +
 	       "(SELECT COALESCE(pr.descuento, 0) FROM Promocion pr " +
 	       " WHERE pr.producto = p AND v.fechaVenta BETWEEN pr.fechaInicio AND pr.fechaFin AND pr.estado = true), " +
 	       "((dv.precioUnitario - p.precioVenta) * dv.cantidad) " +
 	       ") " +
 	       "FROM DetalleVenta dv " +
 	       "JOIN dv.producto p " +
 	       "JOIN dv.venta v " +
 	       "JOIN v.empleado e " +
 	       "WHERE v.fechaVenta BETWEEN :fechaInicio AND :fechaFin " +
 	       "AND v.estado = 'COMPLETADO'")
 	//List<ReporteVentaDTO> obtenerReporteVentas(@Param("fechaInicio") LocalDate fechaInicio,
 	//                                           @Param("fechaFin") LocalDate fechaFin);

	List<ReporteVentaDTO> obtenerReporteVentas(LocalDate fechaInicio, LocalDate fechaFin);


     * 
     * 
     * 
    @Query("SELECT " +
    	       "v.numeroSerie AS numeroSerie, v.fechaVenta AS fechaVenta, v.estado AS estado, " +
    	       "c.nombre AS nombreCliente, e.nombre AS nombreEmpleado, " +
    	       "p.nombre AS nombreProducto, p.marca AS marca, p.descripcion AS descripcion, " +
    	       "d.cantidad AS cantidad, d.precioUnitario AS precioUnitario " +
    	       "FROM Venta v " +
    	       "JOIN v.cliente c " +
    	       "JOIN v.empleado e " +
    	       "JOIN v.detalles d " +
    	       "JOIN d.producto p")
    	List<VentaProyeccion> listarVentasProyeccion();
    	*/
    /*
    @Query("SELECT new com.tienda.stc.Clases.ReporteVentaDTO( " +
    	       "e.nombre, e.apellidos, p.codigo, p.nombre, p.modelo, p.marca, " +
    	       "p.precioVenta, dv.precioUnitario, dv.cantidad, " +
    	       "(SELECT COALESCE(pr.descuento, 0) FROM Promocion pr WHERE pr.producto = p AND v.fechaVenta BETWEEN pr.fechaInicio AND pr.fechaFin AND pr.estado = true), " +
    	       "((dv.precioVenta - p.precioUnitario) * dv.cantidad) " +
    	       ") " +
    	       "FROM DetalleVenta dv " +
    	       "JOIN dv.producto p " +
    	       "JOIN dv.venta v " +
    	       "JOIN v.empleado e " +
    	       "WHERE v.fechaVenta BETWEEN :fechaInicio AND :fechaFin " +
    	       "AND v.estado = 'COMPLETADO'")
    	List<ReporteVentaDTO> obtenerReporteVentas(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
*/
  
    
/*
    @Query("SELECT new com.tu.paquete.dto.ReporteVentaDTO( " +
            "e.nombre, e.apellidos, p.codigo, p.nombre, p.modelo, p.marca, " +
            "p.precioUnitario, dv.precioUnitario, dv.cantidad, " +
            "(SELECT COALESCE(pr.descuento, 0) FROM Promocion pr WHERE pr.producto = p AND :fechaInicio BETWEEN pr.fechaInicio AND pr.fechaFin AND pr.estado = true), " +
            "((dv.precioUnitario - p.precioUnitario) * dv.cantidad) " +
            ") " +
            "FROM DetalleVenta dv " +
            "JOIN dv.producto p " +
            "JOIN dv.venta v " +
            "JOIN v.empleado e " +
            "WHERE v.fechaVenta BETWEEN :fechaInicio AND :fechaFin " +
            "AND (:idEmpleado IS NULL OR e.id = :idEmpleado) " +
            "AND v.estado = 'COMPLETADO'")
    	List<ReporteVentaDTO> obtenerReporteVentasFiltrado(
    	    @Param("fechaInicio") LocalDate fechaInicio,
    	    @Param("fechaFin") LocalDate fechaFin,
    	    @Param("idEmpleado") Long idEmpleado);
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	    
    	      @Query("SELECT new com.tienda.stc.dto.ReporteVentaDTO( " +
    	       "e.nombre, e.apellidos, p.codigo, p.nombre, p.modelo, p.marca, " +
    	       "p.precioUnitario, dv.precioUnitario, dv.cantidad, " +
    	       "(SELECT COALESCE(pr.descuento, 0) FROM Promocion pr " +
    	       " WHERE pr.producto = p AND v.fechaVenta BETWEEN pr.fechaInicio AND pr.fechaFin AND pr.estado = true), " +
    	       "((dv.precioUnitario - p.precioVenta) * dv.cantidad) " +
    	       ") " +
    	       "FROM DetalleVenta dv " +
    	       "JOIN dv.producto p " +
    	       "JOIN dv.venta v " +
    	       "JOIN v.empleado e " +
    	       "WHERE v.fechaVenta BETWEEN :fechaInicio AND :fechaFin " +
    	       "AND v.estado = 'COMPLETADO'")
    	List<ReporteVentaDTO> obtenerReporteVentas(@Param("fechaInicio") LocalDate fechaInicio,
    	                                           @Param("fechaFin") LocalDate fechaFin);


*
*
*
*
*
*
*
*
*
*
*
*
*
*SELECT 
    p.codigo,
    p.nombre,
    p.modelo,
    p.marca,
    e.nombre AS nombre,
    e.apellidos AS apellidos,
    dv.cantidad,
    dv.precio_unitario,
    COALESCE((
        SELECT pr.descuento 
        FROM promociones pr
        WHERE pr.producto_id = p.id
          AND v.fecha_venta BETWEEN pr.fecha_inicio AND pr.fecha_fin
          AND pr.estado = true 
    
        LIMIT 1
    ), 0) AS descuento
FROM detalle_venta dv
JOIN producto p ON dv.id_producto = p.id
JOIN venta v ON dv.id_venta = v.id
JOIN empleados e ON v.id_empleado = e.id_empleado
WHERE v.fecha_venta BETWEEN '2025-05-1' AND '2025-05-27';


--CONSULTA BASICA
SELECT 
    p.codigo,
    p.nombre,
    v.fecha_venta,
    e.nombre AS nombre
FROM detalle_venta dv
JOIN producto p ON dv.id_producto = p.id
JOIN venta v ON dv.id_venta = v.id
JOIN empleados e ON v.id_empleado = e.id_empleado
WHERE v.fecha_venta BETWEEN '2025-05-25' AND '2025-05-27';

----SIN FILTRO
SELECT 
    p.codigo,
    p.nombre,
    v.fecha_venta,
    e.nombre AS empleado_nombre
FROM detalle_venta dv
JOIN producto p ON dv.id_producto = p.id
JOIN venta v ON dv.id_venta = v.id
JOIN empleados e ON v.id_empleado = e.id_empleado
LIMIT 10;

SELECT 
    p.codigo,
    p.nombre,
    p.modelo,
    p.marca,
    p.precio_unitario,  -- precio base del producto
    e.nombre AS nombre,
    e.apellidos AS apellidos,
    dv.cantidad,
    dv.precio_unitario AS precio_venta,  -- precio en la venta
    COALESCE((
        SELECT pr.descuento 
        FROM promociones pr
        WHERE pr.producto_id = p.id
          AND v.fecha_venta BETWEEN pr.fecha_inicio AND pr.fecha_fin
          AND pr.estado = true
        LIMIT 1
    ), 0) AS descuento,
    ((dv.precio_unitario - p.precio_unitario) * dv.cantidad) AS ganancia
FROM detalle_venta dv
JOIN producto p ON dv.id_producto = p.id
JOIN venta v ON dv.id_venta = v.id
JOIN empleados e ON v.id_empleado = e.id_empleado
WHERE v.fecha_venta BETWEEN '2025-05-25' AND '2025-05-27';




SELECT 
    e.nombre AS nombre_empleado,
    e.apellidos AS apellido_empleado,
    p.codigo,
    p.nombre AS nombre_producto,
    p.modelo,
    p.marca,
    p.precio_unitario,
    dv.precio_unitario AS precio_venta,
    dv.cantidad,
    COALESCE((
        SELECT pr.descuento 
        FROM promociones pr
        WHERE pr.producto_id = p.id
          AND v.fecha_venta BETWEEN pr.fecha_inicio AND pr.fecha_fin
          AND pr.estado = true
        LIMIT 1
    ), 0) AS descuento,
    ((dv.precio_unitario - p.precio_unitario) * dv.cantidad) AS ganancia
FROM detalle_venta dv
JOIN producto p ON dv.id_producto = p.id
JOIN venta v ON dv.id_venta = v.id
JOIN empleados e ON v.id_empleado = e.id_empleado
WHERE v.fecha_venta BETWEEN '2025-05-27' AND '2025-05-27'
  AND v.estado = 'COMPLETADO';

*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*/
}