package com.proyecto_tienda.service;

import java.util.ArrayList;

import com.proyecto_tienda.dto.ChangePasswordForm;
import com.proyecto_tienda.model.Cliente;
import com.proyecto_tienda.model.Persona;

public interface ClienteService {

	ArrayList<Persona> buscarTipoCliente(String cliente1, String cliente2) throws Exception;

	void registrarPersona(String nombre, String apellido1, String apellido2, String email, String dni, String password,
			Byte edad, String tipo, int baja) throws Exception;

	Persona consultaUltimoCliente() throws Exception;

	void registrarClientes(int idPersona, int puntos, float saldo, String categoria) throws Exception;

	Cliente buscarClienteId(int id) throws Exception;
	
	Persona cambiarPassword(ChangePasswordForm ch)throws Exception;
	
	void actualizarSaldoCliente(int id, double cantidad) throws Exception;
	
	void recargarSaldoCliente(int id, double cantidad) throws Exception;
	
	void recargarPuntos(int id, int puntos) throws Exception;
	
	void actualizarPuntosCliente(int id, double cantidad) throws Exception;
}
