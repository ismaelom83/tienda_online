package com.proyecto_tienda.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto_tienda.model.CabeceraPedido;
import com.proyecto_tienda.model.DetallePedido;
import com.proyecto_tienda.repository.CabeceraPedidoRepo;
@Service
public class CabeceraPedidoServiceImp implements CabeceraPedidoService {
	
	@Autowired
	CabeceraPedidoRepo caRepo;

	@Override
	public void insertCabeceraPedido(int id, int importe_total) throws Exception {
		caRepo.insertCabeceraPedido(id, importe_total);	
	}

	@Override
	public ArrayList<CabeceraPedido> mostrarCabeceraPedido(int cliente) throws Exception {
		return caRepo.mostrarCabeceraPedido(cliente);
		
	}

	@Override
	public CabeceraPedido consultaUltimoIdCabecera() throws EntityNotFoundException, IOException{
		return caRepo.consultaUltimoIdCabecera();
	}

	@Override
	public DetallePedido buscarIdDetalle(int idCabecera) {
		return caRepo.buscarIdDetalle(idCabecera);
	}

	@Override
	public CabeceraPedido buscarIdDetalleCabecera(int idCabecera) {
		return caRepo.buscarIdDetalleCabecera(idCabecera);
	}

	@Override
	public void actualizarTotalFactura(int id, int cantidad) throws Exception {
		caRepo.actualizarTotalFactura(id, cantidad);
		
	}



}
