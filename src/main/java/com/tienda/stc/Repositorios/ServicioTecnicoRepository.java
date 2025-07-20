package com.tienda.stc.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.stc.Clases.ServicioTecnico;

public interface ServicioTecnicoRepository extends JpaRepository <ServicioTecnico, Long> {

	List<ServicioTecnico> findAllByOrderByIdServicioTecnicoDesc();

}
