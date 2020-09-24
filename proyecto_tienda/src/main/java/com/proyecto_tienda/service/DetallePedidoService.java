package com.proyecto_tienda.service;

import java.util.ArrayList;

import com.proyecto_tienda.model.DetallePedido;
import com.proyecto_tienda.model.Mensajeria;

public interface DetallePedidoService {
	ArrayList<DetallePedido> mostrarLineasPedido(int id) throws Exception;
	void insertDetallePedido(int idCabezera, Long cabezera, int cantidad, int total) throws Exception;
	void actualizarStock(Long id, int cantidad) throws Exception;
	ArrayList<Mensajeria> buscarDevoluconPedidos(int id) throws Exception;
	void borrarPedido(int id) throws Exception;
	void borrarLineaPedido(int id) throws Exception;
	DetallePedido buscarIdDetalleBorrar(int idCabecera) throws Exception;
	DetallePedido buscarId(int idPedido) throws Exception;
}
