package com.proyecto_tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto_tienda.model.ValoracionesProducto;
import com.proyecto_tienda.repository.ValoracionesProductosRepo;
@Service
public class ValoracionesProductosServiceImp implements ValoracionesProductosService {

	
	@Autowired
	ValoracionesProductosRepo valoRepo;
	
	@Override
	public ArrayList<ValoracionesProducto> valorarProducto(Long idProducto) throws Exception {
		return valoRepo.valorarProducto(idProducto);
	}

}