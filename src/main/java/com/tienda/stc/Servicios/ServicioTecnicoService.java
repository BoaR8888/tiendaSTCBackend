package com.tienda.stc.Servicios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tienda.stc.Clases.Cliente;
import com.tienda.stc.Clases.Empleado;
import com.tienda.stc.Clases.Producto;
import com.tienda.stc.Clases.ServicioTecnico;
import com.tienda.stc.Repositorios.ClienteRepository;
import com.tienda.stc.Repositorios.EmpleadoRepository;
import com.tienda.stc.Repositorios.ProductoRepository;
import com.tienda.stc.Repositorios.ServicioTecnicoRepository;

import jakarta.persistence.EntityNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
@Service
public class ServicioTecnicoService {

    @Autowired
    private ServicioTecnicoRepository servicioTecnicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    
    public ServicioTecnico guardarServicioTecnico(ServicioTecnico servicioTecnico) {
        // Recupera el cliente existente por su ID
        Cliente cliente = clienteRepository.findById(servicioTecnico.getCliente().getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("El cliente no existe"));
        servicioTecnico.setCliente(cliente);

        // Recupera el empleado existente por su ID
        Empleado empleado = empleadoRepository.findById((int) servicioTecnico.getEmpleado().getIdEmpleado())
                .orElseThrow(() -> new IllegalArgumentException("El empleado no existe"));
        servicioTecnico.setEmpleado(empleado);

        // Recupera el producto si se envió
        if (servicioTecnico.getProducto() != null && servicioTecnico.getProducto().getId() != null) {
            Producto producto = productoRepository.findById(servicioTecnico.getProducto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("El producto no existe"));
            servicioTecnico.setProducto(producto);
        } else {
            servicioTecnico.setProducto(null);
        }

        return servicioTecnicoRepository.save(servicioTecnico);
    }

    
    
    public ServicioTecnico anularEstadoPago(Long id) {
        Optional<ServicioTecnico> optionalServicio = servicioTecnicoRepository.findById(id);

        if (optionalServicio.isPresent()) {
            ServicioTecnico servicio = optionalServicio.get();
            servicio.setEstadoPago("Anulado");
            return servicioTecnicoRepository.save(servicio);
        } else {
            throw new EntityNotFoundException("Servicio técnico con ID " + id + " no encontrado");
        }
    }
/*
    public ServicioTecnico guardarServicioTecnico(ServicioTecnico servicioTecnico) {
        // Recupera el cliente existente por su ID
        Cliente cliente = clienteRepository.findById(servicioTecnico.getCliente().getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("El cliente no existe"));
        servicioTecnico.setCliente(cliente);

        // Recupera el empleado existente por su ID
        Empleado empleado = empleadoRepository.findById((int) servicioTecnico.getEmpleado().getIdEmpleado())
                .orElseThrow(() -> new IllegalArgumentException("El empleado no existe"));
        servicioTecnico.setEmpleado(empleado);

        // Recupera el producto existente por su ID
        Producto producto = productoRepository.findById(servicioTecnico.getProducto().getId())
                .orElseThrow(() -> new IllegalArgumentException("El producto no existe"));
        servicioTecnico.setProducto(producto);

        // Guarda el servicio técnico con las referencias a las entidades persistentes
        return servicioTecnicoRepository.save(servicioTecnico);
    }*/

    // Listar todos los servicios técnicos
    public List<ServicioTecnico> listarServiciosTecnicos() {
        return servicioTecnicoRepository.findAllByOrderByIdServicioTecnicoDesc();
    }
    
    // Guardar un nuevo servicio técnico
    public ServicioTecnico guardar(ServicioTecnico servicioTecnico) {
       // servicioTecnico.calcularTotal(); // Calcula el total antes de guardar
        return servicioTecnicoRepository.save(servicioTecnico);
    }

    // Obtener un servicio técnico por ID
    public Optional<ServicioTecnico> obtenerPorId(Long id) {
        return servicioTecnicoRepository.findById(id);
    }
    /* Actualizar un servicio técnico existente
   @Transactional
    public ServicioTecnico actualizar(Long id, ServicioTecnico detallesServicioTecnico) {
        return servicioTecnicoRepository.findById(id).map(servicioExistente -> {
            servicioExistente.setFecha(detallesServicioTecnico.getFecha());
            servicioExistente.setTipoServicio(detallesServicioTecnico.getTipoServicio());
            servicioExistente.setNombreRepuesto(detallesServicioTecnico.getNombreRepuesto());
            servicioExistente.setObservacion(detallesServicioTecnico.getObservacion());
            servicioExistente.setRepuesto(detallesServicioTecnico.getRepuesto());
            servicioExistente.setPrecioRepuesto(detallesServicioTecnico.getPrecioRepuesto());
            servicioExistente.setPrecioTrabajo(detallesServicioTecnico.getPrecioTrabajo());
            servicioExistente.setEmpleado(detallesServicioTecnico.getEmpleado());
            servicioExistente.setCliente(detallesServicioTecnico.getCliente());
            servicioExistente.setEstadoReparacion(detallesServicioTecnico.getEstadoReparacion());
            servicioExistente.setEstadoPago(detallesServicioTecnico.getEstadoPago());
            servicioExistente.calcularTotal(); // Recalcula el total con los nuevos detalles
            return servicioTecnicoRepository.save(servicioExistente);
        }).orElseThrow(() -> new RuntimeException("Servicio técnico no encontrado con ID: " + id));
    }
    */
    public ServicioTecnico actualizar(Long id, ServicioTecnico detalles) {
        return servicioTecnicoRepository.findById(id).map(servicio -> {
            servicio.setTipoServicio(detalles.getTipoServicio());
            servicio.setFecha(detalles.getFecha());
            servicio.setNombreRepuesto(detalles.getNombreRepuesto());
            servicio.setObservacion(detalles.getObservacion());
            servicio.setProducto(detalles.getProducto());
            servicio.setPrecioRepuesto(detalles.getPrecioRepuesto());
            servicio.setPrecioTrabajo(detalles.getPrecioTrabajo());
            servicio.setTotal(detalles.getTotal());
            servicio.setEmpleado(detalles.getEmpleado());
            servicio.setCliente(detalles.getCliente());
            servicio.setEstadoReparacion(detalles.getEstadoReparacion());
            servicio.setEstadoPago(detalles.getEstadoPago());
            return servicioTecnicoRepository.save(servicio);
        }).orElse(null);
    }


    // Eliminar un servicio técnico por ID
    public void eliminar(Long id) {
        servicioTecnicoRepository.deleteById(id);
    }
    
  
}
