package com.proyecto_tienda.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.proyecto_tienda.model.Producto;

public interface ProductosService {
	
	List<Producto> buscarTodosProductos() throws IOException;
	
    Producto buscarProductoId(Long id) throws IOException ;
    
    List<Producto> buscarProductoIdLista(Long id) throws IOException;
    
    ArrayList<Producto> buscarPructoCaregoria(String categoria) throws IOException;
    
    void save(Producto producto) throws Exception;
    
    void actualizarPuntosCangeable(Long id, int cantidad) throws Exception;
     
    
}
