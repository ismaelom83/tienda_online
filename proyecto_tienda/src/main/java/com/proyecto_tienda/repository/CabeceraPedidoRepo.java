package com.proyecto_tienda.repository;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proyecto_tienda.model.CabeceraPedido;
import com.proyecto_tienda.model.Cliente;
import com.proyecto_tienda.model.DetallePedido;




@Repository
public class CabeceraPedidoRepo {
	
	@Autowired
	private EntityManager entityManager;
	
	public  void insertCabeceraPedido(int id, int importe_total) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createNativeQuery("INSERT INTO cabecera_pedido  (id_cliente,importe_total) VALUES(?,?)");
		Transaction txn = session.beginTransaction();
		query.setParameter(1, id);
		query.setParameter(2, importe_total);
		query.executeUpdate();
		txn.commit();
	}
	
	public ArrayList<CabeceraPedido> mostrarCabeceraPedido(int cliente) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		String hQuery = "from CabeceraPedido c where c.cliente = '" + cliente + "'";
		Query query = session.createQuery(hQuery);
		@SuppressWarnings("unchecked")
		ArrayList<CabeceraPedido> cabezeraPedido = (ArrayList<CabeceraPedido>) query.list();

		if (cabezeraPedido != null) {
			return cabezeraPedido;
		} else {
//			System.out.println("credenciales no validas");
		}
		return null;
		
	}
	
	public  CabeceraPedido consultaUltimoIdCabecera() throws EntityNotFoundException,IOException {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("from CabeceraPedido order by id  DESC");	
		query.setMaxResults(1);
		
		CabeceraPedido last = (CabeceraPedido) query.uniqueResult();
		return last;
		
	}
	
	public CabeceraPedido buscarIdDetalleCabecera(int idCabecera) {
		Session session = entityManager.unwrap(Session.class);
		String hQuery = "from CabeceraPedido cp " + " where cp.id = :id";
		CabeceraPedido cabecera = session.createQuery(hQuery, CabeceraPedido.class).setParameter("id", idCabecera).setMaxResults(1).uniqueResult();
		return cabecera;
		
	}
	
	public DetallePedido buscarIdDetalle(int idCabecera) {
		Session session = entityManager.unwrap(Session.class);
		String hQuery = "from DetallePedido dp " + " where dp.id = :id";
		DetallePedido cabecera = session.createQuery(hQuery, DetallePedido.class).setParameter("id", idCabecera).setMaxResults(1).uniqueResult();
		return cabecera;
		
	}
	

	
	
	
}
