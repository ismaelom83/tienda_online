package com.proyecto_tienda.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_tienda.model.Producto;

@Repository
public interface ProductoRepositorio extends CrudRepository<Producto, Long> {
	
	public Optional<Producto> findBydescripcion(String descripcion);
	
}
