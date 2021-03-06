package com.proyecto_tienda.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto_tienda.dto.ChangePasswordForm;
import com.proyecto_tienda.model.CabeceraPedido;
import com.proyecto_tienda.model.CategoriasMenu;
import com.proyecto_tienda.model.Cliente;
import com.proyecto_tienda.model.DetallePedido;
import com.proyecto_tienda.model.Mensajeria;
import com.proyecto_tienda.model.Persona;
import com.proyecto_tienda.model.Producto;
import com.proyecto_tienda.model.ValoracionesProducto;
import com.proyecto_tienda.pdf.FacturaWriter;
import com.proyecto_tienda.repository.ProductoRepositorio;
import com.proyecto_tienda.service.CabeceraPedidoService;
import com.proyecto_tienda.service.CategoriaMenuService;
import com.proyecto_tienda.service.ClienteService;
import com.proyecto_tienda.service.DetallePedidoService;
import com.proyecto_tienda.service.MensajeriaService;
import com.proyecto_tienda.service.ProductosService;
import com.proyecto_tienda.service.TrabajadoreService;
import com.proyecto_tienda.service.ValoracionesProductosService;

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

	@Autowired
	ValoracionesProductosService valoService;

	@Autowired
	TrabajadoreService traSer;

	@GetMapping("/trabajadorVentas")
	public String trabajadorVentas(HttpSession session, Persona persona, Model model) {
		persona = (Persona) session.getAttribute("nombre");
		model.addAttribute("nombre", persona);
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
		return "app/trabajadorVentas";

	}

	@GetMapping("/devolverPedido")
	public String devolverPedido(HttpSession session, Persona persona, Model model) {
		persona = (Persona) session.getAttribute("nombre");
		ArrayList<Mensajeria> devolucionPedidos = null;
		try {
			devolucionPedidos = deService.buscarDevoluconPedidos(persona.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("listaDevolucionPedidos", devolucionPedidos);
		return "app/devolverPedido";

	}

	@GetMapping("/ControllerDevolverTotal")
	public String devolver(@RequestParam String id, CabeceraPedido cabecera) {

		cabecera = cabeService.buscarIdDetalleCabecera(Integer.parseInt(id));

		try {
			deService.borrarPedido(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			cliService.recargarSaldoCliente(cabecera.getCliente().getId(), cabecera.getImporteTotal());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int puntosAcumulados = cabecera.getImporteTotal() / 10;
		System.out.println();

		try {
			cliService.recargarPuntos(cabecera.getCliente().getId(),
					cabecera.getImporteTotalPuntos() - puntosAcumulados);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		menService.borrarMensajeDevolucion(Integer.parseInt(id));
		return "redirect:/devolverPedido";

	}

	@GetMapping("/ControllerDevolverParcial")
	public String devolverParcial(@RequestParam String id, @RequestParam String id2, CabeceraPedido cabecera,
			DetallePedido detalle2) {

		cabecera = cabeService.buscarIdDetalleCabecera(Integer.parseInt(id2));
		try {
			detalle2 = deService.buscarId(Integer.parseInt(id));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			deService.borrarLineaPedido(Integer.parseInt(id));

			DetallePedido detalle = deService.buscarIdDetalleBorrar(Integer.parseInt(id2));
			if (detalle == null) {
				deService.borrarPedido(Integer.parseInt(id2));
			}

		} catch (NumberFormatException e) {
			logger.error("El id no es un numero");
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Linea de pedido no encontrada");
			e.printStackTrace();
		}

		int total = 0;
		int totalPuntos = 0;
		ArrayList<DetallePedido> detallePedido = null;
		try {
			detallePedido = deService.mostrarLineasPedido(Integer.parseInt(id2));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (DetallePedido detallePedido2 : detallePedido) {
			total += detallePedido2.getTotalLinea();
			totalPuntos += detallePedido2.getTotalLineaPuntos();
		}

		try {
			cabeService.actualizarTotalFactura(Integer.parseInt(id2), total);
			cabeService.actualizarTotalPuntosFactura(Integer.parseInt(id2), totalPuntos);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			cliService.recargarSaldoCliente(cabecera.getCliente().getId(), detalle2.getTotalLinea());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int puntosAcumulados = (int) (detalle2.getTotalLinea() / 10);

		try {
			cliService.recargarPuntos(cabecera.getCliente().getId(), detalle2.getTotalLineaPuntos() - puntosAcumulados);
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
	public void generarFactura(@PathVariable int id, HttpSession session, HttpServletResponse response, Persona persona)
			throws IOException {

		if (session.getAttribute("nombre") != null) {
			DetallePedido dp = cabeService.buscarIdDetalle(id);
			CabeceraPedido cp = cabeService.buscarIdDetalleCabecera(dp.getCabeceraPedido().getId());

			persona = (Persona) session.getAttribute("nombre");

			if (cp != null) {
				File bill = new FacturaWriter().escribirFactura(cp);
				response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", bill.getName()));
				try {
					OutputStream out = response.getOutputStream();
					InputStream is = new FileInputStream(bill);
					IOUtils.copy(is, out);
					out.flush();
					is.close();
					out.close();

				} catch (IllegalStateException e) {
					logger.error(String.format("No se pudo generar la factura del pedido con id %d", id));
					e.printStackTrace();
				}

				logger.info(String.format("Se descargo la factura del pedido con id %d", id));

				bill.delete();

			}

			logger.info(String.format("No Se descargo la factura del pedido con id %d", id));
		}
//		return "redirect:/trabajadorCompras";	
	}

	@GetMapping("/detallePedidoBD/{id}")
	public String detallePedidoBD(@PathVariable int id, Model model) throws Exception {

		int lProductos = 0;
		int totalPuntos = 0;
		ArrayList<DetallePedido> detalle2 = deService.mostrarLineasPedido(id);
		for (DetallePedido detallePedido : detalle2) {
			lProductos += detallePedido.getTotalLinea();
			totalPuntos += detallePedido.getTotalLineaPuntos();
		}
		for (DetallePedido detallePedido : detalle2) {
			int detalle = detallePedido.getId();
			model.addAttribute("idDetalle", detalle);
		}
		model.addAttribute("totalLinea2", lProductos);
		model.addAttribute("lineaDetalle", detalle2);
		model.addAttribute("totalPuntos", totalPuntos);

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
		System.out.println("estoy en insertar productos");
		model.addAttribute("listaCategorias", listaCategorias);

		return "modales/insertarCategoria";

	}

	@PostMapping(value = "/insertarProductos")
	public String upload(@RequestParam("file") MultipartFile file, @RequestParam("descripcion") String descripcion,
			@RequestParam("precioUnitarioSinIva") String precioUnitarioSinIva, @RequestParam("stock") String stock,
			@RequestParam("categoria2") String categoria2, Model model) throws IOException {
		ArrayList<CategoriasMenu> listaCategorias = null;
		try {
			listaCategorias = catService.buscarCategoria();
		} catch (Exception e1) {
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
	public String insertarCategoriaPost(Model model, @RequestParam("nombre") String nombre) {
		CategoriasMenu categoria = new CategoriasMenu();

		categoria.setCategoria(nombre);

		System.out.println("esto es el nombre de la categoria nueva: " + nombre);

		try {
			catService.save(categoria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/trabajadorCompras";
	}

	@GetMapping("/editarProducto/{id}")
	public String editarProducto(@PathVariable Long id, Model model, Producto producto, HttpSession session) {

		try {
			producto = pService.buscarProductoId(id);
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
	public String modificarProd(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam("descripcion") String descripcion,
			@RequestParam("precioUnitarioSinIva") String precioUnitarioSinIva, @RequestParam("stock") String stock,
			@RequestParam("id") String id, Model model) throws IOException {

		Producto prod = pService.buscarProductoId(Long.parseLong(id));
		System.out.println("id del p " + id);
		prod.setDescripcion(descripcion);
		prod.setPrecioUnitarioSinIva(Integer.parseInt(precioUnitarioSinIva));
		prod.setStock(Integer.parseInt(stock));
		System.out.println("el stock nuevo " + prod.getStock());
		if (!file.isEmpty()) {
			String folder = "./src/main/resources/static/img/";
			byte[] bytes = file.getBytes();
			Path path = Paths.get(folder + file.getOriginalFilename());
			Files.write(path, bytes);
			prod.setRutaImagen(file.getOriginalFilename());
		}

		productoRepositorioInterface.save(prod);

//	 model.addAttribute("modificacionProductoExitosa", "Exito al modificar el producto.");

		return "redirect:/trabajadorCompras";
	}

	@GetMapping("/eliminarProducto/{id}")
	public String eliminarProducto(@PathVariable Long id, Model model, Producto producto) {

		try {
			producto = pService.buscarProductoId(id);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Productos no encontrado");
		}
		if (producto != null) {
			productoRepositorioInterface.delete(producto);
			logger.warn("Producto no borrado: ");
		} else {
			return "redirect:/trabajadorCompras";
		}

		return "redirect:/trabajadorCompras";
	}

	@GetMapping("/administrador")
	public String administrador(HttpSession session, Persona persona, Model model) {
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

	@GetMapping("/descuentoProductos")
	public String descuentoProductos() {

		return "app/descuentoProductos";

	}

	@PostMapping("/productoCangeable")
	public String productoCangeable(@RequestParam(name = "idProducto") String idProducto,
			@RequestParam(name = "puntosCangeable") String puntosCangeable, Persona persona, Cliente cliente,
			Producto producto, HttpSession session, Model model) {

		try {
			producto = pService.buscarProductoId(Long.parseLong(idProducto));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			pService.actualizarPuntosCangeable(Long.parseLong(idProducto), Integer.parseInt(puntosCangeable));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			byte cangeableONo1 = 1;
			byte descuento = 0;
			int precio = 0;
			pService.actualizarCangeable(Long.parseLong(idProducto), cangeableONo1);
			pService.actualizarDescuento(Long.parseLong(idProducto), descuento);
			pService.actualizarPrecio(Long.parseLong(idProducto), precio);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/trabajadorVentas";

	}

	@PostMapping("/quitarCanjeable")
	public String quitarCanjeable(@RequestParam(name = "idQuitar") String idQuitar,
			@RequestParam(name = "precioNuevo") String precioNuevo, Persona persona, Cliente cliente, Producto producto,
			HttpSession session, Model model) {

		try {
			producto = pService.buscarProductoId(Long.parseLong(idQuitar));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			byte cangeableONo0 = 0;
			pService.actualizarCangeable(Long.parseLong(idQuitar), cangeableONo0);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			int puntos = 0;
			pService.actualizarPrecio(Long.parseLong(idQuitar), Integer.parseInt(precioNuevo));
			pService.actualizarPuntosCangeable(Long.parseLong(idQuitar), puntos);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/trabajadorVentas";

	}

	@PostMapping("/cambiarDescuento")
	public String cambiarDescuento(@RequestParam(name = "idProducto") String idProducto,
			@RequestParam(name = "descuentoActual") String descuentoActual,
			@RequestParam(name = "nuevoDescuento") String nuevoDescuento) {

		try {
			pService.actualizarDescuento(Long.parseLong(idProducto), Byte.parseByte(nuevoDescuento));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/trabajadorVentas";

	}

	@GetMapping("/verValoraciones/{id}")
	public String verValoraciones(@PathVariable Long id, Producto producto, Model modelo) {

		try {
			producto = pService.buscarProductoId(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<ValoracionesProducto> listaValoraciones = null;

		try {
			listaValoraciones = valoService.valorarProducto(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("esto es el id del producto " + listaValoraciones);
		float sumaMedia = 0;
		for (ValoracionesProducto valoracionesProducto : listaValoraciones) {
			sumaMedia += valoracionesProducto.getValoracion();
		}
		float tamanio = listaValoraciones.size();

		float media = sumaMedia / tamanio;
		media = (float) (Math.round(media * 100) / 100d);
		modelo.addAttribute("media", media);
		modelo.addAttribute("producto", producto);
		modelo.addAttribute("listaValoraciones", listaValoraciones);
		return "app/verValoraciones";

	}

	@GetMapping("/enviarPromociones/{id}")
	public String enviarPromocionGet(@PathVariable Long id, Producto producto, Model modelo) {

		try {
			producto = pService.buscarProductoId(id);
		} catch (IOException e) {
			e.printStackTrace();
		}

		modelo.addAttribute("producto", producto);
		return "app/enviarPromociones";

	}

	@PostMapping("/enviarPromocion")
	public String enviarPromocionPost(@RequestParam(name = "precioPromocion") int precioPromocion,
			@RequestParam(name = "fecha") String fecha,@RequestParam(name = "id") String id, HttpSession session, Persona persona) {
		persona = (Persona) session.getAttribute("nombre");

		ArrayList<Persona> listaClientes = null;
		try {
			listaClientes = cliService.buscarTipoCliente("CN", "CP");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Persona persona2 : listaClientes) {
			traSer.insertarMensajesDevolucion(persona2.getId(), persona.getId(), "promocion", "promocion",
					"https://ismael-tienda-online.herokuapp.com/verPromocionesClienteUrl?precio=" + precioPromocion + "&fecha=" + fecha + "&id=" + Long.parseLong(id) + "",
					0, 0);
		}

		return "redirect:/administrador";

	}
	
	@GetMapping("/verPromocionesCliente")
	public String verPromocionesCliente(HttpSession session,Persona persona,Model model) {
		
		persona = (Persona) session.getAttribute("nombre");
		ArrayList<Mensajeria> listaPromociones = null;
		try {
			listaPromociones = deService.buscarDevoluconPedidos(persona.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Mensajeria mensajeria : listaPromociones) {
			System.out.println("cuerpo: "+mensajeria.getCuerpo());
		}
		model.addAttribute("listaDevolucionPedidos", listaPromociones);
		model.addAttribute("persona2", persona);
		
		return "app/verPromocionesCliente";
		
	}

	@GetMapping("/verPromocionesClienteUrl")
	public String verPromocionesClienteUrl(@RequestParam(name = "precio") int precio,
			@RequestParam(name = "fecha") String fecha,@RequestParam(name = "id") String id,HttpSession session,
			Persona persona,Model modelo,Producto producto) {
		
		try {
			producto = pService.buscarProductoId(Long.parseLong(id));
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		modelo.addAttribute("producto", producto);
		modelo.addAttribute("precio", precio);
		modelo.addAttribute("fecha", fecha);
		
		return "app/verPromocionesClienteUrl";
		
	}

	
}
