package com.proyecto_tienda.service;

import java.util.ArrayList;

import com.proyecto_tienda.model.ValoracionesProducto;

public interface ValoracionesProductosService {
	
	ArrayList<ValoracionesProducto> valorarProducto(Long idProducto) throws Exception;

}
