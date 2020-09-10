package com.proyecto_tienda.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the categorias_menu database table.
 * 
 */
@Entity
@Table(name="categorias_menu")
@NamedQuery(name="CategoriasMenu.findAll", query="SELECT c FROM CategoriasMenu c")
public class CategoriasMenu implements Serializable {
	

	private static final long serialVersionUID = 1L;

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String categoria;

//	@Column(name="tipo_persona")
//	private String tipoPersona;

	//bi-directional many-to-one association to OpcCategoria
//	@OneToMany(mappedBy="categoriasMenu")
//	private List<OpcCategoria> opcCategorias;

	public CategoriasMenu() {
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
	
	public CategoriasMenu(String categoria) {
		super();
		this.categoria = categoria;
	}

//	public String getTipoPersona() {
//		return this.tipoPersona;
//	}
//
//	public void setTipoPersona(String tipoPersona) {
//		this.tipoPersona = tipoPersona;
//	}
//
//	public List<OpcCategoria> getOpcCategorias() {
//		return this.opcCategorias;
//	}
//
//	public void setOpcCategorias(List<OpcCategoria> opcCategorias) {
//		this.opcCategorias = opcCategorias;
//	}

//	public OpcCategoria addOpcCategoria(OpcCategoria opcCategoria) {
//		getOpcCategorias().add(opcCategoria);
//		opcCategoria.setCategoriasMenu(this);
//
//		return opcCategoria;
//	}
//
//	public OpcCategoria removeOpcCategoria(OpcCategoria opcCategoria) {
//		getOpcCategorias().remove(opcCategoria);
//		opcCategoria.setCategoriasMenu(null);
//
//		return opcCategoria;
//	}
	


	@Override
	public String toString() {
		return "CategoriasMenu [id=" + id + ", categoria=" + categoria + "]";
	}

	

}