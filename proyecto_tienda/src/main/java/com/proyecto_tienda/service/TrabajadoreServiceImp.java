package com.proyecto_tienda.service;

import java.util.ArrayList;
import java.util.Optional;

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
	
	@SuppressWarnings("unused")
	private boolean comprobarExisteNombreUsuario2(Persona persona) throws Exception {
		Optional<Persona> personaNueva = perRepo.findBymail(persona.getMail());
		if (personaNueva.isPresent()) {
			throw new Exception("Este nombre de usuario ya existe");
		}
		return true;
	}

	@Override
	public Persona registrarPersona(Persona persona) throws Exception{
		
		if (comprobarExisteNombreUsuario2(persona)) {
			String encriptarPassword = encriptacion.encode(persona.getPass());
			persona.setPass(encriptarPassword);
			persona = perRepo.save(persona);
		}
				
		return persona;
	}

}
