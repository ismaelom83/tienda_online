package com.proyecto_tienda.repository;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.proyecto_tienda.model.CategoriasMenu;

@Repository
public class CategoriasMenuRepo {

	@Autowired
	private EntityManager entityManager;

	public ArrayList<CategoriasMenu> buscarCategoria() throws Exception {
		Session session = entityManager.unwrap(Session.class);
		String hQuery = " from CategoriasMenu";
		Query query = session.createQuery(hQuery);
		@SuppressWarnings("unchecked")
		ArrayList<CategoriasMenu> categoria = (ArrayList<CategoriasMenu>) query.list();
		if (categoria != null) {
			return categoria;
		} else {
//			System.out.println("credenciales no validas");
		}
		return null;
	}

	public void save(CategoriasMenu categoria) throws Exception {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(categoria);
	}
}
