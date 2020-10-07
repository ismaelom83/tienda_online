package com.proyecto_tienda.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.proyecto_tienda.model.Persona;

@Repository
public interface PersonaRepo extends CrudRepository<Persona, Long> {
	
	public Optional<Persona> findBymail(String email);
	public  <S> S save2(Persona persona);
}
