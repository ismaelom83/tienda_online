package com.proyecto_tienda.repository;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proyecto_tienda.model.DetallePedido;
import com.proyecto_tienda.model.Mensajeria;


@Repository
public class DetallePedidoRepo {

	
	@Autowired
	private EntityManager entityManager;
	
	public  ArrayList<DetallePedido> mostrarLineasPedido(int id) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		String hQuery = "from DetallePedido de where de.cabeceraPedido ='" + id + "' ";
		Query query = session.createQuery(hQuery);
		@SuppressWarnings("unchecked")
		ArrayList<DetallePedido> listaFactura = (ArrayList<DetallePedido>) query.list();
		
		return listaFactura;
		
	}
	
	public void insertDetallePedido(int idCabezera, Long cabezera, int cantidad, int total) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		Transaction txn = session.beginTransaction();
		Query query = session.createNativeQuery(
				"INSERT INTO detalle_pedido  (id_pedido, id_producto,cantidad,total_linea) VALUES(?,?,?,?)");
		query.setParameter(1, idCabezera);
		query.setParameter(2, cabezera);
		query.setParameter(3, cantidad);
		query.setParameter(4, total);
		query.executeUpdate();
		txn.commit();
	}
	
	
	public void actualizarStock(Long id, int cantidad) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		Transaction txn = session.beginTransaction();
		Query updateQuery = session
				.createQuery("UPDATE Producto p set p.stock=p.stock - '" + cantidad + "' where id='" + id + "'");
		updateQuery.executeUpdate();
		txn.commit();
		
	}
	
	
	public  ArrayList<Mensajeria> buscarDevoluconPedidos(int id) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		String hQuery = " from Mensajeria m where m.idDestinatario ='"+id+"'";
		Query query =  session.createQuery(hQuery);
		@SuppressWarnings("unchecked")
		ArrayList<Mensajeria> devolucion = (ArrayList<Mensajeria>) query.list();
		if (devolucion!=null) {
			return devolucion;
		}else {
		System.out.println("producto no encontrado");	
		}
		return null;
		
	}
	
	public void borrarPedido(int id) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		Transaction txn = session.beginTransaction();
		Query updateQuery = session
				.createQuery("delete from CabeceraPedido ca where id ='"+id+"'");
		updateQuery.executeUpdate();
		txn.commit();
	}
	
	public void borrarLineaPedido(int id) throws Exception{
		Session session = entityManager.unwrap(Session.class);
		Transaction txn = session.beginTransaction();
		Query updateQuery = session
				.createQuery("delete from DetallePedido dp where id ='"+id+"'");
		updateQuery.executeUpdate();
		txn.commit();
	}
	
	public DetallePedido buscarIdDetalleBorrar(int idCabecera) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		String hQuery = "from DetallePedido dp " + " where dp.cabeceraPedido.id = :id";
		DetallePedido cabecera = session.createQuery(hQuery, DetallePedido.class)
				.setParameter("id", idCabecera).setMaxResults(1).uniqueResult();
		return cabecera;
		
	}
	
	public DetallePedido buscarId(int idPedido) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		String hQuery = "from DetallePedido dp " + " where dp.id = :id";
		DetallePedido cabecera = session.createQuery(hQuery, DetallePedido.class)
				.setParameter("id", idPedido).setMaxResults(1).uniqueResult();
		return cabecera;
		
	}
	
	
	
}
