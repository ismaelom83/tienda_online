package com.proyecto_tienda.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cabecera_pedido database table.
 * 
 */
@Entity
@Table(name="cabecera_pedido")
@NamedQuery(name="CabeceraPedido.findAll", query="SELECT c FROM CabeceraPedido c")
public class CabeceraPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="importe_total")
	private int importeTotal;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	//bi-directional many-to-one association to DetallePedido
	@OneToMany(mappedBy="cabeceraPedido")
	private List<DetallePedido> detallePedidos;

	@Column(name="importe_total_puntos")
	private int importeTotalPuntos;
	
	public CabeceraPedido() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getImporteTotal() {
		return this.importeTotal;
	}

	
	public void setImporteTotal(int importeTotal) {
		this.importeTotal = importeTotal;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<DetallePedido> getDetallePedidos() {
		return this.detallePedidos;
	}

	public void setDetallePedidos(List<DetallePedido> detallePedidos) {
		this.detallePedidos = detallePedidos;
	}

	public DetallePedido addDetallePedido(DetallePedido detallePedido) {
		getDetallePedidos().add(detallePedido);
		detallePedido.setCabeceraPedido(this);

		return detallePedido;
	}

	public DetallePedido removeDetallePedido(DetallePedido detallePedido) {
		getDetallePedidos().remove(detallePedido);
		detallePedido.setCabeceraPedido(null);

		return detallePedido;
	}

	public int getImporteTotalPuntos() {
		return importeTotalPuntos;
	}

	public void setImporteTotalPuntos(int importeTotalPuntos) {
		this.importeTotalPuntos = importeTotalPuntos;
	}
	
	

}