package com.proyecto_tienda.service;

import java.util.ArrayList;

import com.proyecto_tienda.model.Persona;

public interface TrabajadoreService {
	
	ArrayList<Persona> buscarDepartamentoVentas(String tipo);
	void insertarMensajesDevolucion(int idDestinatario, int idOrigen, String adjunto, String asunto,
			String cuerpo, int leido, int contestado);
	Persona registrarPersona(Persona persona)throws Exception;
}
