package com.proyecto_tienda.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the valoraciones_productos database table.
 * 
 */
@Entity
@Table(name = "valoraciones_productos")
@NamedQuery(name = "ValoracionesProducto.findAll", query = "SELECT v FROM ValoracionesProducto v")
public class ValoracionesProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	private String comentarios;

	private String valoracion;

	// bi-directional one-to-one association to Producto
	@OneToOne
	@JoinColumn(name = "id")
	private Producto producto;

	private String nombre_persona;

	private Long id_producto;

	public ValoracionesProducto() {
	}

	public Long getId_producto() {
		return id_producto;
	}

	public void setId_producto(Long id_producto) {
		this.id_producto = id_producto;
	}

	public String getNombre_persona() {
		return nombre_persona;
	}

	public void setNombre_persona(String nombre_persona) {
		this.nombre_persona = nombre_persona;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComentarios() {
		return this.comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public String getValoracion() {
		return this.valoracion;
	}

	public void setValoracion(String valoración) {
		this.valoracion = valoración;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}