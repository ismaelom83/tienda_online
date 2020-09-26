package com.proyecto_tienda.repository;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proyecto_tienda.model.Cliente;
import com.proyecto_tienda.model.Persona;
import com.proyecto_tienda.model.Producto;




@Repository
public class ClienteRepo {

	@Autowired
	private EntityManager entityManager;
	
	public  ArrayList<Persona> buscarTipoCliente(String cliente1, String cliente2) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		String hQuery = " from Persona p where p.tipoPersona='"+cliente1+"'" + " or p.tipoPersona='"+cliente2+"'";
		Query query =  session.createQuery(hQuery);
		@SuppressWarnings("unchecked")
		ArrayList<Persona> clientesTipo = (ArrayList<Persona>) query.list();
		if (clientesTipo!=null) {
			return clientesTipo;
		}else {
		System.out.println("categoria no encontrada");	
		}
		return null;
		
	}
	
	public void registrarPersona(String nombre, String apellido1,String apellido2,
			String email,String dni,String password,Byte edad,String tipo,int baja) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		Transaction txn = session.beginTransaction();
		Query query = session.createNativeQuery(
				"INSERT INTO personas (nombre,apellido1,apellido2,mail,dni,pass,edad,tipo_persona,baja_logica) VALUES(?,?,?,?,?,?,?,?,?)");
		query.setParameter(1, nombre);
		query.setParameter(2, apellido1);
		query.setParameter(3, apellido2);
		query.setParameter(4, email);
		query.setParameter(5, dni);
		query.setParameter(6, password);
		query.setParameter(7, edad);
		query.setParameter(8, tipo);
		query.setParameter(9, baja);
		query.executeUpdate();
		txn.commit();
	}
	
	public Persona consultaUltimoCliente() throws Exception {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("from Persona order by id  DESC");
		query.setMaxResults(1);
		Persona last = (Persona) query.uniqueResult();
		return last;
	}
	
	public  void registrarClientes(int idPersona,int puntos,float saldo,String categoria) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		Transaction txn = session.beginTransaction();
		Query query = session.createNativeQuery(
				"INSERT INTO clientes (id_persona, puntos,saldo,categoria) VALUES(?,?,?,?)");
		query.setParameter(1, idPersona);
		query.setParameter(2, puntos);
		query.setParameter(3, saldo);
		query.setParameter(4, categoria);
		query.executeUpdate();
		txn.commit();
	}
	
	public Cliente buscarClienteId(int id) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		String hQuery = "from Cliente c " + " where c.persona.id = :id";
		Cliente c = session.createQuery(hQuery, Cliente.class).setParameter("id", id).setMaxResults(1).uniqueResult();
		return c;
		
		
	}
	
	public void actualizarSaldoCliente(int id, double cantidad) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		Transaction txn = session.beginTransaction();
		Query updateQuery = session
				.createQuery("UPDATE Cliente p set p.saldo=p.saldo - '" + cantidad + "' where id='" + id + "'");
		updateQuery.executeUpdate();
		txn.commit();
		
	}
	
	public void recargarSaldoCliente(int id, double cantidad) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		Transaction txn = session.beginTransaction();
		Query updateQuery = session
				.createQuery("UPDATE Cliente p set p.saldo=p.saldo + '" + cantidad + "' where id='" + id + "'");
		updateQuery.executeUpdate();
		txn.commit();
		
	}
	
	public void recargarPuntos(int id, int puntos) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		Transaction txn = session.beginTransaction();
		Query updateQuery = session
				.createQuery("UPDATE Cliente p set p.puntos=p.puntos + '" + puntos + "' where id='" + id + "'");
		updateQuery.executeUpdate();
		txn.commit();
		
	}
	
	public void actualizarPuntosCliente(int id, double cantidad) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		Transaction txn = session.beginTransaction();
		Query updateQuery = session
				.createQuery("UPDATE Cliente p set p.puntos=p.puntos - '" + cantidad + "' where id='" + id + "'");
		updateQuery.executeUpdate();
		txn.commit();
		
	}
	
}
