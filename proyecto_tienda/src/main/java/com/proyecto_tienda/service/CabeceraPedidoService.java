package com.proyecto_tienda.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityNotFoundException;

import com.proyecto_tienda.model.CabeceraPedido;
import com.proyecto_tienda.model.DetallePedido;

public interface CabeceraPedidoService {

	 void insertCabeceraPedido(int id, int importe_total) throws Exception;
	ArrayList<CabeceraPedido> mostrarCabeceraPedido(int cliente) throws Exception;
	CabeceraPedido consultaUltimoIdCabecera() throws EntityNotFoundException,IOException;
	DetallePedido buscarIdDetalle(int idCabecera);
	CabeceraPedido buscarIdDetalleCabecera(int idCabecera);
	void actualizarTotalFactura(int id, int cantidad) throws Exception;
}
