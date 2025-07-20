package com.tienda.stc.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tienda.stc.Clases.Producto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
public interface IProductoService {
    Producto save(Producto producto, MultipartFile image) throws IOException;

    List<Producto> findAll();

    Optional<Producto> findById(Long id);

    void deleteById(Long id);
}
