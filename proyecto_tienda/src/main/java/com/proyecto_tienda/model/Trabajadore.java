package com.proyecto_tienda.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the trabajadores database table.
 * 
 */
@Entity
@Table(name="trabajadores")
@NamedQuery(name="Trabajadore.findAll", query="SELECT t FROM Trabajadore t")
public class Trabajadore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private double salario;

	private String user;

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="id_persona")
	private Persona persona;

	public Trabajadore() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getSalario() {
		return this.salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

}