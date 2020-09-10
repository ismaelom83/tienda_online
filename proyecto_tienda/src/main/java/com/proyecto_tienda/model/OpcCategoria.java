package com.proyecto_tienda.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the opc_categoria database table.
 * 
 */
@Entity
@Table(name="opc_categoria")
@NamedQuery(name="OpcCategoria.findAll", query="SELECT o FROM OpcCategoria o")
public class OpcCategoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String categoria;

	private String opcion;

	//bi-directional many-to-one association to CategoriasMenu
	@ManyToOne
	@JoinColumn(name="tipo_persona")
	private CategoriasMenu categoriasMenu;

	public OpcCategoria() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getOpcion() {
		return this.opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	public CategoriasMenu getCategoriasMenu() {
		return this.categoriasMenu;
	}

	public void setCategoriasMenu(CategoriasMenu categoriasMenu) {
		this.categoriasMenu = categoriasMenu;
	}

}