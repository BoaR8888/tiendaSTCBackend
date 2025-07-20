package com.tienda.stc.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tienda.stc.Clases.Venta;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query("SELECT COUNT(v) FROM Venta v WHERE v.empleado.idEmpleado = :empleadoId")
    Long countVentasByEmpleadoId(Long empleadoId);

    List<Venta> findAllByEmpleadoIdEmpleadoAndFechaVentaBetween(Long empleadoId, Date startDate, Date endDate);

    @Query("SELECT v FROM Venta v WHERE v.empleado.id = :empleadoId")
    List<Venta> encontrarVentasPorEmpleado(@Param("empleadoId") Long empleadoId);

    ///encontrarultima venta
    @Query("SELECT v FROM Venta v WHERE v.id = (SELECT MAX(v2.id) FROM Venta v2)")
    Venta findLastVenta();

	//Optional<Venta> findTopByOrderByIdDesc();
	 //  Optional<Venta> findTopByOrderByIdDesc();  // Para obtener la última venta registrada
    
	   List<Venta> findAllByOrderByFechaVentaDesc();
	   List<Venta> findAllByOrderByFechaVentaDescIdDesc();
	   Optional<Venta> findTopByOrderByIdDesc();
}
