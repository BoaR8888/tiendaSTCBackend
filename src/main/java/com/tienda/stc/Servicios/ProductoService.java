package com.tienda.stc.Servicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tienda.stc.Clases.Producto;
import com.tienda.stc.Repositorios.ProductoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    private final String carpetaImagenes = "ruta/a/tu/carpeta/imagenes";

    // Listar todos los productos
    public List<Producto> listarProducto() {
        return productoRepository.findAll();
    }
    
    public List<Producto> obtenerProductosOrdenados() {
        return productoRepository.findAllProductosOrdenados();
    }

    // Guardar producto
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Obtener producto por ID
    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Actualizar producto
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        Optional<Producto> productoExistente = productoRepository.findById(id);
        if (productoExistente.isPresent()) {
            Producto producto = productoExistente.get();
            producto.setNombre(productoActualizado.getNombre());
            producto.setMarca(productoActualizado.getMarca());
            producto.setModelo(productoActualizado.getModelo());
            producto.setCalidad(productoActualizado.getCalidad());
            producto.setDescripcion(productoActualizado.getDescripcion());
            producto.setPrecioUnitario(productoActualizado.getPrecioUnitario());
            producto.setPrecioVenta(productoActualizado.getPrecioVenta());
            producto.setStock(productoActualizado.getStock());
            producto.setEstado(productoActualizado.getEstado());
            producto.setImagen(productoActualizado.getImagen());
           // producto.setCodigo(productoActualizado.getCodigo());
            producto.setCategoria(productoActualizado.getCategoria());
            return productoRepository.save(producto);
        } else {
            throw new RuntimeException("Producto no encontrado");
        }
    }
    
    // Eliminar producto
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
    
    public Producto cambiarEstado(Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        // Cambia el estado (por ejemplo: 1 = activo, 0 = inactivo)
        producto.setEstado(producto.getEstado() == 1 ? 0 : 1);

        return productoRepository.save(producto);
    }


   
    
    
    
    
    // Actualizar producto con imagen
    public Producto actualizarProducto(Long id, Producto productoActualizado, MultipartFile imagen) throws IOException {
        Optional<Producto> productoExistenteOpt = productoRepository.findById(id);
        
        if (productoExistenteOpt.isPresent()) {
            Producto productoExistente = productoExistenteOpt.get();
            productoExistente.setNombre(productoActualizado.getNombre());
            productoExistente.setMarca(productoActualizado.getMarca());
            productoExistente.setDescripcion(productoActualizado.getDescripcion());
            productoExistente.setPrecioUnitario(productoActualizado.getPrecioUnitario());
            productoExistente.setPrecioVenta(productoActualizado.getPrecioVenta());
            productoExistente.setStock(productoActualizado.getStock());
            productoExistente.setEstado(productoActualizado.getEstado());
          //  productoExistente.setCodigo(productoActualizado.getCodigo());
            productoExistente.setCategoria(productoActualizado.getCategoria());

            // Si se proporciona una imagen, guardar la imagen y actualizar el campo de la imagen
            if (imagen != null && !imagen.isEmpty()) {
                String nombreImagen = guardarImagen(imagen);
                productoExistente.setImagen(nombreImagen);
            }

            return productoRepository.save(productoExistente);
        } else {
            throw new RuntimeException("Producto no encontrado");
        }
    }
    

    // Método para guardar la imagen en la carpeta
    private String guardarImagen(MultipartFile archivo) throws IOException {
        // Crear carpeta si no existe
        Path pathCarpeta = Paths.get(carpetaImagenes);
        if (Files.notExists(pathCarpeta)) {
            Files.createDirectories(pathCarpeta);
        }
        // Guardar archivo
        String nombreArchivo = archivo.getOriginalFilename();
        Path pathArchivo = pathCarpeta.resolve(nombreArchivo);
        Files.copy(archivo.getInputStream(), pathArchivo, StandardCopyOption.REPLACE_EXISTING);
        return nombreArchivo;
    }
    
    public void actualizarStock(Long productoId, Integer cantidadVendida) throws Exception {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new Exception("Producto no encontrado"));
        System.out.print("Lcantidad inicial del producto es:: "+producto.getStock());
        if (producto.getStock() >= cantidadVendida) {
            producto.setStock(producto.getStock() - cantidadVendida);
            System.out.print("Lcantidad final del producto es:: "+producto.getStock());
            productoRepository.save(producto);
        } else {
            throw new Exception("No hay suficiente stock disponible");
        }
    }
    
    
    public List<Producto> buscarPorNombre(String filtro) {
        return productoRepository.findByNombreContainingIgnoreCase(filtro);
    }

    public Producto buscarProductoPorId(Long idProducto) {
        return productoRepository.findById(idProducto)
            .orElse(null);  // o lanza una excepción si prefieres
    }
    
    public List<Producto> obtenerProductosConBajoStock() {
        return productoRepository.findByStockLessThanEqual(5);
    }


}
