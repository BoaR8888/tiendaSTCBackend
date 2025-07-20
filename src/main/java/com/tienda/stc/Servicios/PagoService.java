package com.tienda.stc.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.stc.Clases.Pago;
import com.tienda.stc.Repositorios.PagoRepository;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> listarTodos() {
       // return pagoRepository.findAll();
    	 return pagoRepository.findAllByOrderByIdPagoDesc();
    }

    public Optional<Pago> obtenerPorId(Long id) {
        return pagoRepository.findById(id);
    }

    public Pago guardar(Pago pago) {
        return pagoRepository.save(pago);
    }

    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }
    
    public Pago cambiarEstado(Long idPago) {
        Pago pago = pagoRepository.findById(idPago)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + idPago));

        // Cambia de 1 a 0 o de 0 a 1
        pago.setEstadoPago(pago.getEstadoPago() == 1 ? 2 : 1);


        return pagoRepository.save(pago);
    }


}
