package com.proyecto_tienda.service;

import java.util.ArrayList;

import com.proyecto_tienda.model.CategoriasMenu;

public interface CategoriaMenuService {
	
	ArrayList<CategoriasMenu> buscarCategoria() throws Exception;
	
	void save(CategoriasMenu categoria) throws Exception;
}
