package com.proyecto_tienda.service;

public interface MensajeriaService {
	public void borrarMensajeDevolucion(int id);
	void insertarMensajesTrabajador(int idDestinatario, int idOrigen,String adjunto, String asunto,String cuerpo,int leido,int contestado);
}
