package com.proyecto_tienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto_tienda.repository.MensajeriaRepo;

@Service
public class MensajeriaServiceImp implements MensajeriaService{

	
	@Autowired
	MensajeriaRepo menRepo;
	@Override
	public void borrarMensajeDevolucion(int id) {	
		menRepo.borrarMensajeDevolucion(id);
	}
	@Override
	public void insertarMensajesTrabajador(int idDestinatario, int idOrigen, String adjunto, String asunto,
			String cuerpo, int leido, int contestado) {
		menRepo.insertarMensajesTrabajador(idDestinatario, idOrigen, adjunto, asunto, cuerpo, leido, contestado);
		
	}

}
