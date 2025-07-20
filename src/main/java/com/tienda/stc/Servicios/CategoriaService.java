package com.tienda.stc.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.stc.Clases.Categoria;
import com.tienda.stc.Repositorios.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarCategoria() {
        return categoriaRepository.findAllByOrderByIdCategoriaDesc();
    }
    
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    
    public Optional<Categoria> obtenerCategoria(Long id) {
        return categoriaRepository.findById(id);
    }
    
    public void eliminarCategoria(long id) {
		 categoriaRepository.deleteById(id);
	 }

    /*//Funcion de mapeo
    public Map<String, Long> contarCiudad() {
        List<Object[]> results = categoriaRepository.contarNombres();
        Map<String, Long> nombreContador = new HashMap<>();

        for (Object[] result : results) {
            String ciudad = (String) result[0];
            Long cantidad = (Long) result[1];
            nombreContador.put(ciudad, cantidad);
        }

        return nombreContador;
    }
    */
}
