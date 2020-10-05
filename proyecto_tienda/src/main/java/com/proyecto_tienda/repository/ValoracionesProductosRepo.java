package com.proyecto_tienda.repository;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.proyecto_tienda.model.ValoracionesProducto;
@Repository
public class ValoracionesProductosRepo {
	
	@Autowired
	private EntityManager entityManager;
	
	public ArrayList<ValoracionesProducto> valorarProducto(Long idProducto) throws Exception{
		Session session = entityManager.unwrap(Session.class);
		String hQuery = "from ValoracionesProducto v where v.id_producto = '" + idProducto + "'";
		Query query = session.createQuery(hQuery);
		@SuppressWarnings("unchecked")
		ArrayList<ValoracionesProducto> listaValoraciones = (ArrayList<ValoracionesProducto>) query.list();
		if (listaValoraciones != null) {
			return listaValoraciones;
		} else {
			System.out.println("lista no encontrada");
		}
		return null;
	}
	
	public ArrayList<ValoracionesProducto> verValoraciones(Long idProducto){
		Session session = entityManager.unwrap(Session.class);
		String hQuery = "from ValoracionesProducto v where v.id_producto = '" + idProducto + "'";
		Query query = session.createQuery(hQuery);
		@SuppressWarnings("unchecked")
		ArrayList<ValoracionesProducto> listaValoraciones = (ArrayList<ValoracionesProducto>) query.list();
		if (listaValoraciones != null) {
			return listaValoraciones;
		} else {
//			System.out.println("credenciales no validas");
		}
		return null;
		
	}

}
