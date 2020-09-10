package com.proyecto_tienda.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the mensajeria database table.
 * 
 */
@Entity
@NamedQuery(name="Mensajeria.findAll", query="SELECT m FROM Mensajeria m")
public class Mensajeria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Lob
	private byte[] adjunto;

	private String asunto;

	private byte contestado;

	private String cuerpo;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_envio")
	private Date fechaEnvio;

	@Column(name="id_destinatario")
	private int idDestinatario;

	private byte leido;

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="id_origen")
	private Persona persona;

	public Mensajeria() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getAdjunto() {
		return this.adjunto;
	}

	public void setAdjunto(byte[] adjunto) {
		this.adjunto = adjunto;
	}

	public String getAsunto() {
		return this.asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public byte getContestado() {
		return this.contestado;
	}

	public void setContestado(byte contestado) {
		this.contestado = contestado;
	}

	public String getCuerpo() {
		return this.cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public Date getFechaEnvio() {
		return this.fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public int getIdDestinatario() {
		return this.idDestinatario;
	}

	public void setIdDestinatario(int idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public byte getLeido() {
		return this.leido;
	}

	public void setLeido(byte leido) {
		this.leido = leido;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

}