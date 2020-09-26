package com.proyecto_tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto_tienda.model.DetallePedido;
import com.proyecto_tienda.model.Mensajeria;
import com.proyecto_tienda.repository.DetallePedidoRepo;

@Service
public class DetallePedidoServiceImp implements DetallePedidoService {

	@Autowired
	DetallePedidoRepo deRepo;

	@Override
	public ArrayList<DetallePedido> mostrarLineasPedido(int id) throws Exception {
		return deRepo.mostrarLineasPedido(id);
	}

	@Override
	public void insertDetallePedido(int idCabezera, Long cabezera, int cantidad, int total,int totalPuntos) throws Exception {
		deRepo.insertDetallePedido(idCabezera, cabezera, cantidad, total,totalPuntos);
	}

	@Override
	public void actualizarStock(Long id, int cantidad) throws Exception {
		deRepo.actualizarStock(id, cantidad);
	}

	@Override
	public ArrayList<Mensajeria> buscarDevoluconPedidos(int id) throws Exception {
		return deRepo.buscarDevoluconPedidos(id);
	}

	@Override
	public void borrarPedido(int id) throws Exception {
		deRepo.borrarPedido(id);
	}

	@Override
	public void borrarLineaPedido(int id) throws Exception {
		deRepo.borrarLineaPedido(id);
	}

	@Override
	public DetallePedido buscarIdDetalleBorrar(int idCabecera) throws Exception {
	 return	deRepo.buscarIdDetalleBorrar(idCabecera);
	}

	@Override
	public DetallePedido buscarId(int idPedido) throws Exception {
		
		return deRepo.buscarId(idPedido);
	}

}
