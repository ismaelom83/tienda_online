package com.proyecto_tienda.repository;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proyecto_tienda.model.Persona;

@Repository
public class PersonaRepositorio {
	
	@Autowired
	private EntityManager entityManager;
	
	public Persona buscarPersonaId(int id) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		String hQuery = "from Persona p " + " where p.id = :id";
		Persona p = session.createQuery(hQuery, Persona.class).setParameter("id", id).setMaxResults(1).uniqueResult();
		return p;
		
		
	}
	

}
