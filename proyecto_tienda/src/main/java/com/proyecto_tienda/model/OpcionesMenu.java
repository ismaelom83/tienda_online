package com.proyecto_tienda.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the opciones_menu database table.
 * 
 */
@Entity
@Table(name="opciones_menu")
@NamedQuery(name="OpcionesMenu.findAll", query="SELECT o FROM OpcionesMenu o")
public class OpcionesMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String opcion;

	@Column(name="tipo_persona")
	private String tipoPersona;

	public OpcionesMenu() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOpcion() {
		return this.opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	public String getTipoPersona() {
		return this.tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

}