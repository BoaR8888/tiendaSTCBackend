package com.tienda.stc.Controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.stc.Clases.Producto;
import com.tienda.stc.Repositorios.ProductoRepository;
import com.tienda.stc.Servicios.ProductoService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    private ProductoRepository productoRepository;

    private final String carpetaImagenes = "imagenes"; // Carpeta para almacenar imágenes

    // Crear un nuevo producto con imagen
    
    
    
    @PostMapping("/crear")
    public ResponseEntity<Producto> crearProducto(@RequestPart("producto") Producto producto,
                                                  @RequestPart("archivo") MultipartFile archivo) {
        try {
            String urlImagen = guardarImagen(archivo);
            producto.setImagen(urlImagen);  // Guarda la URL de la imagen en el producto
            Producto nuevoProducto = productoService.guardarProducto(producto);
            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // Listar todos los productos con la URL completa de la imagen
    @GetMapping("/listaProductos")
    public List<Producto> listarProductos() {
        return productoService.obtenerProductosOrdenados().stream().map(producto -> {
            producto.setImagen("/api/productos/imagenes/" + producto.getImagen());
            return producto;
        }).collect(Collectors.toList());
    }

    
    @GetMapping("/productos")
    public List<Producto> listarProductosOrdenados() {
        return productoService.obtenerProductosOrdenados();
    }


      // Endpoint para obtener la imagen desde el servidor
    @GetMapping("/imagenes/{nombreImagen:.+}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String nombreImagen) {
        try {
            Path pathImagen = Paths.get(carpetaImagenes).resolve(nombreImagen).normalize();
            System.out.println("Ruta de la imagen: " + pathImagen.toString());
            Resource recurso = new UrlResource(pathImagen.toUri());
            if (recurso.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(Files.probeContentType(pathImagen)))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + recurso.getFilename() + "\"")
                        .body(recurso);
            } else {
            	  System.out.println("La imagen no existe: " + nombreImagen);
                return ResponseEntity.notFound().build();
              
            }
        } catch (MalformedURLException e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Método para guardar la imagen en la carpeta
    private String guardarImagen(MultipartFile archivo) throws IOException {
        // Crear carpeta si no existe
        Path pathCarpeta = Paths.get(carpetaImagenes);
        if (Files.notExists(pathCarpeta)) {
            Files.createDirectories(pathCarpeta);
        }
        //guardar archivo
        String nombreArchivo = archivo.getOriginalFilename();
        Path pathArchivo = pathCarpeta.resolve(nombreArchivo);
        Files.copy(archivo.getInputStream(), pathArchivo, StandardCopyOption.REPLACE_EXISTING);

        return carpetaImagenes + "/" + nombreArchivo;
    }
 
    @PutMapping(value = "/actualizar/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id,
                                                       @RequestPart("producto") Producto productoActualizado,
                                                       @RequestPart(value = "archivo", required = false) MultipartFile archivo) {
        try {
            Producto productoExistente = productoService.obtenerProductoPorId(id)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Actualizar imagen si se proporciona un nuevo archivo
            if (archivo != null) {
                eliminarImagen(productoExistente.getImagen());
                String urlImagen = guardarImagen(archivo);
                productoActualizado.setImagen(urlImagen);
            } else {
                productoActualizado.setImagen(productoExistente.getImagen());
            }

            Producto productoActualizadoFinal = productoService.actualizarProducto(id, productoActualizado);
            return new ResponseEntity<>(productoActualizadoFinal, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Actualizar producto y su imagen
    /*
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id,
                                                       @RequestParam("producto") Producto productoActualizado,
                                                       @RequestParam(value = "archivo", required = false) MultipartFile archivo) {
        try {
            Producto productoExistente = productoService.obtenerProductoPorId(id)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Actualizar imagen si se proporciona un nuevo archivo
            if (archivo != null) {
                eliminarImagen(productoExistente.getImagen());
                String urlImagen = guardarImagen(archivo);
                productoActualizado.setImagen(urlImagen);
            } else {
                productoActualizado.setImagen(productoExistente.getImagen());
            }

            Producto productoActualizadoFinal = productoService.actualizarProducto(id, productoActualizado);
            return new ResponseEntity<>(productoActualizadoFinal, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Eliminar producto y su imagen
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        Producto producto = productoService.obtenerProductoPorId(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        eliminarImagen(producto.getImagen());
        productoService.eliminarProducto(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
*/
 

  
    // Método para eliminar la imagen del servidor
    private void eliminarImagen(String urlImagen) {
        if (urlImagen != null) {
            Path pathImagen = Paths.get(urlImagen);
            try {
                Files.deleteIfExists(pathImagen);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(
        @PathVariable Long id,
        @RequestParam("producto") String productoJson,
        @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        try {
            // Convertir el JSON de producto a un objeto de Producto
            ObjectMapper objectMapper = new ObjectMapper();
            Producto productoActualizado = objectMapper.readValue(productoJson, Producto.class);

            // Buscar el producto existente en la base de datos
            Optional<Producto> productoExistenteOpt = productoRepository.findById(id);
            if (!productoExistenteOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Producto productoExistente = productoExistenteOpt.get();

            // Actualizar los campos del producto existente
            productoExistente.setNombre(productoActualizado.getNombre());
            productoExistente.setMarca(productoActualizado.getMarca());
            productoExistente.setModelo(productoActualizado.getModelo());
            productoExistente.setCalidad(productoActualizado.getCalidad());
            productoExistente.setDescripcion(productoActualizado.getDescripcion());
            productoExistente.setPrecioUnitario(productoActualizado.getPrecioUnitario());
            productoExistente.setPrecioVenta(productoActualizado.getPrecioVenta());
            productoExistente.setStock(productoActualizado.getStock());
            productoExistente.setEstado(productoActualizado.getEstado());
           // productoExistente.setCodigo(productoActualizado.getCodigo());
            productoExistente.setCategoria(productoActualizado.getCategoria());

            // Si hay una imagen nueva, reemplazar la imagen existente
            if (imagen != null && !imagen.isEmpty()) {
                String nombreImagen = guardarImagen(imagen);
                productoExistente.setImagen(nombreImagen);
            }

            // Guardar el producto actualizado en la base de datos
            Producto productoGuardado = productoRepository.save(productoExistente);
            return ResponseEntity.ok(productoGuardado);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para actualizar el stock de un producto
    @PutMapping("/{productoId}/actualizar-stock")
    public ResponseEntity<Void> actualizarStock(@PathVariable Long productoId, @RequestBody Map<String, Integer> body) {
    	System.out.println("ENTRA 1");
        Integer cantidadVendida = body.get("cantidadVendida");
        System.out.println("ENTRA2 "+ productoId +"cantidad " + cantidadVendida);

        try {
            productoService.actualizarStock(productoId, cantidadVendida);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarProductos(@RequestParam("filtro") String filtro) {
        List<Producto> productos = productoService.buscarPorNombre(filtro);
        return ResponseEntity.ok(productos);
    }

    @PutMapping("/cambiar-estado/{id}")
    public ResponseEntity<Producto> cambiarEstadoProducto(@PathVariable Long id) {
        Producto productoActualizado = productoService.cambiarEstado(id);
        return ResponseEntity.ok(productoActualizado);
    }

    
    @GetMapping("/bajo-stock")
    public List<Producto> obtenerProductosConBajoStock() {
        return productoService.obtenerProductosConBajoStock();
    }


    
}
