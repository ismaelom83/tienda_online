package com.proyecto_tienda.repository;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proyecto_tienda.model.Persona;

@Repository
public class TrabajadoreRepo {

	@Autowired
	private EntityManager entityManager;

	public ArrayList<Persona> buscarDepartamentoVentas(String tipo) {
		Session session = entityManager.unwrap(Session.class);
		String hQuery = " from Persona p where p.tipoPersona ='" + tipo + "'";
		Query query = session.createQuery(hQuery);
		@SuppressWarnings("unchecked")
		ArrayList<Persona> persona = (ArrayList<Persona>) query.list();
		if (persona != null) {
			return persona;
		} else {
			System.out.println("producto no encontrado");
		}
		return null;

	}

	public void insertarMensajesDevolucion(int idDestinatario, int idOrigen, String adjunto, String asunto,
			String cuerpo, int leido, int contestado) {
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
