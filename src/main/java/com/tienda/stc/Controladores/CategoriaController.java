package com.tienda.stc.Controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.stc.Clases.Categoria;
import com.tienda.stc.Servicios.CategoriaService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> getAllCategorias() {
        return categoriaService.listarCategoria();
    }

    @PostMapping
    public Categoria createCategoria(@RequestBody Categoria categoria) {
        return categoriaService.save(categoria);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateProveedor(@PathVariable Long id, @RequestBody Categoria categoriaFrontend) {
        Optional<Categoria> optionalCategoria = categoriaService.obtenerCategoria(id);
        if (optionalCategoria.isPresent()) {
        	Categoria categoria = optionalCategoria.get();
        	categoria.setNombreCategoria(categoriaFrontend.getNombreCategoria());
        	categoria.setDescripcion(categoriaFrontend.getDescripcion());
            Categoria updatedProveedor = categoriaService.save(categoria);
            return ResponseEntity.ok(updatedProveedor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/cambiarEstado/{id}")
    public ResponseEntity<Categoria> updateEstadoCategoria(@PathVariable Long id, @RequestBody Categoria categoriaFrontend) {
        Optional<Categoria> optionalCategoria = categoriaService.obtenerCategoria(id);
        Categoria updatedCategoria;
        if (optionalCategoria.isPresent()) {
        	Categoria categoria = optionalCategoria.get();
            if(categoriaFrontend.getEstado() == 1) {
            	categoria.setEstado(2);
            	updatedCategoria = categoriaService.save(categoria);
            }else {
            	categoria.setEstado(1);
            	updatedCategoria = categoriaService.save(categoria);
            }
  
          //  Proveedor updatedProveedor = proveedorService.save(proveedor1);
            return ResponseEntity.ok(updatedCategoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        if (categoriaService.obtenerCategoria(id).isPresent()) {
        	categoriaService.eliminarCategoria(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
   
    
    /*//ES PARA REPORTES
   @GetMapping("/contar-nombres")
    public Map<String, Long> contarNombres() {
        return categoriaService.contarCiudad();
    }
*/
   


}
