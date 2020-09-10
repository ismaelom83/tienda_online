package com.proyecto_tienda.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_tienda.model.Producto;
import com.proyecto_tienda.model.ValoracionesProducto;
@Repository
public interface ValoracionesProductoRepo extends CrudRepository<ValoracionesProducto, Long> {
	
	public Optional<Producto> findByid(Long id);

}
