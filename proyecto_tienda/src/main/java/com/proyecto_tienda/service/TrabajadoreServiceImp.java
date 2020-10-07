package com.proyecto_tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto_tienda.model.Persona;
import com.proyecto_tienda.repository.PersonaRepo;
import com.proyecto_tienda.repository.TrabajadoreRepo;

@Service
public class TrabajadoreServiceImp implements TrabajadoreService{
	
	@Autowired
	TrabajadoreRepo traRepo;
	
	@Autowired
	PersonaRepo perRepo;
	
	@Autowired
	BCryptPasswordEncoder encriptacion;

	@Override
	public ArrayList<Persona> buscarDepartamentoVentas(String tipo) {
		return traRepo.buscarDepartamentoVentas(tipo);
	}

	@Override
	public void insertarMensajesDevolucion(int idDestinatario, int idOrigen, String adjunto, String asunto,
			String cuerpo, int leido, int contestado) {
		traRepo.insertarMensajesDevolucion(idDestinatario, idOrigen, adjunto, asunto, cuerpo, leido, contestado);
		
	}

	@Override
	public Persona registrarPersona(Persona persona) {
		
			String encriptarPassword = encriptacion.encode(persona.getPass());
			persona.setPass(encriptarPassword);
			persona = perRepo.save(persona);
		
		return persona;
	}

}
