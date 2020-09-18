package com.proyecto_tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto_tienda.dto.ChangePasswordForm;
import com.proyecto_tienda.model.Cliente;
import com.proyecto_tienda.model.Persona;
import com.proyecto_tienda.repository.ClienteRepo;
import com.proyecto_tienda.repository.PersonaRepo;
import com.proyecto_tienda.repository.PersonaRepositorio;

@Service
public class ClienteServiceImp implements ClienteService {

	@Autowired
	BCryptPasswordEncoder passwordDescifrada;

	@Autowired
	ClienteRepo cliRepo;

	@Autowired
	PersonaRepositorio perRepo;

	@Autowired
	PersonaRepo personaRepositorio;

	@Override
	public ArrayList<Persona> buscarTipoCliente(String cliente1, String cliente2) throws Exception {
		return cliRepo.buscarTipoCliente(cliente1, cliente2);
	}

	@Override
	public void registrarPersona(String nombre, String apellido1, String apellido2, String email, String dni,
			String password, Byte edad, String tipo, int baja) throws Exception {
		cliRepo.registrarPersona(nombre, apellido1, apellido2, email, dni, password, edad, tipo, baja);

	}

	@Override
	public Persona consultaUltimoCliente() throws Exception {
		return cliRepo.consultaUltimoCliente();
	}

	@Override
	public void registrarClientes(int idPersona, int puntos, float saldo, String categoria) throws Exception {
		cliRepo.registrarClientes(idPersona, puntos, saldo, categoria);

	}

	@Override
	public Cliente buscarClienteId(int id) throws Exception {
		return cliRepo.buscarClienteId(id);
	}

	@Override
	public Persona cambiarPassword(ChangePasswordForm ch) throws Exception {

		Persona persona = perRepo.buscarPersonaId(ch.getId());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String passwordExistente = persona.getPass();
		String currentPassword = ch.getCurrentPassword();
		String newPassword = ch.getNewPassword();

		if (!passwordEncoder.matches(currentPassword, passwordExistente)) {

			throw new Exception("La password actual no es la correcta");
		}
		if (passwordEncoder.matches(newPassword, passwordExistente)) {
			throw new Exception("La Nueva password debe de ser diferente a la anterior");
		}
		if (!ch.getNewPassword().equals(ch.getConfirmPassword())) {
			throw new Exception("Las Passwords no coinciden");
		}

		String encoderPassword = passwordDescifrada.encode(ch.getNewPassword());
		System.out.println("la password cifrada?" + encoderPassword);
		persona.setPass(encoderPassword);
		return personaRepositorio.save(persona);

	}

}
