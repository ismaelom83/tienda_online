package com.proyecto_tienda.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.log4j.Logger;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.proyecto_tienda.model.CabeceraPedido;
import com.proyecto_tienda.model.CategoriasMenu;
import com.proyecto_tienda.model.DetallePedido;
import com.proyecto_tienda.model.Mensajeria;
import com.proyecto_tienda.model.Persona;
import com.proyecto_tienda.model.Producto;
import com.proyecto_tienda.pdf.FacturaWriter;
import com.proyecto_tienda.repository.ProductoRepositorio;
import com.proyecto_tienda.service.CabeceraPedidoService;
import com.proyecto_tienda.service.CategoriaMenuService;
import com.proyecto_tienda.service.ClienteService;
import com.proyecto_tienda.service.DetallePedidoService;
import com.proyecto_tienda.service.MensajeriaService;
import com.proyecto_tienda.service.ProductosService;


@Controller
public class TrabajadorController {
	
	static Logger logger = Logger.getLogger(TrabajadorController.class);

	@Autowired
	DetallePedidoService deService;

	@Autowired
	MensajeriaService menService;

	@Autowired
	ClienteService cliService;

	@Autowired
	CabeceraPedidoService cabeService;

	@Autowired
	ServletContext context;

	@Autowired
	ProductosService pService;

	@Autowired
	CategoriaMenuService catService;
	
	@Autowired
	ProductoRepositorio productoRepositorioInterface;

	@GetMapping("/trabajadorVentas")
	public String trabajadorVentas(HttpSession session, Persona persona, Model model) {
		persona = (Persona) session.getAttribute("nombre");
		model.addAttribute("nombre", persona);
		return "app/trabajadorVentas";

	}

	@GetMapping("/devolverPedido")
	public String devolverPedido(HttpSession session, Persona persona, Model model) {
		persona = (Persona) session.getAttribute("nombre");
		ArrayList<Mensajeria> devolucionPedidos = null;
	   try {
		devolucionPedidos = deService.buscarDevoluconPedidos(persona.getId());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		model.addAttribute("listaDevolucionPedidos", devolucionPedidos);
		return "app/devolverPedido";

	}

	@GetMapping("/ControllerDevolver")
	public String devolver(@RequestParam String id) {
		System.out.println("esto es el id del devolucion" + id);
		try {
			deService.borrarPedido(Integer.parseInt(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		menService.borrarMensajeDevolucion(Integer.parseInt(id));
		return "redirect:/devolverPedido";

	}

	@GetMapping("/enviarMensaje")
	public String enviarMensajeGet() {

		return "app/enviarMensaje";

	}

	@GetMapping("/enviarMensajeBaseDatos")
	public String enviarMensajePost(HttpSession session, Model model, Persona persona,
			@RequestParam(name = "id") String id, @RequestParam(name = "asunto") String asunto,
			@RequestParam(name = "cuerpo") String cuerpo) {
		persona = (Persona) session.getAttribute("nombre");
		menService.insertarMensajesTrabajador(Integer.parseInt(id), persona.getId(), "hola", asunto, cuerpo, 0, 0);
		return "redirect:/clientes";

	}

	@GetMapping("/trabajadorCompras")
	public String trabajadorCompras(HttpSession session, Persona persona, Model model) {
		ArrayList<CategoriasMenu> listaCategorias = null;
		try {
			listaCategorias = catService.buscarCategoria();
		} catch (Exception e1) {
			logger.error("Lista de productos no encontrada");
			e1.printStackTrace();
		}
		model.addAttribute("listaCategorias", listaCategorias);
		persona = (Persona) session.getAttribute("nombre");
		model.addAttribute("nombre", persona);
		List<Producto> listaProductos = null;
		try {
			listaProductos = pService.buscarTodosProductos();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Lista de productos no encontrada: " + persona.getNombre());
		}
		model.addAttribute("listaProductos2", listaProductos);
		ArrayList<Persona> listaClientes = null;
		try {
			listaClientes = cliService.buscarTipoCliente("CN", "CP");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("listaClientes", listaClientes);

		return "app/trabajadorCompras";
	}

	@GetMapping("/mostrarMensajesTrabajador")
	public String mostrarMensajesTrabajador(HttpSession session, Persona persona, Model model) throws Exception {
		persona = (Persona) session.getAttribute("nombre");
		ArrayList<Mensajeria> listaMensajes = deService.buscarDevoluconPedidos(persona.getId());
		model.addAttribute("listaMensajes2", listaMensajes);
		model.addAttribute("nombre", persona);
		return "app/mostrarMensajesTrabajador";

	}

	@GetMapping("/cabeceraPedidoTrabajador/{id}")
	public String cabeceraPedidoTrabajador(@PathVariable int id, Model model) {
		ArrayList<CabeceraPedido> cabeceraPedido2 = null;

		try {
			cabeceraPedido2 = cabeService.mostrarCabeceraPedido(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("cabecera no mostrada");
		}
		model.addAttribute("cabecera2", cabeceraPedido2);

		return "app/cabeceraPedidoTrabajador";

	}
	
	@GetMapping("/generarFactura/{id}")
	public String generarFactura(@PathVariable int id,HttpSession session, HttpServletResponse response) {

		
		
		if (session.getAttribute("nombre") != null) {
			System.out.println("hola1");
			DetallePedido dp = cabeService.buscarIdDetalle(id);
			
			CabeceraPedido cp = cabeService.buscarIdDetalleCabecera(dp.getCabeceraPedido().getId());

//			if (cp != null && cp.getCliente().getId() == (int) session.getAttribute("nombre")) {
				System.out.println("hola2");
				File bill = new FacturaWriter().escribirFactura(cp);

				response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", bill.getName()));

				try {

					OutputStream out = response.getOutputStream();
					// response.setContentType(message.getTipoAdjunto());
					InputStream is = new FileInputStream(bill);
					IOUtils.copy(is, out);
					out.flush();
					is.close();
					out.close();

				} catch (Exception e) {

					logger.error(String.format("No se pudo generar la factura del pedido con id %d", id));
					e.printStackTrace();

				}

				logger.info(String.format("Se descargo la factura del pedido con id %d", id));

				bill.delete();

//			}

		}
		System.out.println("hola3");
		return "redirect:/trabajadorCompras";
		
	}

	@GetMapping("/detallePedidoBD/{id}")
	public String detallePedidoBD(@PathVariable int id, Model model) throws Exception {

		int lProductos = 0;
		ArrayList<DetallePedido> detalle2 = deService.mostrarLineasPedido(id);
		for (DetallePedido detallePedido : detalle2) {
			lProductos += detallePedido.getTotalLinea();
		}
		model.addAttribute("totalLinea2", lProductos);
		model.addAttribute("lineaDetalle", detalle2);

		return "app/detallePedidoBaseDatos";

	}

	@GetMapping("/insertarProductos")
	public String insertarProductosGet(Model model) {

		ArrayList<CategoriasMenu> listaCategorias = null;
		try {
			listaCategorias = catService.buscarCategoria();
		} catch (Exception e) {
			logger.error("Lista de productos no encontrada");
			e.printStackTrace();
		}
		model.addAttribute("listaCategorias", listaCategorias);

		return "modales/insertarProducto";

	}

	@PostMapping(value = "/insertarProductos")
	public String upload(@RequestParam("file") MultipartFile file,
			@RequestParam("descripcion") String descripcion,
			@RequestParam("precioUnitarioSinIva") String precioUnitarioSinIva, @RequestParam("stock") String stock,
			@RequestParam("categoria2") String categoria2, Model model) throws IOException {
		ArrayList<CategoriasMenu> listaCategorias = null;
		try {
			listaCategorias = catService.buscarCategoria();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		model.addAttribute("listaCategorias", listaCategorias);
		if (!file.isEmpty()) {
			String folder = "./src/main/resources/static/img/";
			byte[] bytes = file.getBytes();
			Path path = Paths.get(folder + file.getOriginalFilename());
			Files.write(path, bytes);
		}
		Producto producto = new Producto();
		producto.setDescripcion(descripcion);
		producto.setPrecioUnitarioSinIva(Integer.parseInt(precioUnitarioSinIva));
		producto.setRutaImagen(file.getOriginalFilename());
		producto.setStock(Integer.parseInt(stock));
		producto.setCategoria(categoria2);
	

		try {
			pService.save(producto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/trabajadorCompras";
	}
	
	@GetMapping("/insertarCategoria")
	public String insertarCategoriaGet(Model model) {
		ArrayList<CategoriasMenu> listaCategorias = null;
		try {
			listaCategorias = catService.buscarCategoria();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("listaCategorias", listaCategorias);
		return "modales/insertarCategoria";
		
	}
	
	@PostMapping("/insertarCategoria")
	public String insertarCategoriaPost(Model model,@RequestParam ("nombre") String nombre) {
		CategoriasMenu categoria  = new CategoriasMenu();
		
		categoria.setCategoria(nombre);
		
		System.out.println("esto es el nombre de la categoria nueva: "+nombre);
		
		try {
			catService.save(categoria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/trabajadorCompras";
	}
	
	@GetMapping("/editarProducto/{id}")
	public String editarProducto(@PathVariable Long id,Model model,Producto producto,HttpSession session) {
		
	try {
		producto =	pService.buscarProductoId(id);
	} catch (IOException e) {
		e.printStackTrace();
		logger.error("Productos no encontrado");
	}
	session.setAttribute("prod", producto);
	producto = (Producto) session.getAttribute("prod");
	model.addAttribute("prod", producto);
		
		return "app/editarProducto";	
	}
	
	@PostMapping(value = "/modificarProducto")
    public String modificarProd(@RequestParam(value="file",required=false) MultipartFile file,
    		@RequestParam("descripcion") String descripcion,
    		@RequestParam("precioUnitarioSinIva") String precioUnitarioSinIva,
    		@RequestParam("stock") String stock,
    		@RequestParam("id") String id,
    		Model model) throws IOException{
	 
	 Producto prod = pService.buscarProductoId(Long.parseLong(id));
	 System.out.println("id del p "+id);
	 prod.setDescripcion(descripcion);
	 prod.setPrecioUnitarioSinIva(Integer.parseInt(precioUnitarioSinIva));
	 prod.setStock(Integer.parseInt(stock));
	 System.out.println("el stock nuevo " +prod.getStock());
	 if (!file.isEmpty()) {
		 String folder= "./src/main/resources/static/img/";
		 byte[] bytes=file.getBytes();
		 Path path = Paths.get(folder +file.getOriginalFilename());
		 Files.write(path, bytes);		
		 prod.setRutaImagen(file.getOriginalFilename());
		 }  	 
	 
		 productoRepositorioInterface.save(prod);
	
//	 model.addAttribute("modificacionProductoExitosa", "Exito al modificar el producto.");
	 
	 return "redirect:/trabajadorCompras";
 }
	
	@GetMapping("/eliminarProducto/{id}")
	public String eliminarProducto(@PathVariable Long id, Model model,Producto producto) {
		
	try {
		producto =	pService.buscarProductoId(id);
	} catch (IOException e) {
		e.printStackTrace();
		logger.error("Productos no encontrado");
	}
		if (producto!=null) {
			productoRepositorioInterface.delete(producto);
			logger.warn("Producto no borrado: ");
		}else {
			return "redirect:/trabajadorCompras";
		}
	
		
		return "redirect:/trabajadorCompras";
	}
	
	@GetMapping("/administrador")
	public String administrador(HttpSession session, Persona persona,Model model) {
		persona = (Persona) session.getAttribute("nombre");
		model.addAttribute("nombre", persona);	
	    
		
		return "app/administrador";
		
	}
	@GetMapping("/verLogsConsola")
	public String verLogsConsola() {

		
		Path path = Paths.get("C:\\Users\\tron8\\logs\\Test_01.log");
		try {
			@SuppressWarnings("resource")
			Stream<String> stream = Files.lines(path);
			stream.forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return "redirect:/administrador";
	}
	
	@GetMapping("/verLogsArchivo")
	public String verLogsConsolaArchivo() {

		try {
			@SuppressWarnings("unused")
			Process p = new ProcessBuilder("explorer.exe", "/select,C:\\Users\\tron8\\logs\\Test_01.log").start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/administrador";
	}

}
