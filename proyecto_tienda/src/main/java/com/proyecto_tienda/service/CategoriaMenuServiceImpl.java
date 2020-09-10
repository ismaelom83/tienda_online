package com.proyecto_tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto_tienda.model.CategoriasMenu;
import com.proyecto_tienda.repository.CategoriasMenuRepo;

@Service
public class CategoriaMenuServiceImpl implements CategoriaMenuService {

	
	@Autowired
	CategoriasMenuRepo catRepo;
	
	@Override
	public ArrayList<CategoriasMenu> buscarCategoria() throws Exception {	
		return catRepo.buscarCategoria();
	}

	@Override
	public void save(CategoriasMenu categoria) throws Exception {
		 catRepo.save(categoria);
	}

}
