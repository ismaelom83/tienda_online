package com.proyecto_tienda.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;
import org.springframework.stereotype.Repository;

import com.proyecto_tienda.model.Producto;

@Repository
public class ProductoRepo{
	
	@Autowired
	private EntityManager entityManager;
	

	public List<Producto> buscarTodosProductos() throws IOException {
		Session session = entityManager.unwrap(Session.class);
		Query<Producto> query =session.createQuery("from Producto",Producto.class);
		List<Producto> listaProductos = query.getResultList();
		return listaProductos;
	}

	
	public Producto buscarProductoId(Long id)  throws IOException {
		Session session = entityManager.unwrap(Session.class);
		String hQuery = "from Producto p " + " where p.id = :id";
		Producto p = session.createQuery(hQuery, Producto.class).setParameter("id", id).setMaxResults(1).uniqueResult();
		return p;
		
	}
	public  void save(Producto producto) throws Exception {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(producto);	
	}
	
	public List<Producto> buscarProductoIdLista(Long id) throws IOException {
		Session session = entityManager.unwrap(Session.class);
		Query<Producto> query =session.createQuery("from Producto p " + " where p.id = '"+id+"'",Producto.class);
		List<Producto> listaProductos = query.getResultList();
		return listaProductos;
		
	}
	
	public ArrayList<Producto> buscarPructoCaregoria(String categoria) throws IOException {
		Session session = entityManager.unwrap(Session.class);
		String hQuery = " from Producto pro where pro.categoria = '" + categoria + "'";
		Query query = session.createQuery(hQuery);
		@SuppressWarnings("unchecked")
		ArrayList<Producto> aProductos = (ArrayList<Producto>) query.list();
		if (aProductos != null) {
			return aProductos;
		} else {
//			System.out.println("credenciales no validas");
		}
		return null;
	}
	
	public void actualizarPuntosCangeable(Long id, int cantidad) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		Transaction txn = session.beginTransaction();
		Query updateQuery = session
				.createQuery("UPDATE Producto p set p.puntos=p.puntos + '" + cantidad + "' where id='" + id + "'");
		updateQuery.executeUpdate();
		txn.commit();
		
	}





	

	
	
	
}
