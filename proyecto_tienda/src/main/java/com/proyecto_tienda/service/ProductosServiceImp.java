package com.proyecto_tienda.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto_tienda.model.Producto;
import com.proyecto_tienda.repository.ProductoRepo;

@Service
public class ProductosServiceImp implements ProductosService {

	@Autowired
	ProductoRepo pRepo;
	
	@Override
	public List<Producto> buscarTodosProductos() throws IOException {		
		return pRepo.buscarTodosProductos();
	}

	@Override
	public Producto buscarProductoId(Long id) throws IOException {
		
		return pRepo.buscarProductoId(id);
	}

	@Override
	public List<Producto> buscarProductoIdLista(Long id) throws IOException {
		
		return pRepo.buscarProductoIdLista(id);
	}

	@Override
	public ArrayList<Producto> buscarPructoCaregoria(String categoria) throws IOException {
		return pRepo.buscarPructoCaregoria(categoria);
	}

	@Override
	public void save(Producto producto) throws Exception {
		pRepo.save(producto);
	}


}
