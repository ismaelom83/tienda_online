package com.proyecto_tienda.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the personas database table.
 * 
 */
@Entity
@Table(name="personas")
@NamedQuery(name="Persona.findAll", query="SELECT p FROM Persona p")
public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;


	@NotEmpty(message = "No puede estar vacio")
	@Size(min = 2, max = 20, message = "Numero de caracteres invalido")
	private String apellido1;


	@NotEmpty(message = "No puede estar vacio")
	@Size(min = 2, max = 20, message = "Numero de caracteres invalido")
	private String apellido2;

	@Column(name="baja_logica")
	private byte bajaLogica;


	@NotEmpty(message = "No puede estar vacio")
	@Pattern(regexp="\\d{8}[A-HJ-NP-TV-Z]",message = "Introduce un DNI Valido")
	private String dni;

	@NotNull(message = "Introduce un numero valido")
	@Max(value = 100, message = "La edad maxima es 100 años")
	@Min(value = 18, message = "La edad minima es 18 años")
	private byte edad;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_alta")
	private Date fechaAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_baja")
	private Date fechaBaja;

	@Email(message = "Introduce un email valido")
	private String mail;



	@NotEmpty(message = "No puede estar vacio")
	@Size(min = 2, max = 20, message = "Numero de caracteres invalido")
	private String nombre;

	@NotEmpty(message = "No puede estar vacio")
	@Size(min = 4, max = 4, message = "Introduce solo cuatro caracteres numeros o letras")
	private String pass;

	private String sexo;

	@Column(name="tipo_persona")
	private String tipoPersona;

	//bi-directional many-to-one association to Cliente
	@OneToMany(mappedBy="persona")
	private List<Cliente> clientes;

	//bi-directional many-to-one association to Mensajeria
	@OneToMany(mappedBy="persona")
	private List<Mensajeria> mensajerias;

	//bi-directional many-to-one association to Trabajadore
	@OneToMany(mappedBy="persona")
	private List<Trabajadore> trabajadores;

	public Persona() {
	}
	

	public Persona(
			@NotEmpty(message = "No puede estar vacio") @Size(min = 2, max = 20, message = "Numero de caracteres invalido") String apellido1,
			@NotEmpty(message = "No puede estar vacio") @Size(min = 2, max = 20, message = "Numero de caracteres invalido") String apellido2,
			@NotEmpty(message = "No puede estar vacio") @Size(min = 2, max = 20, message = "Numero de caracteres invalido") String dni,
			@NotEmpty(message = "No puede estar vacio") @Size(min = 2, max = 20, message = "Numero de caracteres invalido") String nombre) {
		super();
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.dni = dni;
		this.nombre = nombre;
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApellido1() {
		return this.apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return this.apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public byte getBajaLogica() {
		return this.bajaLogica;
	}

	public void setBajaLogica(byte bajaLogica) {
		this.bajaLogica = bajaLogica;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public byte getEdad() {
		return this.edad;
	}

	public void setEdad(byte edad) {
		this.edad = edad;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return this.fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTipoPersona() {
		return this.tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public List<Cliente> getClientes() {
		return this.clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente addCliente(Cliente cliente) {
		getClientes().add(cliente);
		cliente.setPersona(this);

		return cliente;
	}

	public Cliente removeCliente(Cliente cliente) {
		getClientes().remove(cliente);
		cliente.setPersona(null);

		return cliente;
	}

	public List<Mensajeria> getMensajerias() {
		return this.mensajerias;
	}

	public void setMensajerias(List<Mensajeria> mensajerias) {
		this.mensajerias = mensajerias;
	}

	public Mensajeria addMensajeria(Mensajeria mensajeria) {
		getMensajerias().add(mensajeria);
		mensajeria.setPersona(this);

		return mensajeria;
	}

	public Mensajeria removeMensajeria(Mensajeria mensajeria) {
		getMensajerias().remove(mensajeria);
		mensajeria.setPersona(null);

		return mensajeria;
	}

	public List<Trabajadore> getTrabajadores() {
		return this.trabajadores;
	}

	public void setTrabajadores(List<Trabajadore> trabajadores) {
		this.trabajadores = trabajadores;
	}

	public Trabajadore addTrabajadore(Trabajadore trabajadore) {
		getTrabajadores().add(trabajadore);
		trabajadore.setPersona(this);

		return trabajadore;
	}

	public Trabajadore removeTrabajadore(Trabajadore trabajadore) {
		getTrabajadores().remove(trabajadore);
		trabajadore.setPersona(null);

		return trabajadore;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellido1 == null) ? 0 : apellido1.hashCode());
		result = prime * result + ((apellido2 == null) ? 0 : apellido2.hashCode());
		result = prime * result + bajaLogica;
		result = prime * result + ((clientes == null) ? 0 : clientes.hashCode());
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		result = prime * result + edad;
		result = prime * result + ((fechaAlta == null) ? 0 : fechaAlta.hashCode());
		result = prime * result + ((fechaBaja == null) ? 0 : fechaBaja.hashCode());
		result = prime * result + id;
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((mensajerias == null) ? 0 : mensajerias.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((pass == null) ? 0 : pass.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result + ((tipoPersona == null) ? 0 : tipoPersona.hashCode());
		result = prime * result + ((trabajadores == null) ? 0 : trabajadores.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		if (apellido1 == null) {
			if (other.apellido1 != null)
				return false;
		} else if (!apellido1.equals(other.apellido1))
			return false;
		if (apellido2 == null) {
			if (other.apellido2 != null)
				return false;
		} else if (!apellido2.equals(other.apellido2))
			return false;
		if (bajaLogica != other.bajaLogica)
			return false;
		if (clientes == null) {
			if (other.clientes != null)
				return false;
		} else if (!clientes.equals(other.clientes))
			return false;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		if (edad != other.edad)
			return false;
		if (fechaAlta == null) {
			if (other.fechaAlta != null)
				return false;
		} else if (!fechaAlta.equals(other.fechaAlta))
			return false;
		if (fechaBaja == null) {
			if (other.fechaBaja != null)
				return false;
		} else if (!fechaBaja.equals(other.fechaBaja))
			return false;
		if (id != other.id)
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (mensajerias == null) {
			if (other.mensajerias != null)
				return false;
		} else if (!mensajerias.equals(other.mensajerias))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (pass == null) {
			if (other.pass != null)
				return false;
		} else if (!pass.equals(other.pass))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		if (tipoPersona == null) {
			if (other.tipoPersona != null)
				return false;
		} else if (!tipoPersona.equals(other.tipoPersona))
			return false;
		if (trabajadores == null) {
			if (other.trabajadores != null)
				return false;
		} else if (!trabajadores.equals(other.trabajadores))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Persona [id=" + id + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", bajaLogica="
				+ bajaLogica + ", dni=" + dni + ", edad=" + edad + ", fechaAlta=" + fechaAlta + ", fechaBaja="
				+ fechaBaja + ", mail=" + mail + ", nombre=" + nombre + ", pass=" + pass + ", sexo=" + sexo
				+ ", tipoPersona=" + tipoPersona + ", clientes=" + clientes + ", mensajerias=" + mensajerias
				+ ", trabajadores=" + trabajadores + "]";
	}
	
	

}