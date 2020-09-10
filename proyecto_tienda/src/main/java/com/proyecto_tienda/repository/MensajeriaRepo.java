package com.proyecto_tienda.repository;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MensajeriaRepo {
	
	
	@Autowired
	private EntityManager entityManager;
	
	public void borrarMensajeDevolucion(int id) {
		Session session = entityManager.unwrap(Session.class);
		Transaction txn = session.beginTransaction();
		Query updateQuery = session
				.createQuery("delete from Mensajeria m where m.cuerpo like '%"+id+"%'");
		updateQuery.executeUpdate();
		txn.commit();
	}
	
	public  void insertarMensajesTrabajador(int idDestinatario, int idOrigen,String adjunto, String asunto,String cuerpo,int leido,int contestado) {
		Session session = entityManager.unwrap(Session.class);
		Transaction txn = session.beginTransaction();
		Query query = session.createNativeQuery(
				"INSERT INTO mensajeria  (id_destinatario, id_origen,adjunto,asunto,cuerpo,leido,contestado) VALUES(?,?,?,?,?,?,?)");
		query.setParameter(1, idDestinatario);
		query.setParameter(2, idOrigen);
		query.setParameter(3, adjunto);
		query.setParameter(4, asunto);
		query.setParameter(5, cuerpo);
		query.setParameter(6, leido);
		query.setParameter(7, contestado);
		query.executeUpdate();
		txn.commit();
	}
	
}
