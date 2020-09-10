package com.proyecto_tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto_tienda.model.Cliente;
import com.proyecto_tienda.model.Persona;
import com.proyecto_tienda.repository.ClienteRepo;

@Service
public class ClienteServiceImp implements ClienteService {

	
	@Autowired
	ClienteRepo cliRepo;
	
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

}
