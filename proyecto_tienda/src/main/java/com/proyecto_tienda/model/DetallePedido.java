package com.proyecto_tienda.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the detalle_pedido database table.
 * 
 */
@Entity
@Table(name="detalle_pedido")
@NamedQuery(name="DetallePedido.findAll", query="SELECT d FROM DetallePedido d")
public class DetallePedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int cantidad;

	@Column(name="total_linea")
	private double totalLinea;

	//bi-directional many-to-one association to CabeceraPedido
	@ManyToOne
	@JoinColumn(name="id_pedido")
	private CabeceraPedido cabeceraPedido;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="id_producto")
	private Producto producto;

	public DetallePedido() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getTotalLinea() {
		return this.totalLinea;
	}

	public void setTotalLinea(double totalLinea) {
		this.totalLinea = totalLinea;
	}

	public CabeceraPedido getCabeceraPedido() {
		return this.cabeceraPedido;
	}

	public void setCabeceraPedido(CabeceraPedido cabeceraPedido) {
		this.cabeceraPedido = cabeceraPedido;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}